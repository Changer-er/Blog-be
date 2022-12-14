package blog.servlet;


import blog.dao.ArticleDao;
import blog.model.Article;
import blog.model.User;
import blog.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        //请求数据类型是application/json,需要用输入流
        InputStream inputStream = req.getInputStream();
        Article article = JSONUtil.deserialize(inputStream,Article.class);
        article.setUserId(user.getId());
        int num = ArticleDao.insert(article);
        return null;
    }
}