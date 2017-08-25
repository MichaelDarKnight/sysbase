package snippet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conn {
    private static Connection conn;

    private static Statement st;

    public static Connection getConnection() {

        initConnection();
        return conn;
    }

    /**
     * 初始化连接
     */
    public static synchronized void initConnection() {
        Pro.init();
        try {
            Class.forName(Pro.getConfig("Driver"));
            conn = DriverManager.getConnection(Pro.getConfig("URL"), Pro.getConfig("userName"),
                Pro.getConfig("passWord"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static int insert(String sql) {
        conn = getConnection();
        int count = 0;
        try {
            st = conn.createStatement();
            count = st.executeUpdate(sql);
            return count;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return count;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int delete(String sql) {
        conn = getConnection();
        int count = 0;
        try {
            st = conn.createStatement();

            count = st.executeUpdate(sql);

            return count;
        } catch (SQLException e) {
            System.out.println(e);
            return count;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int update(String sql) {
        conn = getConnection();
        int count = 0;
        try {
            st = conn.createStatement();

            count = st.executeUpdate(sql);

            return count;
        } catch (SQLException e) {
            System.out.println(e);
            return count;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Map<String, Object>> query(String sql) {
        List list = new ArrayList();
        conn = getConnection();
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            int count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= count; i++) {
                    map.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
            return new ArrayList();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exec(String sql) {
        conn = getConnection();
        try {
            st = conn.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, Object> queryMap(String sql) {
        Map map = new HashMap();
        conn = getConnection();
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            int count = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= count; i++) {
                    map.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
            }
            return map;
        } catch (SQLException e) {
            System.out.println(e);
            return new HashMap();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}