/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.sql.*;
 //op
public class as400update {   

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
		 
		int cnt = 0;
		try {
			Class.forName(driver);
			System.out.println("Driver " + driver + " loaded.");
				
			conn = DriverManager.getConnection(url, "", "");
			System.out.println("Connecting to: " + url);
			
			Statement stmt = conn.createStatement();
			 for (int i = 13001; i <32000; i++) {
				 String sql = "INSERT INTO MRCJAVALIB.LONGFIELD VALUES(" + i 
						 + ", 'a"  + i + "', 'b" + i + "')";
				 int aa = stmt.executeUpdate(sql);
				 
			 }
			
			stmt.close();
			conn.close();
		 
		} catch (ClassNotFoundException exc) {
			System.out.println("jdbc driver: " + driver +  " not found.");
		} catch (SQLException exc) {
			System.out.println("Error:'" + exc.getMessage() + "'");
		}  
		System.out.println("cnt=" + cnt);
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
