package tcf.httpclient.exception;

/**
 * Authorization未传入异常
 * 在使用rest请求时, Authorization未传入则抛出此异常
 *
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public class AuthorizationIsNullException extends TcfHttpClientException {

	private static final long serialVersionUID = -293130776190347890L;

	public AuthorizationIsNullException() {
		this("Authorization is null");
	}

	public AuthorizationIsNullException(String message) {
		super(message);
	}	
}
