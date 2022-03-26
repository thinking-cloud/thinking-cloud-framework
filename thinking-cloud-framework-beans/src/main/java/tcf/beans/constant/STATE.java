package tcf.beans.constant;

/**
 * 状态枚举常量值
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public enum STATE {
	ACTIVED(0), // 活跃的
	DELETED(1), // 已删除
	DISABLED(2), // 已禁用
	READ_ONLY(3), // 只读的
	WRITE_ONLY(4); // 只写的

	private int code;

	private STATE(int code) {
		this.code = code;
	}

	public int code() {
		return this.code;
	}

	/**
	 * 将code直 转为 STATE
	 * 
	 * @param code
	 * @return
	 */
	public static STATE codeOf(Integer code) {
		if (code == null) {
			throw new NullPointerException("code为null");
		}
		switch (code) {
		case 0:
			return STATE.ACTIVED;
		case 1:
			return STATE.DELETED;
		case 2:
			return STATE.DISABLED;
		case 3:
			return STATE.READ_ONLY;
		case 4:
			return STATE.WRITE_ONLY;
		default:
			return null;
		}
	}
}
