package blog.servlet;

import blog.dao.ArticleDao;
import blog.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/articleDetail")
public class ArticleFindServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //根据URL获取到文章id
        int  articleId = Integer.valueOf(req.getParameter("id"));
        Article article = ArticleDao.findArticle(articleId);
        return article;
    }
}