package tcf.core.exception;


/**
 * 意外的条件表达式值。
 * 在处理条件判断逻辑时，Expression出现意外的值，抛出此异常。
 * @author zxk
 * @date 2021/1/11
 *
 */
public class UnexpectedExpressionValueException extends RuntimeException {
	
	private static final long serialVersionUID = 3313704859808661424L;

	public UnexpectedExpressionValueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message(message), cause, enableSuppression, writableStackTrace);
	}

	public UnexpectedExpressionValueException(String message, Throwable cause) {
		super(message(message), cause);
	}

	public UnexpectedExpressionValueException(String message) {
		super(message(message));
	}

	public UnexpectedExpressionValueException(Throwable cause) {
		super(cause);
	}
	
	private static String message(String value) {
		return "Switch Unexpected  Expression Value："+ value;
	}
	
}