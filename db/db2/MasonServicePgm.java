/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package db.db2;
import java.math.BigDecimal;
import java.sql.*;
 
public class MasonServicePgm {   

    public static void main(String[] args) {
        Connection conn = getConnection();
        disconnect(conn);
    }
    
	/***********************************************************
	 *  Get a connection to connect to AS400.
	 **********************************************************/
	public static Connection getConnection() {
			 
		String driver = "com.ibm.as400.access.AS400JDBCDriver";
		String url = "jdbc:as400://10.10.16.1;naming=system;trace=true;translate binary=true;libraries=MRCCUST,ARLIBR,QS36F";		
		System.out.println( " Driver=" + driver	+ "  url=" + url   );		
		Connection conn = null;
		 
		try {
			Class.forName(driver);
			System.out.println("Driver " + driver + " loaded.");
				
			conn = DriverManager.getConnection(url, "MRC", "mP0wer");
			System.out.println("Connecting to: " + url);		
			Statement stmt = conn.createStatement();

		 
			//String sql = "SELECT Cust_GetCost2(1,1) FROM MRCCUST/TEMP1";
			String sql = "SELECT BRUCE2(1,1) FROM MRCCUST/TEMP1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String sss = rs.getString(1);
				System.out.println(sss);
			}
			stmt.close();
			System.out.println("Query done");	
		} catch (ClassNotFoundException exc) {
			System.out.println("jdbc driver: " + driver +  " not found.");
		} catch (SQLException exc) {
			System.out.println("Error:'" + exc.getMessage() + "'");
			exc.printStackTrace();
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
