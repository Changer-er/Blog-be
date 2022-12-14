package blog.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * http响应json数据，前后端统一的json格式
 * 响应状态码为200，进入ajax的success方法
 * */
@Getter
@Setter
@ToString
public class JSONResponse {
    //业务是否操作成功
    private boolean success;
    //业务操作消息码,是用来给后端人员看的
    private String code;
    //业务操作的错误码，给用户看
    private String message;
    //业务数据，给前端人员,用来解析响应json数据，渲染网页内容
    private Object data;

}