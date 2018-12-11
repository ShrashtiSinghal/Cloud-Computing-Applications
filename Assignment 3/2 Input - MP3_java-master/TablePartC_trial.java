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
						//	Instantiating	Configuration	class
						Configuration	config	=	HBaseConfiguration.create();
						//	Instantiating	HTable	class
						HTable	hTable	=	new	HTable(config,	"powers");
						//	Instantiating	Put	class
						//	accepts	a	row	name.
						Put	p	=	new	Put(Bytes.toBytes("row1"));
						//	adding	values	using	add()	method
						//	accepts	column	family	name,	qualifier/row	name	,value

						p.add(Bytes.toBytes("personal"),
						Bytes.toBytes("hero"),Bytes.toBytes("yes"));
						p.add(Bytes.toBytes("personal"),
						Bytes.toBytes("power"),Bytes.toBytes("fly"));
						p.add(Bytes.toBytes("professional"),Bytes.toBytes("name"),Bytes.toBytes("Batman"));
						p.add(Bytes.toBytes("professional"),Bytes.toBytes("xp"),Bytes.toBytes("100"));
						p.add(Bytes.toBytes("custom"),Bytes.toBytes("color"),Bytes.toBytes("Black"));
						
						Put	q	=	new	Put(Bytes.toBytes("row1"));
						q.add(Bytes.toBytes("personal"),
						Bytes.toBytes("hero"),Bytes.toBytes("yes"));
						q.add(Bytes.toBytes("personal"),
						Bytes.toBytes("power"),Bytes.toBytes("fly"));
						q.add(Bytes.toBytes("professional"),Bytes.toBytes("name"),Bytes.toBytes("Batman"));
						q.add(Bytes.toBytes("professional"),Bytes.toBytes("xp"),Bytes.toBytes("100"));
						q.add(Bytes.toBytes("custom"),Bytes.toBytes("color"),Bytes.toBytes("Black"));

						//	Saving	the	put	Instance	to	the	HTable.
						hTable.put(p);
						hTable.put(q);
						System.out.println("data	inserted");
						//	closing	HTable
						hTable.close();
   }
}

