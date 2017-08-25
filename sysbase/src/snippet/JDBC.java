/*
 * @(#)Snippet.java 2017-7-25 下午1:43:27 sysbase Copyright 2017 Thuisoft, Inc.
 * All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use is subject to
 * license terms.
 */
package snippet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Snippet
 * @author Administrator
 * @version 1.0
 *
 */
public class JDBC {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
        String url = "jdbc:sybase:Tds:172.18.40.14:6000";
        Connection conn = DriverManager.getConnection(url, "sa", "123456");
        String sql = "SELECT ipaddr,count(*) a  from master.dbo.sysprocesses GROUP BY ipaddr ORDER BY a desc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rmd = rs.getMetaData();
        int t = rmd.getColumnCount();
        List<Map> list = new ArrayList<Map>();
        while (rs.next()) {
            Map m = new HashMap<String, Object>();
            for (int i = 1; i <= t; i++) {
                m.put(rmd.getColumnLabel(i), rs.getObject(i));
            }
            list.add(m);
        }
        Map m = list.get(0);
        String spid = "SELECT spid  from master.dbo.sysprocesses WHERE ipaddr = ?";
        PreparedStatement stmt1 = conn.prepareStatement(sql);
        stmt1.setString(0, m.get("ipaddr").toString());
        ResultSet rs1 = stmt.executeQuery();
        ResultSetMetaData rmd2 = rs1.getMetaData();
        int t2 = rmd.getColumnCount();
        List<Map> list2 = new ArrayList<Map>();
        while (rs.next()) {

        }
        rs.close();
        stmt.close();
        conn.close();
    }

    public void aa() throws ClassNotFoundException, SQLException {
        Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
        String url = "jdbc:sybase:Tds:172.18.40.14:6000";
        Connection conn = DriverManager.getConnection(url, "sa", "123456");
        String sql = "SELECT ipaddr,count(*) a  from master.dbo.sysprocesses GROUP BY ipaddr ORDER BY a desc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rmd = rs.getMetaData();
        int t = rmd.getColumnCount();
        List<Map> list = new ArrayList<Map>();
        while (rs.next()) {
            Map m = new HashMap<String, Object>();
            for (int i = 1; i <= t; i++) {
                m.put(rmd.getColumnLabel(i), rs.getObject(i));
            }
            list.add(m);
        }
        Map m = list.get(0);
        String spid = "SELECT spid  from master.dbo.sysprocesses WHERE ipaddr = ?";
        PreparedStatement stmt1 = conn.prepareStatement(sql);
        stmt1.setString(0, m.get("ipaddr").toString());
        ResultSet rs1 = stmt.executeQuery();
        ResultSetMetaData rmd2 = rs1.getMetaData();
        int t2 = rmd.getColumnCount();
        List<Map> list2 = new ArrayList<Map>();
        while (rs.next()) {

        }
        rs.close();
        stmt.close();
        conn.close();

    }
}
