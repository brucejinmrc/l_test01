/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package hsqldb;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class hsqlQuery {
	
	 static Logger log = Logger.getLogger(hsqlQuery.class);
	 
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("org.hsqldb.jdbcDriver");
				log.info("Driver loaded.");
		 
				//String sys = "jdbc:hsqldb:file:C:/temp/hsqldb-2.2.8/hsqldb/mrc/mrcappllib";		
				//String sys = "jdbc:hsqldb:file:C:/temp/hsqldb-2.2.8/hsqldb/mrc/mrcdb;ifexists=true";	
				String sys = "jdbc:hsqldb:hsql://localhost:9001/mpowerdb2;ifexists=true";	
				//access remote HSQLDB:
				//String sys = "jdbc:hsqldb:hsql://192.168.0.170:9001/mpowerdb;ifexists=true";	

				conn = DriverManager.getConnection(sys, "sa", "mrc");
				log.info("Connecting to: " + sys);
	
				Statement stmt = conn.createStatement();
				String sql = "Select * from  MRCJAVA4.CUSTTABLE3 WHERE F2 <30";
				ResultSet rs  = stmt.executeQuery(sql);
				while (rs.next()) {
					System.out.println(rs.getString(1) + ", " + rs.getString(2));
				}
				
				sql = "Select * from  MRCLIB198.MRCTABLE2 ";
				rs  = stmt.executeQuery(sql);
				while (rs.next()) {
					System.out.println(rs.getString(1) + ", " + rs.getString(2));
				}
		 
				stmt.close();
				 
			} catch (ClassNotFoundException exc) {
				System.out.println("Error: " + exc);
				exc.printStackTrace();
			} catch (SQLException exc) {
				log.info("connection failed with:'" + exc.getMessage() + "'");
				exc.printStackTrace();
			} finally {
				try {
				 	if (conn != null) {
				 		conn.close(); 
				 		log.info("connection closed.");
				 	}
				}  catch (SQLException exc) {}
			}

		  
		} //main                


}
