package tcf.core.mapper.simple;

import java.io.Serializable;
import java.util.List;

import tcf.beans.entity.Entity;
import tcf.beans.page.Limit;
import tcf.core.mapper.Mapper;

/**
 * 分页查询的Mapper
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface PageMapper <PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T>{
	/**
	 * 分页查询
	 * @param Entity 分页查询对象
	 * @return 分页数据
	 */
	public List<T> queryPage(Limit Entity);
	
}
