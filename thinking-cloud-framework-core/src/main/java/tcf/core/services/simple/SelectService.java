package tcf.core.services.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;

/**
 * 查询指定一个数据
 * 
 * @param <T>  实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface SelectService<PK extends Serializable,T extends Entity<PK>> {
	
	/**
	 * 根据主键查询
	 * 
	 * @param 主键
	 * @return 查询结果
	 */
	public T selectByPK(PK id);
}
