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

import org.apache.hadoop.hbase.util.Bytes;

public class TablePartE{

   public static void main(String[] args) throws IOException {

	//TODO    

						//	Instantiating	Configuration	class
						Configuration	config	=	HBaseConfiguration.create();
						//	Instantiating	HTable	class
						HTable	table	=	new	HTable(config,	"powers");
						//	Instantiating	the	Scan	class
						Scan	scan	=	new	Scan();
						//	Scanning	the	required	columns
						scan.addColumn(Bytes.toBytes("personal"),	Bytes.toBytes("hero"));
						scan.addColumn(Bytes.toBytes("personal"),	Bytes.toBytes("power"));
						scan.addColumn(Bytes.toBytes("professional"),	Bytes.toBytes("name"));
						scan.addColumn(Bytes.toBytes("professional"),	Bytes.toBytes("xp"));
						scan.addColumn(Bytes.toBytes("custom"),	Bytes.toBytes("color"));

						//	Getting	the	scan	result
						ResultScanner	scanner	=	table.getScanner(scan);
						//	Reading	values	from	scan	result
						for	(Result	result	=	scanner.next();	result	!=	null;	result	=	scanner.next())
						System.out.println("Found	row	:	"	+	result);
						//closing	the	scanner
						scanner.close();  
	
   }
}

