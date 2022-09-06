package tcf.api.constant;

/**
 * Message 状态码
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public enum MESSAGE {
	SUCCESS("00"),
	FAILURE("01"),
	ERROR("02"),
	TEST("03");
	
	private String code;
	private MESSAGE(String code) {
		this.code = code;
	}
	
	public String code() {
		return this.code;
	}
}
