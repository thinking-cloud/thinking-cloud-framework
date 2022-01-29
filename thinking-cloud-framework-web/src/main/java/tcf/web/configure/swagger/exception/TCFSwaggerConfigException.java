package tcf.web.configure.swagger.exception;

/**
 * swagger配置异常
 *
 * @author think
 * @Date 2021年12月14日
 * @version 1.0.0
 */
public class TCFSwaggerConfigException extends RuntimeException {

	private static final long serialVersionUID = 3843925184376256127L;

	public TCFSwaggerConfigException() {
		super();
	}

	public TCFSwaggerConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public TCFSwaggerConfigException(String message) {
		super(message);
	}

	public TCFSwaggerConfigException(Throwable cause) {
		super(cause);
	}
}
