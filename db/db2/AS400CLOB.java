/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.sql.*;
 //chg as400b in branch3
public class AS400CLOB {   

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
				
			conn = DriverManager.getConnection(url, "jintest", "mrcuser001");
			System.out.println("Connecting to: " + url);
			
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM MRCJAVA2.TEST6";
			ResultSet rs = stmt.executeQuery(sql);  	
			while (rs.next()) {
				cnt++;
				System.out.println( "id - " + rs.getString(1) 
			 	+ "    "  +  rs.getString(2)  );				 
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
