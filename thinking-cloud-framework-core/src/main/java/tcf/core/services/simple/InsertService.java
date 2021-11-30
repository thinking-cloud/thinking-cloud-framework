package tcf.core.services.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;

/**
 * 插入数据的Service 接口
 *
 * @param <T> 实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface InsertService<PK extends Serializable,T extends Entity<PK>> {
	/**
	 * 保存指定实体
	 * @param entity 保存的实体
	 * @return 保存的实体
	 */
	public T insert(T entity);
}
