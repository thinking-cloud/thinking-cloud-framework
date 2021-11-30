package tcf.core.services.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;

/**
 * 修改指定数据
 *
 * @param <T>  实体泛型
 * @param <PK> 主键泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface UpdateService<PK extends Serializable,T extends Entity<PK>> {
	/**
	 * 修改
	 * @param 修改实体
	 * @return 影响行数
	 */
	public int update(T entity);
}
