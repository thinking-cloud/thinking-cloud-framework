package tcf.core.services.simple;

import java.io.Serializable;
import java.util.List;

import tcf.beans.entity.Entity;
import tcf.beans.page.Limit;
import tcf.beans.page.Page;

/**
 * 分页查询的Service接口 
 * 
 * @param <T>  实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @Date 2021-11-29
 * @version 1.0.0
 */
public interface LimitService<PK extends Serializable, T extends Entity<PK>> {
	
	/**
	 * 分页查询
	 * @param limit 查询件
	 * @return 分页数据
	 */
	public  <LIMIT extends Limit> List<T> queryPage(LIMIT limit);
}
