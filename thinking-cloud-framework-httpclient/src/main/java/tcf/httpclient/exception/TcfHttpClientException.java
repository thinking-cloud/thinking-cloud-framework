package tcf.httpclient.exception;

/**
 * Tcf模块处理业务处理的异常
 * Tcf模块中所有异常，都应该是这个异常的子类。
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public class TcfHttpClientException extends RuntimeException{

	private static final long serialVersionUID = -293130776190347890L;

	public TcfHttpClientException() {
	}

	public TcfHttpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public TcfHttpClientException(String message) {
		super(message);
	}

	public TcfHttpClientException(Throwable cause) {
		super(cause);
	}
}
