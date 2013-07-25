/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package oracle;

import java.sql.*;
/**
 *
 */

public class TableMeta {

	public static void main(String args[]) {
		Connection conn =  null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded for Oracle.");
			String sys = "jdbc:oracle:thin:@72.16.169.182:1521/XE";
			conn = DriverManager.getConnection(sys, "mrc", "mrccorp11");
			System.out.println("Connecting to: " + sys);
			DatabaseMetaData meta = conn.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = meta.getColumns(null, "MRCDATES", "TESTDATES", null);
			while (rs.next()) {
				String s1 = rs.getString("TABLE_NAME");
				String s2 = rs.getString("COLUMN_NAME");
				String s3 = rs.getString("DATA_TYPE");
			 
				System.out.println(s1 + ", " + s2 + ", " + s3 + ",  " );
			}
			rs.close();
			conn.close();

		} catch (SQLException exc) {
			System.out.println("Error:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					System.out.println("connection closed.");
				}
			} catch (SQLException exc) {
			}
		}
	} // main
}
