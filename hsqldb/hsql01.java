/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package hsqldb;
import java.sql.*;
 
public class hsql01 {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("org.hsqldb.jdbcDriver");
				System.out.println("Driver loaded.");
		 
				//String sys = "jdbc:hsqldb:file:C:/temp/hsqldb-2.2.8/hsqldb/mrc/mrcappllib";		
				//String sys = "jdbc:hsqldb:file:C:/temp/hsqldb-2.2.8/hsqldb/mrc/mrcdb;ifexists=true";
				String sys = "jdbc:hsqldb:hsql://localhost/mpowerdb;ifexists=true";	
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				System.out.println("Connecting to: " + sys);
				
				
				Statement stmt = conn.createStatement();
				//String sql = "CREATE SCHEMA MRCJAVA4 AUTHORIZATION DBA";
				//String sql = "CREATE TABLE MRCAPPLLIB.mrctable1 (fld1 char(10), fld2 int)";
				String sql = "INSERT INTO MRCJAVA4.CUSTTABLE2 VALUES('aaXXX',YYY)";
				for (int i = 1; i < 4; i++) {
					String sql0 = sql.replaceFirst("XXX", ""+ i);
					sql0 = sql0.replaceFirst("YYY", ""+ i);
					int rr = stmt.executeUpdate(sql0);
					System.out.println(sql0);
				}
				
				sql = "CHECKPOINT";
				//int yrr = stmt.executeUpdate(sql);
				//System.out.println(yrr);
				
				stmt.close();
				 
			} catch (ClassNotFoundException exc) {
				System.out.println("Error: " + exc);
				exc.printStackTrace();
			} catch (SQLException exc) {
				System.out.println("connection failed with:'" + exc.getMessage() + "'");
				exc.printStackTrace();
			} finally {
				try {
				 	if (conn != null) {
				 		conn.close(); 
					    System.out.println("connection closed.");
				 	}
				}  catch (SQLException exc) {}
			}

		  
		} //main                


}
