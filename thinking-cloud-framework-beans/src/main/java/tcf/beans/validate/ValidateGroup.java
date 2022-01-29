package tcf.beans.validate;

/**
 * validate验证分组
 *
 * @author think
 * @Date 2021年12月7日
 * @version 1.0.0
 */
public class ValidateGroup {
	/** 插入数据分组 */
	public static interface INSERT{}
	/** 删除数据分组 */
	public static interface DELETE{}
	/** 修改数据分组 */
	public static interface UPDATE{}
	/** 查询数据分组 */
	public static interface QUERY{}
}
