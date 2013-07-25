/*
 * Created on Jan 6, 2004
 * Test connecting to local oracle db.
 */
package oracle;
import java.sql.*;
 
public class jdbc01 {
	
	public static void main(String args[]) {
        Connection conn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded.");
 
            String sys = "jdbc:oracle:thin:@mrcwin:1521:testdb";
                   sys = "jdbc:oracle:thin:@72.16.169.182:1521/XE";
            conn = DriverManager.getConnection(sys, "mrc", "mrccorp11");
            System.out.println("Connecting to: " + sys);

            Statement stmt = conn.createStatement();
            //String sql = "SELECT * FROM BYJTEST1";
            //String sql = "SELECT ROWID FROM HR.countries";
            stmt.execute("alter session set NLS_DATE_FORMAT = 'yyyy-MM-dd'");
            String sql = "SELECT * FROM mrcmpower.aaa where col3='2011-04-15'";
            ResultSet rs = stmt.executeQuery(sql);
            String s = "";
            while (rs.next()) {
                String x = rs.getString(1);
                String x2 = rs.getString(2);
                String x3 = rs.getString(3);
                System.out.println( x + ", " +x2 + ",  " + x3 );
            }
            System.out.println("s = " + s);
            stmt.close();
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

    } //main                


}
