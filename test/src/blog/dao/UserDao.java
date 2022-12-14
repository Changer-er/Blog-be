package blog.dao;

import blog.Exception.AppException;
import blog.model.User;
import blog.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Author:ZouDouble
 * Description:
 * 天气：晴天
 * 目标：Good Offer
 * Date    2021-08-03 20:40
 */
public class UserDao {
    public static User findUser(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            connection = DBUtil.getConnection();
            String sql = "select * from user where username=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            resultSet = statement.executeQuery();
            System.out.println(statement);
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(username);
                user.setPassword(resultSet.getString("password"));
            }
        }catch (Exception e){
            throw new AppException("User001","查询用户失败",e);
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return user;
    }

    public static void addUser(User userRegister) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into user (username,password) values (?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,userRegister.getUsername());
            statement.setString(2,userRegister.getPassword());
            statement.executeUpdate();
        }catch (Exception e){
            throw new AppException("User002","添加用户失败",e);
        }finally {
            DBUtil.close(connection,statement);
        }
    }
}
