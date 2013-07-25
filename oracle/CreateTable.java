/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package oracle;
import java.sql.*;
 
public class CreateTable {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Driver loaded.");
				
				String sys = "jdbc:oracle:thin:@72.16.169.182:1521:XE";			
				conn = DriverManager.getConnection(sys, "mrc", "mrccorp11");
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				String sql = "DROP TABLE mrcmpower.AAA";
				//stmt.executeUpdate(sql);  
				stmt.execute("alter session set NLS_DATE_FORMAT = 'yyyy-MM-dd'");
				
				sql = "CREATE TABLE mrcmpower.AAA(col1 CHAR(10), col2 DECIMAL(4,1), col3 date)";
				//stmt.executeUpdate(sql);  		
				 
				
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException exc) {
				System.out.println("jdbc driver: " + " not found.");
			} catch (SQLException exc) {
				System.out.println("SQL Error: " + exc.getMessage() );
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
