package tcf.utils.exception;

/**
 * 反射工具类抛出的异常
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class ReflectException extends RuntimeException {
    public ReflectException() {
    }

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectException(Throwable cause) {
        super(cause);
    }
}
