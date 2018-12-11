import java.io.IOException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

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

import org.apache.hadoop.hbase.util.Bytes;

public class TablePartC{

   public static void main(String[] args) throws IOException {

	//TODO   
						 //      Instantiating   Configuration   class
                                                Configuration   config  =       HBaseConfiguration.create();
                                                //      Instantiating   HTable  class
                                                HTable  hTable  =       new     HTable(config,  "powers");
                                                //      Instantiating   Put     class
                                                //      accepts a       row     name.
                                                        Put     q       =       new     Put(Bytes.toBytes("Row1"));
                                                //      adding  values  using   add()   method
                                                //      accepts column  family  name,   qualifier/row   name    ,value

                                        String[] splitData = {};
                                        FileReader fr = new FileReader(("input.csv"));
                                        BufferedReader br = new BufferedReader(fr);
                                        String scanner = br.readLine();
                                        while (scanner!=null){
                                                if(scanner!=null){
                                                splitData = scanner.split("\\s*,\\s* ");
                                                //splitData = scanner.split(" ");

                                                        for (int i = 0; i < splitData.length; i++) {
                                                                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {

                                                                        //System.out.println(splitData[i]);
                                                                        Put p=new Put(Bytes.toBytes(splitData[0].trim()));
                                                                        if(i==1)        p.add(Bytes.toBytes("personal"),Bytes.toBytes("hero"),Bytes.toBytes(splitData[1].trim()));
                                                                        //System.out.println(splitData[1]);
                                                                        if(i==2)        p.add(Bytes.toBytes("personal"),Bytes.toBytes("power"),Bytes.toBytes(splitData[2].trim()));
                                                                        if(i==3)        p.add(Bytes.toBytes("professional"),Bytes.toBytes("name"),Bytes.toBytes(splitData[3].trim()));
                                                                        if(i==4)        p.add(Bytes.toBytes("professional"),Bytes.toBytes("xp"),Bytes.toBytes(splitData[4].trim()));
                                                                        if(i==5)        p.add(Bytes.toBytes("custom"),Bytes.toBytes("color"),Bytes.toBytes(splitData[5].trim()));

                                                                       q=p;
                                                                        // hTable.put(p);
                                                                        //System.out.println(splitData[i]);
                                                                }

                                                        }
                                                }
                                          scanner = br.readLine();

                                          }

                                        br.close();



                                                //      Saving  the     put     Instance        to      the     HTable.
                                               hTable.put(q);
                                                System.out.println("data        inserted");
                                                //      closing HTable
                                                hTable.close();
   }
}