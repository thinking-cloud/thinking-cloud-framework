package tcf.core.mapper.simple;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.Mapper;
/**
 * 修改数据的接口
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see Mapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface UpdateMapper<PK extends Serializable, T extends Entity<PK>> extends Mapper<PK, T> {
	/**
	 * 根据主键修改数据
	 * @param t 查询条件与修改的值
	 * @return 影响行数
	 * @throws Exception SQL异常
	 */
	public int update(T t);

}
