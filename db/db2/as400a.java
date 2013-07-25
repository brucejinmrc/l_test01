/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 //v11 chg 2 in master 3 test
public class as400a {   

    public static void main(String[] args) {
        Connection conn = getConnection(); 
        disconnect(conn);   
    }
    
	/***********************************************************
	 *  Get a connection to connect to AS400.
	 **********************************************************/
	public static Connection getConnection() {
			 
		String driver = "com.ibm.as400.access.AS400JDBCDriver";
		String url = "jdbc:as400://192.168.0.170;translate binary=true;secure=";		
		System.out.println( " Driver=" + driver	+ "  url=" + url   );		
		Connection conn = null;
		 
		try {
			Class.forName(driver);
			System.out.println("Driver " + driver + " loaded.");
				
			conn = DriverManager.getConnection(url, "jintest", "mrcuser001");
			System.out.println("Connecting to: " + url);
		 
		} catch (ClassNotFoundException exc) {
			System.out.println("jdbc driver: " + driver +  " not found.");
			exc.printStackTrace();
		} catch (SQLException exc) {
			System.out.println("Error:'" + exc.getMessage() + "'");
		}  
		
		return conn;
	}
	
	 /***********************************************************
	 *  Get a connection to connect to AS400.
	 * 
	 **********************************************************/
	public static void disconnect(Connection conn) {
 		 
		try {
		 if (conn != null) {
		     conn.close();
		     System.out.println("connection closed.");
		 }
		} catch (SQLException exc) {
			System.out.println("Error:'" + exc.getMessage() + "'");
		}  
 
	}

}
