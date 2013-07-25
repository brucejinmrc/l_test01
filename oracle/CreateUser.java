/*
 * Created on Jan 6, 2004
 * Create a table in oracle
 */
package oracle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateUser {

	public static void main(String args[]) {
		 
		Connection conn = null;
		try {
		    Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded.");
		 
			String sys = "jdbc:oracle:thin:192.168.0.153:1521:mrcora";			
			 conn = DriverManager.getConnection(sys, "mrc", "mrc"); 
			//Connection conn = driver.getConnection();
 
			Statement stmt = conn.createStatement();
			String user = "";
			
			String sql = "DROP USER " + user ;
			//stmt.executeUpdate(sql);
			System.out.println("User " + user + " dropped.");
			
			sql = "CREATE USER " + user 
						+ " identified by mrc default tablespace users temporary tablespace temp";
			stmt.executeUpdate(sql);
			System.out.println("User " + user + " created.");
			
			sql = "alter user " + user + " quota unlimited on users " ;
			stmt.executeUpdate(sql);
			System.out.println("Granted " + user + " quota.");
			stmt.close();
	 
		} catch (ClassNotFoundException exc) {
			System.out.println("jdbc driver: " + " not found.");
		 
		} catch (SQLException exc) {
			System.out.println("SQL Error: " + exc.getMessage());
		} finally {
		    try {
			conn.close();
		    } catch (Exception ee ) {
		        
		    }
		}

	} //main                

}
