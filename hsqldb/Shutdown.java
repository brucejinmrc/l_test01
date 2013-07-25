/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package hsqldb;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class Shutdown {
	
	 static Logger log = Logger.getLogger(Shutdown.class);
	 
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("org.hsqldb.jdbcDriver");
				log.info("Driver loaded.");
				String sys = "jdbc:hsqldb:hsql://localhost:9001/mpowerdb2;ifexists=true";	
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				log.info("Connecting to: " + sys);
	
				Statement stmt = conn.createStatement();
				String sql = "SHUTDOWN";
				int sss = stmt.executeUpdate(sql);
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
