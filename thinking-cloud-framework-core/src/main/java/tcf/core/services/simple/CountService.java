package tcf.core.services.simple;

import tcf.beans.page.Limit;

/**
 * 查询总数
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface CountService {
	/**
	 * 查询总数
	 * 
	 * @param limit 查询条件
	 * @return 总数
	 */
	public <T extends Limit> long count(T limit);
}
