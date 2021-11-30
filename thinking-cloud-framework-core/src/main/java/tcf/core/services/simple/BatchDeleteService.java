package tcf.core.services.simple;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import tcf.beans.entity.Entity;

/**
 * 批量delete数据 的Service
 *
 * @param <T> 实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface BatchDeleteService<PK extends Serializable, T extends Entity<PK>> {
	
	/**
	 * 根据ID列表，批量删除
	 * 
	 * @param id列表
	 * @return 影响行数
	 */
	public long deleteByIds(Iterable<PK> batch);
	
}
