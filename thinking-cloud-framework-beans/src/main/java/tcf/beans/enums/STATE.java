package tcf.beans.enums;


/**
 * 状态枚举常量值
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public enum STATE {
	FALSE(0),	// 假
	TRUE(1),		// 真
	ENABLED(2),	// 已启用
	DISABLED(3),	// 已禁用
	DELETED(4),	// 已删除
	ACTIVED(5),	// 活跃的
	READ_ONLY(6),	//只读的
	WRITE_ONLY(7);	//只写的


	private int code;
	private STATE(int code) {
		this.code = code;
	}

	public int code() {
		return this.code;
	}

	/**
	 * 将code直 转为 STATE
	 * @param code
	 * @return
	 */
	public static STATE codeOf(Integer code) {
		if(code == null) {
			throw new NullPointerException("code为null");
		}
		switch (code) {
			case 0:
				return STATE.FALSE;
			case 1:
				return STATE.TRUE;
			case 2:
				return STATE.ENABLED;
			case 3:
				return STATE.DISABLED;
			case 4:
				return STATE.DELETED;
			case 5:
				return STATE.ACTIVED;
			case 6:
				return STATE.READ_ONLY;
			case 7:
				return STATE.WRITE_ONLY;
			default:
				return null;
		}
	}
}
