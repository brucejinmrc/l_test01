/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package informix;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// chg
public class Create {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {//
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				System.out.println("Driver loaded.");
				String sys = "jdbc:derby://localhost:1527/mrcdb";		
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				System.out.println("Connecting to: " + sys);
				
				List<String> dds = createDDs(conn, 200);
				for (String dd : dds) {
					List<String> tables = createTables(conn, dd, 20);
					for(String table : tables) {
						insertData(conn, dd, table, 500, 1);
					}
				}
				 
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
				}  catch (SQLException exc) {}
			}

		  
		} //main                


	static List<String> createDDs(Connection conn, int num) {

		List<String> dds = getDDs(num);
		try {
			Statement stmt = conn.createStatement();
			for (String dd : dds) {
				String sql = "CREATE SCHEMA " + dd + " ";
				int rr = stmt.executeUpdate(sql);
				System.out.println(sql);
			}
			stmt.close();

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}
		return dds;
	}
	
	static List<String> createTables(Connection conn, String DD, int num) {

		List<String> tables = getTables(num);
		try {
			Statement stmt = conn.createStatement();
			for (String table : tables) {
				String sql = "CREATE TABLE " + DD + "." + table + "(FLD1 CHARACTER(10),FLD2 INTEGER, FLD3 CHARACTER(20))";
				int rr = stmt.executeUpdate(sql);
				System.out.println(sql);
			}
			stmt.close();

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}
		return tables;
	}
	

	static void insertData(Connection conn, String DD, String table, int num, int start) {

		int pp = 1;
		try {
			Statement stmt = conn.createStatement();
			for (int i = 0; i < num; i++) {
				int ii = i + start;
				String v1 = "CC" + ii;
				String v3 = "VV" + ii;
				String sql = "INSERT INTO " + DD + "." + table 
						+ " VALUES('" + v1 + "'," + ii + ",'" + v3 + "')";
				int rr = stmt.executeUpdate(sql);
				System.out.println(sql);
			}
			stmt.close();

		} catch (SQLException exc) {
			System.out.println("Err:'" + exc.getMessage() + "'");
			exc.printStackTrace();
		}
	 
	}

	static List<String> getDDs(int num) {
		List<String> dds = new ArrayList<String>();
		for(int i=0; i <num; i++) {
			String dd = "MRCLIB" + i;
			dds.add(dd);
		}
		return dds;
	}
	static List<String> getTables(int num) {
		List<String> tables = new ArrayList<String>();
		for(int i=0; i <num; i++) {
			String table = "MRCTABLE" + i;
			tables.add(table);
		}
		return tables;
	}
}
