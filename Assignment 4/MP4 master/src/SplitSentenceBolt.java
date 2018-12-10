
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitSentenceBolt extends BaseBasicBolt {

@Override
   public void execute(Tuple tuple, BasicOutputCollector collector) {
       String sentence = tuple.getString(0);
       String[]words=sentence.split("[\\s~`!@#$%^&*(-)+=_:;'\",.<>?/\\\\0-9"+"\\]\\[\\}\\{]+");

       for(String word:words){
         collector.emit(new Values(word));
       }
   }
   @Override
   public void declareOutputFields(OutputFieldsDeclarer declarer) {
     declarer.declare(new Fields("word"));
   }
 }
