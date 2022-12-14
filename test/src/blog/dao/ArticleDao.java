package blog.dao;

import blog.Exception.AppException;
import blog.model.Article;
import blog.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:ZouDouble
 * Description:
 * 天气：晴天
 * 目标：Good Offer
 * Date    2021-08-03 12:23
 */
public class ArticleDao {
    public static List<Article> queryByUserId(Integer userId) {
        List<Article> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select id,title,content,create_time,user_id from article where user_id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                article = new Article();
                article.setId(resultSet.getInt("id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                Date createTime = resultSet.getTime("create_time");
                article.setCreateTime(createTime);
                list.add(article);
            }
        }catch (Exception e){
            throw new AppException("Article001","数据库查询失败",e);
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return list;
    }

    public static int delete(String[] ids) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from article where id in(");
            for (int i = 0; i < ids.length; i++) {
                if (i != 0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");
            statement = connection.prepareStatement(sql.toString());
            for (int i = 0; i < ids.length; i++) {
                statement.setInt(i+1,Integer.parseInt(ids[i]));
            }
            return statement.executeUpdate();
        }catch (Exception e){
            throw new AppException("Article004","删除文章操作失败",e);
        }finally {
            DBUtil.close(connection,statement);
        }
    }

    public static int insert(Article article) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String title = article.getTitle();
            String content = article.getContent();
            int userId = article.getUserId();
            String sql = "insert into article (title,content,user_id) values (?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,title);
            statement.setString(2,content);
            statement.setInt(3,userId);
            return statement.executeUpdate();
        }catch (Exception e){
            throw new AppException("Article005","文章插入失败",e);
        }finally {
            DBUtil.close(connection,statement);
        }
    }

    public static int update(Article article) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "update article set title=?,content=?where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,article.getTitle());
            statement.setString(2,article.getContent());
            statement.setInt(3,article.getId());
            return statement.executeUpdate();
        }catch (Exception e){
            throw new AppException("Article006","修改文章出错",e);
        }finally {
            DBUtil.close(connection,statement);
        }
    }

    public static Article findArticle(int articleId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Article article = null;
        try{
            connection = DBUtil.getConnection();
            String sql = "select id,title,content from article where id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1,articleId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                article  = new Article();
                article.setId(articleId);
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
            }

        }catch (Exception e){
            throw new AppException("Article007","查询文章id出错",e);
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return article;
    }
}
