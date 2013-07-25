/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package mysql;

import java.sql.*;

public class mysql04 {

	public static void main(String args[]) {
		for (int i = 0; i < 1; i++) {
		mysql04 my = new mysql04();
		my.test();
		}
		
	} // main
	
	void test() {
		
		long t0 = System.currentTimeMillis();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded.");

			// String sys = "jdbc:mysql://instance9901.db.xeround.com:6755/MySQL";
			String sys = "jdbc:mysql://107.21.238.19:6755/MySQL";
			conn = DriverManager.getConnection(sys, "MRC", "abc123");
			// String sys = "jdbc:mysql://72.16.169.182/mysql";
			// conn = DriverManager.getConnection(sys, "root", "mrccorp11");
			System.out.println("Connecting to: " + sys);
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM mrcappllib.mrcdct88";
			// PreparedStatement ps = conn.prepareStatement(sql);
		
			for (int i = 0; i < 10; i++) {
				query(conn, stmt);
				// queryp(conn, ps);
			}
			System.out.println("time= " + new java.util.Date());

			conn.close();

		} catch (ClassNotFoundException exc) {
			System.out.println("Error: " + exc);
			exc.printStackTrace();
		} catch (SQLException exc) {
			System.out.println("connection failed with:'" + exc.getMessage()
					+ "'");
			exc.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					System.out.println("connection closed.");
				}
			} catch (SQLException exc) {
			}
		}

		long t1 = System.currentTimeMillis();
		System.err.println("conn/disconn: " + (t1 - t0));
	}

	static 	int tot = 0;
	static void queryp(Connection conn, PreparedStatement pstmt) {

		try {

			long t0 = System.currentTimeMillis();
			// Statement stmt = conn.createStatement();

			ResultSet rs = pstmt.executeQuery();

			String s = "";

			while (rs.next()) {
				String x = rs.getString(1);
				s += x + "-" + rs.getString(2);
				s += "-" + rs.getString(3);
				s += "-" + rs.getString(4);
				s += "-" + rs.getString(5);
				// System.out.println( "id - " + x + "    " );
			}

			long t1 = System.currentTimeMillis();
			tot += (t1 - t0);
			System.err.println((t1 - t0) + ",  total=" + tot);
			// stmt.close();

		} catch (SQLException exc) {
			System.out.println("connection failed with:'" + exc.getMessage()
					+ "'");
			exc.printStackTrace();
		}
	}

	static void query(Connection conn, Statement stmt) {

		try {

			long t0 = System.currentTimeMillis();
			// Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM mrcappllib.mrcdct88";
			ResultSet rs = stmt.executeQuery(sql);

			String s = "";

			while (rs.next()) {
				String x = rs.getString(1);
				s += x + "-" + rs.getString(2);
				s += "-" + rs.getString(3);
				s += "-" + rs.getString(4);
				s += "-" + rs.getString(5);
				// System.out.println( "id - " + x + "    " );
			}

			long t1 = System.currentTimeMillis();
			tot += (t1 - t0);
			System.err.println((t1 - t0) + ",  total = " + tot);

		} catch (SQLException exc) {
			System.out.println("connection failed with:'" + exc.getMessage()
					+ "'");
			exc.printStackTrace();
		}
	}

}
