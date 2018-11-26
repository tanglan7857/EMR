package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接数据库
 * @author 唐岚岚
 */
public class JDBCUtil {

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "1234";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EMR";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    static {
        try {
            Class.forName(DRIVER);
            connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
    private JDBCUtil() { }

    /**
     * 获得数据库连接
     *
     * @return
     */
    public static Connection connect() {
        try {
            if(connection == null) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 增加 删除 修改
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
        connect();
        boolean flag = false;
        int result = -1;
        preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        result = preparedStatement.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }

    /**
     * 查询单条记录
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
        connect();
        Map<String, Object> map = new HashMap<>();
        int index = 1;
        preparedStatement = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        if (resultSet.next()) {
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name.toUpperCase(), cols_value);
            }
        }
        return map;
    }

    /**
     * 查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
        connect();
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 1;
        preparedStatement = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()) {
            for(int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(index++, params.get(i));
            }
        }
        resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while(resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for(int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if(cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name.toUpperCase(), cols_value);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 释放数据库连接
     */
    public static void releaseConn() {
        connect();
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                resultSet = null;
            }
        }

        if(preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                preparedStatement =  null;
            }
        }

        if(connection != null) {
            try {
                connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }


}
