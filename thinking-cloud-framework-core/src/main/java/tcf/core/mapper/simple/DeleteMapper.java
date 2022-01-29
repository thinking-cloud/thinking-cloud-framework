package tcf.core.mapper.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.Mapper;

/**
 * 删除数据的接口
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0 
 */
public interface DeleteMapper<PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T> {
	
	/**
	 * 根据主键删除
	 * @param pk 要删除的主键
	 * @return 影响条数
	 */
	public int deleteById(PK id);
	
	/**
	 * 根据条件，删除对象
	 * @param entity 要删除的主键
	 * @return 影响条数
	 */
	public int delete(T entity);
}
