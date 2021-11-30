package tcf.core.mapper.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.Mapper;

/**
 * 插入数据的接口
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface InsertMapper<PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T>  {
	/**
	 * 插入数据
	 * @param Entity 插入的数据对象
	 * @return 影响条数
	 * @throws SQL异常
	 */
	public int insert(T Entity);
}
