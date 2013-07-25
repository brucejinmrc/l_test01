/*
 * Created on April 1, 2004
 * Test connecting to local oracle db.
 */
package informix;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
public class meta {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("org.apache.derby.jdbc.ClientDriver");
				System.out.println("Driver loaded.");
				String sys = "jdbc:derby://localhost:1527/mrcdb;create=false";		
				conn = DriverManager.getConnection(sys, "sa", "mrc");
				System.out.println("Connecting to: " + sys);
				List<String> list = schemas(conn, "MRCX");
				for (String lib : list) {
					List<String> tables = tables(conn, lib);
					for (String tt : tables) {
						System.out.println(lib + "-" + tt);
					}
				}
				conn.close();
				
			} catch (ClassNotFoundException exc) {
				System.out.println("Error: " + exc);
				exc.printStackTrace();
			} catch (SQLException exc) {
				System.out.println("connection failed with:'" + exc.getMessage() + "'");
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

	/***********************************************************
	 * Get schemas from a connection
	 * @param conn Connection
	 * @param prefix if null, return all schemas, else return schmas with prefix
	 * @return List of schemas
	 ************************************************************/
	public static List<String> schemas(Connection conn, String prefix) {
		List<String> list = new ArrayList<String>();
		
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet rs = meta.getSchemas(); 
			while (rs.next()) {
				String lib = rs.getString("TABLE_SCHEM");
				if (prefix == null ) { //get all schemas
					list.add(lib);
				} else {
					if (lib.startsWith(prefix)) {
						list.add(lib);
					}
				}
				System.out.println(lib );
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

	/***********************************************************
	 * Get tables from a lib
	 * @param conn Connection
	 * @param lib  
	 * @return List of table names
	 ************************************************************/
	public static List<String> tables(Connection conn, String lib) {
		List<String> list = new ArrayList<String>();
		
		try {
		
			DatabaseMetaData meta = conn.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs1 = meta.getTables(null, lib, null, types); 
			while (rs1.next()) {
				//String s1 = rs1.getString("TABLE_SCHEM");
				String table = rs1.getString("TABLE_NAME");
				list.add(table);
			}
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}

}
