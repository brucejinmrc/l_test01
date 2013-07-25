/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package postgres;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class JdbcSSL {
	
	 static Logger log = Logger.getLogger(JdbcSSL.class);
	 
	public static void main(String args[]) {
			Connection conn = null;

			try {

				String url = "jdbc:postgresql://72.16.169.182:5432/postgres?";
				url +=	"ssl=true&";
			    url +=	"sslfactory=org.postgresql.ssl.NonValidatingFactory";
			    
			    //connection failed with:'The server does not support SSL.'
			    
				String user = "sa";
				String password = "mrc";
					
				Class dbDriver = Class.forName("org.postgresql.Driver");	
				conn = DriverManager.getConnection(url, "postgres", "mrccorp11");
				log.info("Connecting to: " + url);
				
				for(int i=1; i<199; i++) {
					String DD = "MRCLIB" + i;
					String table = "MRCTABLE3";
					//test1(conn, DD, table);
				}
				 
			} catch (ClassNotFoundException exc) {
				System.out.println("Error: " + exc);
				exc.printStackTrace();
			} catch (SQLException exc) {
				log.info("connection failed with:'" + exc.getMessage() + "'");
				exc.printStackTrace();
			} finally {
				try {
				 	if (conn != null) {
				 		conn.close(); 
				 		log.info("connection closed.");
				 	}
				}  catch (SQLException exc) {}
			}

		  
		} //main                


	public static void test1(Connection conn, String DD, String table) {
		 
		try {
			Statement stmt = conn.createStatement();
			String sql = "Select * from  " + DD + "." + table;
			ResultSet rs  = stmt.executeQuery(sql);
			int nn = 0;
			while (rs.next()) {
				nn = nn +1;
				String xx = rs.getString(1) + ", " + rs.getString(2);
			}	
			System.out.println("num=" + nn);
			stmt.close();
	 
		} catch (SQLException exc) {
			log.info("connection failed with:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}

	  
	} //main                
}
