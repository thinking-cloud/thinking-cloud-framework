package tcf.core.services.simple;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import tcf.beans.entity.Entity;

/**
 * 批量insert数据 的Service
 *
 * @param <T> 实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface BatchInsertService<PK extends Serializable, T extends Entity<PK>> {
	
	/**
	 * 批量保存，只保存，不返回数据id
	 * 
	 * @param list 保存的实体的集合
	 * @return 影响行数
	 */
	public int inserts(Collection<T> batch);
	
	/**
	 * 批量保存，并返回对象中的ID
	 * 
	 * @param list 保存的实体的集合
	 * @return 影响行数
	 */
	public Collection<T> insertsAndReturnId(Collection<T> batch);
	

}
