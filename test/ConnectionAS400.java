/*
 * ConnectionAlone.java   Created on Jan 28, 2005, 2:44:18 PM
 *
 */
package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 *
 * @author jin
 */
public class ConnectionAS400 {
    
    /***********************************************************
	 *  Get a connection to connect to AS400.
	 * 
	 **********************************************************/
	public static Connection getConnection() {
		
		String driver = null;
		String url = null;
		
		String os = System.getProperty("os.name");
		
		if (os.indexOf("OS/400") >= 0) {
			driver = "com.ibm.db2.jdbc.app.DB2Driver";
			url = "jdbc:db2://*LOCAL" + ";translate binary=true;naming=system";			
		} else {	 
			driver = "com.ibm.as400.access.AS400JDBCDriver";
			url = "jdbc:as400://192.168.0.170;libraries=MRCJAVALIB:MRCAPPLLIB;translate binary=true;naming=system";	
		}
		
		System.out.println( " Driver=" + driver	+ "  url=" + url   );
		
		Connection conn = null;
		 
		try {
			Class.forName(driver);
			System.out.println("Driver " + driver + " loaded.");
				
			conn = DriverManager.getConnection(url, "jintest", "mrcuser001");
			System.out.println("Connecting to: " + url);
		 
		} catch (ClassNotFoundException exc) {
			System.out.println("jdbc driver: " + driver +  " not found.");
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

   

    public static void main(String[] args) {
        Connection conn = getConnection();
        disconnect(conn);
    }
}
