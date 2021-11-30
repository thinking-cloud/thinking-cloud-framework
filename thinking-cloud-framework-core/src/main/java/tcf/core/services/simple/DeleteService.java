package tcf.core.services.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;

/**
 * 删除指定数据
 * 
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface DeleteService<PK extends Serializable, T extends Entity<PK>> {
	
	/**
	 * 根据ID，删除
	 * @param pk 删除的主键
	 * @return 影响行数
	 */
	public int deleteById(PK id);
	
	
	/**
	 * 根据实体对象,删除指定数据
	 * @param entity
	 * @return
	 */
	public int delete(T entity);
}
