import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;

import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Get;


import org.apache.hadoop.hbase.util.Bytes;

public class TablePartD{

   public static void main(String[] args) throws IOException {

	//TODO 


						 //      Instantiating   Configuration   class
                                                Configuration   config  =       HBaseConfiguration.create();
                                                //      Instantiating   HTable  class
                                                HTable  table   =       new     HTable(config,  "powers");
                                                //      Instantiating   Get     class
                                                Get     g       =       new     Get(Bytes.toBytes("row1"));
                                                //      Reading the     data
                                                Result  result  =       table.get(g);
                                                //      Reading values  from    Result  class   object
                                                byte    []      value           =       result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero"));
                                                byte    []      value1  =       result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("power"));
                                                byte    []      value2  =       result.getValue(Bytes.toBytes("professional"),Bytes.toBytes("name"));
                                                byte    []      value3  =       result.getValue(Bytes.toBytes("professional"),Bytes.toBytes("xp"));
                                                byte    []      value4  =       result.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color"));

                                                //      Printing        the     values
                                                String  hero    =       Bytes.toString(value);
                                                String  power   =       Bytes.toString(value1);
                                                String  name    =       Bytes.toString(value2);
                                                String  xp      =       Bytes.toString(value3);
                                                String  color   =       Bytes.toString(value4);


                                                System.out.println("hero:       "       +       hero    +       "       power:  "       +       power+  "       name:   "       +       name$





                                                Get     h       =       new     Get(Bytes.toBytes("row19"));
                                                //      Reading the     data
                                                Result  r       =       table.get(h);
                                                //      Reading values  from    Result  class   object
                                                byte    []      a               =       r.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero"));
                                                byte    []      b       =       r.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color"));

                                                //      Printing        the     values
                                                String  hero1   =       Bytes.toString(a);
								     String  color1  =       Bytes.toString(b);

                                                System.out.println("hero:       "       +       hero1   +       "       color:  "       +       color1);



                                                Get     i       =       new     Get(Bytes.toBytes("row1"));
                                                //      Reading the     data
                                                Result  rs      =       table.get(i);
                                                //      Reading values  from    Result  class   object
                                                byte    []      c               =       rs.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero"));
                                                byte    []      d       =       rs.getValue(Bytes.toBytes("professional"),Bytes.toBytes("name"));
                                                byte    []      e       =       rs.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color"));

                                                //      Printing        the     values
                                                String  x       =       Bytes.toString(c);

                                                String  y       =       Bytes.toString(d);
                                                String  z       =       Bytes.toString(e);


                                                System.out.println("hero:       "       +       x       +       "       name:   "       +       y+              "       color:  "       +   $



   }
}





