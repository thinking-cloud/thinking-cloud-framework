package tcf.utils.exception;

/**
 * 日期格式转换异常
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class DateFormatException extends RuntimeException{
    public DateFormatException() {
    }

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateFormatException(Throwable cause) {
        super(cause);
    }
}
