/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package derby;

import java.sql.*;
 

public class Create2 {

	public static void main(String args[]) {
		//createDB("mrcderby");
		 test();
	} 
	
	public static void test( ) {
		Connection conn = null;

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			System.out.println("Driver loaded.");
			String sys = "jdbc:derby://localhost:1527/mrcdb;create=true";
			conn = DriverManager.getConnection(sys, "sa", "mrc");
			System.out.println("Connecting to: " + sys);
 
			
			//createDDs(conn, "TESTLIB");
			String sql = "CREATE TABLE TESTLIB.HOTELAVAILABILITY  (HOTEL_ID INT NOT NULL, BOOKING_DATE DATE NOT NULL," 
				   + "ROOMS_TAKEN INT DEFAULT 0, PRIMARY KEY (HOTEL_ID, BOOKING_DATE)) ";
				   
			//createTables(conn, "TESTLIB", sql);

		} catch (ClassNotFoundException exc) {
			System.out.println("Error: " + exc);
			exc.printStackTrace();
		} catch (SQLException exc) {
			System.out.println("Err :'" + exc.getMessage() + "'");
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

	} // main

	static void createDB( String name) {

		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			String sys = "jdbc:derby://localhost:1527/" + name + ";create=true";
			Connection conn = DriverManager.getConnection(sys, "sa", "mrc");
			conn.close();
			System.out.println("Created DB " + name);

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		} catch (ClassNotFoundException e) {
			 
			e.printStackTrace();
		}

	}
	
	static void createDDs(Connection conn, String name) {

		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE SCHEMA " + name;
			int rr = stmt.executeUpdate(sql);
			System.out.println("Created DB using:" + sql);
			stmt.close();

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}

	}

	static void createTables(Connection conn, String DD, String sql) {

		try {
			Statement stmt = conn.createStatement();
 
			int rr = stmt.executeUpdate(sql);
			System.out.println(sql);

			stmt.close();

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}

	}

}
