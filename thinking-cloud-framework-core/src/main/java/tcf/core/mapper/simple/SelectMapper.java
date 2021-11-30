package tcf.core.mapper.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.Mapper;

/**
 * 根据主键查询数据
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface SelectMapper<PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T> {
	/**
	 * 根据主键查询
	 * @param pk 主键
	 * @return 查询到的对象
	 */
	public T selectById(PK id);
}

