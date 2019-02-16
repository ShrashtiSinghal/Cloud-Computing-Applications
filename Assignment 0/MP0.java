
import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MP0 {
    Random generator;
    String userName;
//  static  String delimiters = " \t,;.?!-:@[](){}_*/";
  static  String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
    
  static List<String> stop =  Arrays.asList(stopWordsArray);

    public MP0(String userName) {
        this.userName = userName;
    }


    public Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        long longSeed = Long.parseLong(this.userName);
        this.generator = new Random(longSeed);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public String[] process() throws Exception{
    	String[] topItems = new String[20];
        Integer[] indexes = getIndexes();

    	//TO DO

    	try
    	{
    		ArrayList<String> allList = new ArrayList();
    		HashMap<String,Integer> hm = new HashMap<String,Integer>();
    		HashMap<String,Integer> finalMap = new HashMap<String,Integer>();
        	//FileReader fr = new FileReader(new File("filefolder\\input1.txt")); 
	InputStreamReader fr =new InputStreamReader(System.in);        	
	BufferedReader br = new BufferedReader(fr);
        //	String[] topItems = new String[20];
            //Integer[] indexes = getIndexes();
    	String line = "";
    	  
    	do
    	{
    	line = br.readLine();
    //	System.out.println("line "+line);
    	if(line!=null && ((line=line.trim())!=""))
    	 {
    		//  \t,;.?!-:@[](){}_*/
    	line = line.replaceAll("[\\.||,||?||\t||;||!||-||:||@||_||*||-]", " ");
    	 line = line.replaceAll("\\(", " ");
    	 line = line.replaceAll("\\)", " ");
    	 line = line.replaceAll("\\[", " ");
    	 line = line.replaceAll("\\]", " ");
    	 line = line.replaceAll("\\{", " ");
    	 line = line.replaceAll("\\}", " ");
    	 
    	 
    		line = line.trim();
    	  if(line.contains(" "))
    	    {
    		  String allLines[] = line.split(" ");
    		  for(String word:allLines)
    		  {
    			  if(!word.trim().isEmpty())
    			  allList.add(word.toLowerCase());
    		  }
  
    	  }
    	  else
    	  {
    		  if(!line.trim().isEmpty())
    		  allList.add(line.toLowerCase());
    	  }
    			
        	}
    		
    	}
    	while(line!=null);
    	
    //	System.out.println(allList);
    //	System.out.println(allList.size());
    	
    	allList.removeAll(stop);
    	 //	System.out.println(allList);
    //	System.out.println(allList.size());
    	
    	int count = 0;
    	for(String seperateWord : allList)
    	{
    		count = 0;
    		if(hm.containsKey(seperateWord))
    		{
    			count = hm.get(seperateWord);
    			count = count+1;
    			hm.put(seperateWord, count);
    		}
    		else
    			hm.put(seperateWord,++count);
    		
    	}
    	
    	//System.out.println(hm);
    	
    	
    	
    	//for(int newCount = 0;newCount<100000;newCount++)
    	//{
    		// finalMap.put(key, value)
    	//}
    	//for (int j : indexes) {}
    	
    	

ArrayList reverslist = new ArrayList<>(hm.entrySet());
    		Collections.sort(reverslist, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1,
                                   Map.Entry<String, Integer> o2) {
                    return (o2.getValue()).compareTo(o1.getValue());
                }
            });

  // System.out.println(reverslist);
    		
   String index = "";
   for(int rotate = 0;rotate<reverslist.size();rotate++)
   {
	   if(rotate == 20)
		   break;
	   String keyValue = reverslist.get(rotate).toString();
	   topItems[rotate]=keyValue.substring(0, keyValue.indexOf("="));
	
   }
    	//System.out.println(Arrays.toString(topItems));
    	}
    	catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    	
    	
    	
        
        


		return topItems;
    }

    public static void main(String args[]) throws Exception {
    	
    	if (args.length < 1){
    		System.out.println("missing the argument");
    	}
    	else{
    		String userName = args[0];
	    	MP0 mp = new MP0(userName);
	    	String[] topItems = mp.process();

	        for (String item: topItems){
	            System.out.println(item);
	        }
	    }

	}

}
