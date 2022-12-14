package blog.Exception;

public class AppException extends RuntimeException{
    private String code;
    public AppException(String code,String message) {
        this(code,message,null);
    }

    public String getCode() {
        return code;
    }

    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}