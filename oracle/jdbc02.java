/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package oracle;
import java.sql.*;
 
public class jdbc02 {
	
	public static void main(String args[]) {
			Connection conn = null;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Driver loaded.");
			 
				String sys = "jdbc:oracle:thin:@192.168.0.120:1521:mrc";			
				conn = DriverManager.getConnection(sys, "mrc", "mrc");
				if (conn.getAutoCommit()) {	
					conn.setAutoCommit(false);
				}
				System.out.println("Connecting to: " + sys);
				
				Statement stmt = conn.createStatement();
				//String sql = "SELECT * FROM BYJTEST1";
				String sql = "SELECT T01.USRPRF, T01.OUTQUE, T01.ROWID " 
					+ "FROM MRCAPPLLIB.MRCPRFIL T01  " 
					+ " WHERE UPPER(T01.USRPRF)=UPPER('JIN  ') " 
					+ " ORDER BY T01.USRPRF ASC FOR UPDATE OF OUTQUE";
				//String sql = "SELECT T01.USRPRF, T01.OUTQUE, T01.JOBQUE, T01.WRKLIB, T01.JOBDSC, T01.ALLMNT, T01.ALLSUM, T01.ALLDCT, T01.ALLADM, T01.ALLECS, T01.ALLPRF, T01.ALLAPP, T01.ALLBCH, T01.ALLTMP, T01.ALLOOP FROM MRCAPPLLIB.MRCPRFIL T01  WHERE UPPER(T01.USRPRF)=UPPER('CROWLEY   ') ORDER BY T01.USRPRF ASC FOR UPDATE";
				ResultSet rs = stmt.executeQuery(sql);  
				conn.commit();	
				conn.commit();
				if (!conn.getAutoCommit()) {	
					conn.setAutoCommit(true);
				}
	 
				while (rs.next()) {
					String x = rs.getString(1) ;
					String y = rs.getString(2) ;
					String id = rs.getString("ROWID") ;
					System.out.println( "f1=" + x + "   f2=" + y + "  id=" + id  );				 
				}
				 
				stmt.close();
				conn.close();
				
			} catch (ClassNotFoundException exc) {
				System.out.println("jdbc driver: " + " not found.");
			} catch (SQLException exc) {
				System.out.println("Failed with:'" + exc.getMessage() + "'");
			} finally {
				try {
				 	if (conn != null) {
				 		conn.close(); 
					    System.out.println("connection closed.");
				 	}
				}  catch (SQLException exc) {}
			}

		  
		} //main                


}
