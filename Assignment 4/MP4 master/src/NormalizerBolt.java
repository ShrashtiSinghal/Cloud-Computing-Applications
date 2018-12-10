import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;
import java.util.List;

/**
 * A bolt that normalizes the words, by removing common words and making them lower case.
 */
public class NormalizerBolt extends BaseBasicBolt {
  private List<String> commonWords = Arrays.asList("the", "be", "a", "an", "and",
      "of", "to", "in", "am", "is", "are", "at", "not", "that", "have", "i", "it",
      "for", "on", "with", "he", "she", "as", "you", "do", "this", "but", "his",
      "by", "from", "they", "we", "her", "or", "will", "my", "one", "all", "s", "if",
      "any", "our", "may", "your", "these", "d" , " ", "me" , "so" , "what" , "him" );

  @Override
  public void execute(Tuple tuple, BasicOutputCollector collector) {

    /*
    ----------------------TODO-----------------------
    Task:
     1. make the words all lower case
     2. remove the common words

    ------------------------------------------------- */


  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {

    declarer.declare(new Fields("word"));

  }
}
