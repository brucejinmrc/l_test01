/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package oracle;

import java.sql.*;
/**
 * for DESC columns 
 * rs.getString("COLUMN_NAME") give SYS_NC000nn$  !!
 * rs.getString("ASC_OR_DESC") give null !
 
 *
 */

public class IndexInfo {

	public static void main(String args[]) {
		Connection conn =  null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded for Oracle.");
			String sys = "jdbc:oracle:thin:@72.16.169.182:1521/XE";
			conn = DriverManager.getConnection(sys, "mrc", "mrccorp11");
			System.out.println("Connecting to: " + sys);
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getIndexInfo(null, "MRCMPOWER", "MRCDCT15", false, true);
			while (rs.next()) {
				String s1 = rs.getString("INDEX_NAME");
				String s2 = rs.getString("COLUMN_NAME");
				String s3 = rs.getString("ASC_OR_DESC");
				String s4 = rs.getString("ORDINAL_POSITION");
				System.out.println(s1 + ", " + s2 + ", " + s3 + ",  " + s4);
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
