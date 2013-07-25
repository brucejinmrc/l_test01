/*
 * Created on Jan 6, 2004
 * Create a table in oracle
 */
package oracle;
import java.sql.*;
import java.io.*;


public class ListDCT {

	public static void main(String args[]) {
		 
		Connection    conn = null;;
		try {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
	            System.out.println("Driver loaded.");
	 
	            String sys = "jdbc:oracle:thin:@mrcwin:1521:testdb";
	                   sys = "jdbc:oracle:thin:@72.16.169.182:1521/XE";
	        conn =  DriverManager.getConnection(sys, "mrc", "mrccorp11");
			DatabaseMetaData dbMeta = conn.getMetaData();
			
			/** List DCT */
			ResultSet rs = null;
			/** MSSQLServer */
			//rs = dbMeta.getCatalogs();
			/** Oracle, DB2/net */
			rs = dbMeta.getSchemas();
				 
			while (rs.next()) {
				String sche = rs.getString(1)  ;
				System.out.println("dct=" + sche);	 
		   }
			
			/** List Tables */
			String[] types = {"TABLE"};
			/** Oracle, DB2/net */
			//rs = dbMeta.getTables(null, "MRCJAVALIB", null, types);
			
			/** MSSQLServer */
			rs = dbMeta.getTables("MRCJAVALIB", null,null, types);
	 	
			while (rs.next()) {
				System.out.println( "table: " + rs.getString("TABLE_NAME")
					+  "   Desc: " + rs.getString("REMARKS") );
			}
			
			/** List Fields */
			/** Oracle, DB2/net */
			//rs = dbMeta.getColumns(null, "MRCJAVALIB", "MRCDCT15", null);
			/** MSSQLServer */
			rs = dbMeta.getColumns("MRCJAVALIB", null, "MRCDCT15", null);
			while (rs.next()) { 
				System.out.print("Table:" + rs.getString("TABLE_NAME") );
				System.out.print("  col name:   " + rs.getString("COLUMN_NAME") );
				System.out.print("  type:   " + rs.getString("DATA_TYPE") );
				System.out.print("  len:   " + rs.getString("COLUMN_SIZE") );
				System.out.println("  dec:   " + rs.getInt("DECIMAL_DIGITS") );
			}
		 
			rs.close();
			conn.close();

		} catch (ClassNotFoundException exc) {
			System.out.println("Error: " + exc.getMessage());
	 
		} catch (SQLException exc) {
			System.out.println("Error: " + exc.getMessage());
		}  

	} //main                

}
