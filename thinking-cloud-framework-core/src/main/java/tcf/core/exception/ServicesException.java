package tcf.core.exception;

/**
 * Service层 异常
 * 
 * @see RuntimeException
 * @author think
 * @Date 2021-11-29
 * @version 1.0.0
 */
public class ServicesException extends RuntimeException {

	private static final long serialVersionUID = -8926719806462982645L;

	public ServicesException() { }

	public ServicesException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServicesException(String message) {
		super(message);
	}

	public ServicesException(Throwable cause) {
		super(cause);
	}
	
	
}
