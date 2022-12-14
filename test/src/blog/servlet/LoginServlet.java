package blog.servlet;

import blog.dao.LoginDao;
import blog.Exception.AppException;
import blog.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //解析请求数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //进行数据库用户名密码校验
        User user = LoginDao.query(username);
        if (user == null){
            throw new AppException("Login002","用户不存在");
        }
        if (!user.getPassword() .equals(password)){
            throw new AppException("Login003","用户名或者密码错误");
        }
        HttpSession session = req.getSession();
        session.setAttribute("user",user);
        return null;
    }
}