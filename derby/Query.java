/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package derby;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class Query {
	
	 static Logger log = Logger.getLogger(Query.class);
	 
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				log.info("Driver loaded.");
				String sys = "jdbc:derby://localhost:1527/mrcdb";		
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				log.info("Connecting to: " + sys);
				test2(conn);
				/*for(int i=1; i<199; i++) {
					String DD = "MRCLIB" + i;
					String table = "MRCTABLE3";
					test1(conn, DD, table);
				}*/
				 
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

	public static void test2(Connection conn) {
		 
		try {
			Statement stmt = conn.createStatement();
			String sql = "Select * from  TESTLIB.TEST1";
			ResultSet rs  = stmt.executeQuery(sql);
			int nn = 0;
			while (rs.next()) {
				nn = nn +1;
				String xx = rs.getString(1) + ", " + rs.getString(2);
			}	
			//System.out.println("num=" + nn);
			stmt.close();
	 
		} catch (SQLException exc) {
			log.info("connection failed with:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}

	  
	} //main                

	public static void test1(Connection conn, String DD, String table) {
		 
		try {
			Statement stmt = conn.createStatement();
			String sql = "Select * from  " + DD + "." + table;
			ResultSet rs  = stmt.executeQuery(sql);
			int nn = 0;
			while (rs.next()) {
				nn = nn +1;
				String xx = rs.getString(1) + ", " + rs.getString(2);
			}	
			System.out.println("num=" + nn);
			stmt.close();
	 
		} catch (SQLException exc) {
			log.info("connection failed with:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}

	  
	} //main                
}
