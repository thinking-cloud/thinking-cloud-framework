package tcf.core.mapper.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.beans.page.Limit;
import tcf.core.mapper.Mapper;

/**
 * 查询总数的mapper
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface CountMapper <PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T>{
	/**
	 * 查询总数
	 * @param limit 查询条件
	 * @return 总数
	 * @throws Exception SQK异常
	 */
	public Long count(Limit limit);
}
