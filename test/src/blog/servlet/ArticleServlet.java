package blog.servlet;

import blog.dao.ArticleDao;
import blog.Exception.AppException;
import blog.model.Article;
import blog.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/articleList")
public class ArticleServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取session,如果为Null就直接返回
        HttpSession session = req.getSession(false);
        if (session == null){
            throw new AppException("Article002","用户未登录，请返回登录");
        }
        User user = (User)session.getAttribute("user");
        if (user == null){
            throw new AppException("Article003","会话异常，请重新登录");
        }
        List<Article> articles = ArticleDao.queryByUserId(user.getId());
        return articles;
    }
}