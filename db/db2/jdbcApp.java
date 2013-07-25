/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.sql.*;
 //chg 4
public class jdbcApp {
	
	public static void main(String args[]) {
			Connection conn = null;
			String driver = "COM.ibm.db2.jdbc.app.DB2Driver";
			try {
				Class.forName(driver);
				System.out.println("Driver " + driver + " loaded.");
				
				String sys = "jdbc:db2:SAMPLE";			
				conn = DriverManager.getConnection(sys);
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM MRC.EMPLOYEE";
				ResultSet rs = stmt.executeQuery(sql);  		
				while (rs.next()) {
					System.out.println( "id - " + rs.getString(1) 
						+ "    "  +  rs.getString(2)  );				 
				}
				
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException exc) {
				System.out.println("jdbc driver: " + driver +  " not found.");
			} catch (SQLException exc) {
				System.out.println("Error:'" + exc.getMessage() + "'");
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
