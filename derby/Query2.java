/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package derby;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class Query2 {
	
	 static Logger log = Logger.getLogger(Query2.class);
	 
	public static void main(String args[]) {
			Connection conn = null;

			try {
				//No need tp load driver!. It is in JDK 6
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				log.info("Driver loaded.");
				String sys = "jdbc:derby://localhost:1527/mrcdb";		
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				log.info("Connecting to: " + sys);
				Statement stmt = conn.createStatement();
				String sql = "Select * from  mrcmpower.MRCDCT15 ";

				ResultSet rs  = stmt.executeQuery(sql);
				int nn = 0;
				while (rs.next()) {
					nn = nn +1;
					String xx = rs.getString(1) + ", " + rs.getString(2);
					//System.out.println(nn + " xx=" + xx);
					if (nn > 1000) {
						break;
					}
				}	
				System.out.println("num=" + nn);
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
