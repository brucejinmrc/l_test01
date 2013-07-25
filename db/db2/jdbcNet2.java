/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.sql.*;
 //chg 5
public class jdbcNet2 {
	
	public static void main(String args[]) {
			Connection conn = null;
			String driver = "COM.ibm.db2.jdbc.net.DB2Driver";
			try {
				Class.forName(driver);
				System.out.println("Driver " + driver + " loaded.");
				
				//String sys = "jdbc:db2://192.168.0.152:6789/SAMPLE";	
				//String sys = "jdbc:db2://192.168.0.152/mrcdb";	
				String sys = "jdbc:db2://192.168.0.153/mrcdb2";	
				conn = DriverManager.getConnection(sys, "mrc","mrc");
				//bug: CLI0621E  Unsupported JDBC Server configuration
				System.out.println("Connecting to: " + sys);
				String sql = "SELECT * FROM MRCAPPLLIB.MRCPRFIL";
				PreparedStatement stmt = conn.prepareStatement(sql);
						//sql  ,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
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

			  System.out.println("end.");
		} //main                
	

}
