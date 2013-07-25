/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package mysql;
import java.sql.*;
 
public class mysql02 {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver loaded.");
				
				String sys = "jdbc:mysql://192.168.0.131/test:testdb";			
				conn = DriverManager.getConnection(sys, "root", "mrc");
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM test.customer";
				ResultSet rs = stmt.executeQuery(sql);  	
				String s = "";	
				while (rs.next()) {
					String x = rs.getString(1) ;
					s += x + "-";
					System.out.println( "id - " + x 
						+ "    "  +  rs.getString(2)  );				 
				}
				
				/*System.out.println( "get table from another db:"); 
				sql = "SELECT * FROM test.tree";
				rs = stmt.executeQuery(sql);  	
				while (rs.next()) {
					String x = rs.getString(1) ;
					s += x + "-";
					System.out.println( "id - " + x 
					+ "    "  +  rs.getString(2)  );				 
				}*/
				stmt.close();
				conn.close();
				
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
