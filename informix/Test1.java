/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package informix;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
 

import java.io.PrintWriter;

import javax.annotation.PostConstruct;
 

public class Test1 {

	static Logger log = Logger.getLogger(Test1.class);

	public static void main(String args[]) {
		log.info("Start.");
		try {
			// No need tp load driver!. It is in JDK 6
			Class.forName("com.informix.jdbc.IfxDriver");// "org.apache.derby.jdbc.ClientDriver");
			log.info("Driver loaded.");
		 
			String sys = "jdbc:informix-sqli://192.168.0.74:9088/mrcdb1:INFORMIXSERVER=ol_ids_1150_1";
			Connection conn = DriverManager.getConnection(sys, "Informix", "abc123");
		 
		 

			log.info("Connecting to: " + sys);
			Statement stmt = conn.createStatement();
			String sql = "Select * from  brucelib1.table1";

			ResultSet rs = stmt.executeQuery(sql);
			int nn = 0;
			while (rs.next()) {
				nn = nn + 1;
				String xx = rs.getString(1) + ", " + rs.getString(2);
				// System.out.println(nn + " xx=" + xx);
				if (nn > 1000) {
					break;
				}
			}
			System.out.println("num=" + nn);
			stmt.close();

		} catch (ClassNotFoundException exc) {
			System.out.println("Error: " + exc);
			exc.printStackTrace();
		} catch (SQLException exc) {
			log.info("connection failed with:'" + exc.getMessage() + "'");
			exc.printStackTrace();

		}

		log.info("End.");
	} // main

	/**************************************************************
	 * @param dbname
	 *            C:/temp/derby/bin/mrcdb1
	 * @return connection
	 *************************************************************/
	public static Connection conn(String dbname) {

		Connection conn = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			log.info("Driver loaded.");
			String sys = "jdbc:derby:" + dbname;
			conn = DriverManager.getConnection(sys, "sa", "mrc");
		} catch (ClassNotFoundException exc) {
			log.error(exc.getMessage(), exc);
		} catch (SQLException exc) {
			log.error(exc.getMessage(), exc);
		}

		return conn;
	}

	/**************************************************************
	 * @param dbname
	 *            C:/temp/derby/bin/mrcdb1
	 * @return connection
	 *************************************************************/
	public static void closeConn(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) {
				return;
			}
			conn.close();
			log.info(conn + " closed");
		} catch (SQLException exc) {
			log.error(exc.getMessage(), exc);
		}

	}

	void start() {

	}
}
