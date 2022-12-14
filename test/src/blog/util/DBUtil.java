package blog.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import blog.Exception.AppException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
    private static String url = "jdbc:mysql://localhost:3306/servlet_blog?characterEncoding=utf-8&useSSL=false";
    private static String username = "root";
    private static String password = "261919Zss";
    private static volatile DataSource dataSource;
    private static DataSource getDataSource(){
        if (dataSource == null){
            synchronized (DBUtil.class){
                if (dataSource == null){
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setURL(url);
                    ((MysqlDataSource)dataSource).setUser(username);
                    ((MysqlDataSource)dataSource).setPassword(password);
                }
            }
        }
        return dataSource;
    }
    //获取Connection对象
    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            //返回自定义异常
            throw new AppException("DBError001","数据库连接异常",e);
        }
    }

    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try {
            if (resultSet != null){
                resultSet.close();
            }
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new AppException("DBError002","数据库关闭资源失败",e);
        }
    }
    public static void close(Connection connection, PreparedStatement statement){
        close(connection, statement,null);
    }
}
