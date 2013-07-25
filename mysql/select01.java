/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package mysql;
import java.sql.*;
 
public class select01 {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver loaded.");
				
				String sys = "jdbc:mysql://localhost/test?user=root&password=mrc";			
				conn = DriverManager.getConnection(sys);
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM orders";
				ResultSet rs = stmt.executeQuery(sql);  	
				String s = "";	
				while (rs.next()) {
					String x = rs.getString(1) ;
					s += x + "-";
					System.out.println( "id - " + x 
						+ "    "  +  rs.getString(2)  );				 
				}
				System.out.println( "s = " + s );
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException exc) {
				System.out.println("Error: " + exc);
				exc.printStackTrace();
			} catch (SQLException exc) {
				System.out.println("connection failed with:'" + exc.getMessage() + "'");
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
