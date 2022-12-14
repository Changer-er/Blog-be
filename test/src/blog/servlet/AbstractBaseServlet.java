package blog.servlet;

import blog.Exception.AppException;
import blog.model.JSONResponse;
import blog.util.JSONUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 创建一个servlet的抽象类，做好初始化工作，后面子类直接继承即可
 * */
public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求(响应)编码和方式
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //请求数据类型为application/json,req.getInputStream获取请求体，数据作为输入流获取
        //通过json框架反序列化为一个Java对象，请求数据要和对象数据类型一致
        //服务端也返回application/json
        resp.setContentType("application/json");

        //使用req.getServletPath()获取请求路径
        JSONResponse json = new JSONResponse();
        try {
            //调用子类重写的方法
            Object data = process(req, resp);
            //子类的process方法正常执行，没有异常
            json.setSuccess(true);
            json.setData(data);
        }catch (Exception e){
            //处理异常，如SQLException,JSON异常，使用自定义异常返回异常
            e.printStackTrace();
            String s = "未知错误";
            String code = "UnknownError";
            if (e instanceof AppException){
                code = ((AppException)e).getCode();
                s = e.getMessage();
            }
            //json.setSuccess默认值就是false,此处可以不用设置
            json.setCode(code);
            json.setMessage(s);
        }
        PrintWriter pw = resp.getWriter();
        pw.write(JSONUtil.serialize(json));
        pw.flush();
        pw.close();
    }
    //模板方法，用来完成servlet初始化操作
    protected abstract Object process(HttpServletRequest req,
                                      HttpServletResponse resp) throws IOException;
}

