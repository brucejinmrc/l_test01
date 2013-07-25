/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package mysql;
import java.sql.*;

import org.apache.log4j.Logger;
 
public class JdbcSSL {
	
	 static Logger log = Logger.getLogger(JdbcSSL.class);
	 
	public static void main(String args[]) {
		
		//After enabled MySQL for SSL, the following need to be coded correctly
		System.setProperty("javax.net.debug", "all");
		System.setProperty("javax.net.ssl.keyStore","c:/temp/mysql-ssl/keystore");
		System.setProperty("javax.net.ssl.keyStorePassword","mpower");
		System.setProperty("javax.net.ssl.trustStore","c:/temp/mysql-ssl/truststore");
		System.setProperty("javax.net.ssl.trustStorePassword","mpower");
		
			Connection conn = null;

			try {

				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver loaded.");
				
				//String sys = "jdbc:mysql://localhost/test";			
				String sys = "jdbc:mysql://localhost/test?verifyServerCertificate=false&useSSL=true";	
				//String sys = "jdbc:mysql://localhost/test?verifyServerCertificate=false&useSSL=true&requireSSL=true";	
				conn = DriverManager.getConnection(sys, "root", "mrc");
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				String sql = "SELECT * FROM mrcappllib.mrcdct88";
				Statement ps = conn.createStatement();
				ResultSet rs = ps.executeQuery(sql);
				while (rs.next()) {
					System.out.println("1: " + rs.getString(1));
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

          
}
