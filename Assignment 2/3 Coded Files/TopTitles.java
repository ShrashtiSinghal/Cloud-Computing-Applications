import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.*;
import java.util.*;
/**
 * Verify:
 * hadoop fs -cat $HDFS_HOME/TopTitles-output/* | sort -n -k2 -r | head -n10
 */
public class TopTitles extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new TopTitles(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = this.getConf();
        FileSystem fs = FileSystem.get(conf);
        Path tmpPath = new Path("./tmp");
        fs.delete(tmpPath, true);

        Job jobA = Job.getInstance(conf, "Title Count");
        jobA.setOutputKeyClass(Text.class);
        jobA.setOutputValueClass(IntWritable.class);

        jobA.setMapperClass(TitleCountMap.class);
        jobA.setReducerClass(TitleCountReduce.class);

        FileInputFormat.setInputPaths(jobA, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobA, tmpPath);

        jobA.setJarByClass(TopTitles.class);
        jobA.waitForCompletion(true);

        Job jobB = Job.getInstance(conf, "Top Titles");
        jobB.setOutputKeyClass(Text.class);
        jobB.setOutputValueClass(IntWritable.class);

        jobB.setMapOutputKeyClass(NullWritable.class);
        jobB.setMapOutputValueClass(TextArrayWritable.class);

        jobB.setMapperClass(TopTitlesMap.class);
        jobB.setReducerClass(TopTitlesReduce.class);
        jobB.setNumReduceTasks(1);

        FileInputFormat.setInputPaths(jobB, tmpPath);
        FileOutputFormat.setOutputPath(jobB, new Path(args[1]));

        jobB.setInputFormatClass(KeyValueTextInputFormat.class);
        jobB.setOutputFormatClass(TextOutputFormat.class);

        jobB.setJarByClass(TopTitles.class);
        return jobB.waitForCompletion(true) ? 0 : 1;
    }

    public static String readHDFSFile(String path, Configuration conf) throws IOException{
        Path pt=new Path(path);
        FileSystem fs = FileSystem.get(pt.toUri(), conf);
        FSDataInputStream file = fs.open(pt);
        BufferedReader buffIn=new BufferedReader(new InputStreamReader(file));

        StringBuilder everything = new StringBuilder();
        String line;
        while( (line = buffIn.readLine()) != null) {
            everything.append(line.toLowerCase());
            everything.append("\n");
        }
        return everything.toString();
    }

    /**
     * Stupid class
     */
    public static class TextArrayWritable extends ArrayWritable {
        public TextArrayWritable() {
            super(Text.class);
        }

        public TextArrayWritable(String[] strings) {
            super(Text.class);
            Text[] texts = new Text[strings.length];
            for (int i = 0; i < strings.length; i++) {
                texts[i] = new Text(strings[i]);
            }
            set(texts);
        }
    }

    public static class TitleCountMap extends Mapper<Object, Text, Text, IntWritable> {
        Set<String> stopWords = new HashSet<String>();
        String delimiters;

        @Override
        protected void setup(Context context) throws IOException,InterruptedException {

            Configuration conf = context.getConfiguration();

            String delimitersPath = conf.get("delimiters");
            delimiters = readHDFSFile(delimitersPath, conf);
            
            String stopWordsPath = conf.get("stopwords");
            List<String> stopWordsList = Arrays.asList(readHDFSFile(stopWordsPath, conf).split("\n"));
            for(String e : stopWordsList){
                stopWords.add(e);
            }
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer stk = new StringTokenizer(value.toString(),delimiters);
            while(stk.hasMoreTokens()){
                String e = stk.nextToken().trim().toLowerCase();
                if(stopWords.contains(e) == false){
                    context.write(new Text(e),new IntWritable(1));
                }
            }
        }
    }

    public static class TitleCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable e : values){
                sum += e.get();
            }
            context.write(key, new IntWritable(sum));
        }
    }

    public static class TopTitlesMap extends Mapper<Text, Text, NullWritable, TextArrayWritable> {
        Integer N;
        List<Pair<Integer,String>> toSort = new ArrayList<>();
        
        @Override
        protected void setup(Context context) throws IOException,InterruptedException {
            Configuration conf = context.getConfiguration();
            this.N = conf.getInt("N", 10);
        }

        /**
         * Swap key-value
         * @param key
         * @param value
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            // Map 2
            Pair<Integer,String> p = new Pair<>(Integer.valueOf(value.toString()), key.toString());
            toSort.add(p);
        }

        /**
         * Sort, emits no more than N pairs
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            Collections.sort(toSort, Collections.reverseOrder());
            int copySize = Math.min(toSort.size(), N);
            String[] stringArray = new String[copySize];
            for(int i = 0;i<copySize;i++){
                stringArray[i] = toSort.get(i).toString();
            }
            context.write(NullWritable.get(), new TextArrayWritable(stringArray));
        }
    }

    public static class TopTitlesReduce extends Reducer<NullWritable, TextArrayWritable, Text, IntWritable> {
        Integer N;

        @Override
        protected void setup(Context context) throws IOException,InterruptedException {
            Configuration conf = context.getConfiguration();
            this.N = conf.getInt("N", 10);
        }

        /**
         * Sort
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void reduce(NullWritable key, Iterable<TextArrayWritable> values, Context context) throws IOException, InterruptedException {
            // Reduce 2, single reducer
            List<Pair<Integer,String>> toSort = new ArrayList<>();
            
            for(TextArrayWritable arr : values){
                for(Writable w : arr.get()){
                    String e = ((Text)w).toString();
                    Pair<Integer,String> p = Pair.fromString(e);
                    toSort.add(p);
                }
            }
            Collections.sort(toSort);
            
            for(Integer i = 0; i<N;i++){
                context.write(new Text(toSort.get(i).second), new IntWritable(toSort.get(i).first));
            }
        }
    }

}

class Pair<A extends Comparable<? super A>,
        B extends Comparable<? super B>>
        implements Comparable<Pair<A, B>> {

    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A extends Comparable<? super A>,
            B extends Comparable<? super B>>
    Pair<A, B> of(A first, B second) {
        return new Pair<A, B>(first, second);
    }

    @Override
    public int compareTo(Pair<A, B> o) {
        int cmp = o == null ? 1 : (this.first).compareTo(o.first);
        return cmp == 0 ? (this.second).compareTo(o.second) : cmp;
    }

    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second);
    }

    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((Pair<?, ?>) obj).first)
                && equal(second, ((Pair<?, ?>) obj).second);
    }
    
    private boolean equal(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * Added
     * @param input
     * @return
     */
    public static Pair<Integer, String> fromString(String input){
        String arr[] = input.split("\\(|\\)|,");       
        return new Pair<Integer, String>(Integer.parseInt(arr[1]),arr[2]);
    }
        
    @Override
    public String toString() {
        return "(" + first + "," + second + ')';
    }
}