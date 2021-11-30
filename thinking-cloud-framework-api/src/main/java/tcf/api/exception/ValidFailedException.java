package tcf.api.exception;

/**
 * 验证失败信息的异常
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public class ValidFailedException extends  RuntimeException{
    public ValidFailedException(String message) {
        super(message);
    }

    public ValidFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidFailedException(Throwable cause) {
        super(cause);
    }
}
