/*
 * Created on May 13, 2004
 * Test connecting to remote ms sql server.
 */
package db.mssql;

import java.sql.*;

public class mssql01 {

    public static void main(String args[]) {
        Connection conn = null;

        try {
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            System.out.println("Driver loaded.");

            String sys = "jdbc:microsoft:sqlserver://192.168.0.151";

            conn = DriverManager.getConnection(sys, "MRC", "mrc");
            System.out.println("Connecting to: " + sys);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM pubs.dbo.jobs   ";
            ResultSet rs = stmt.executeQuery(sql);
            String s = "";
            while (rs.next()) {
                // String cur = rs.getCursorName();
                String x = rs.getString(1);
                s += x + "-";
                System.out.println("id - " + x + "    " + rs.getString(2));
            }
            System.out.println("get table from MRCDCT14 db:");
            sql = "SELECT * FROM testbj01.dbo.mrcdct14";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out
                        .println(rs.getString(1) + ",     " + rs.getString(2));
            }

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

    } // main

}
