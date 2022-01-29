package tcf.core.mapper.simple;

import java.io.Serializable;
import java.util.Collection;

import tcf.beans.entity.Entity;
import tcf.core.mapper.Mapper;

/**
 * 批量添加数据的接口
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface InsertMultipleMapper<PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T> {
	
	/**
	 * 批量保存
	 * 
	 * @param 批量更新对象
	 * @return 影响行数
	 */
	public int inserts(Collection<T> batch);
	
	/**
	 * 批量保存
	 * 
	 * @param 批量更新对象
	 * @return 影响行数
	 */
	public long insertsAndReturnId(Collection<T> batch);
	
}
