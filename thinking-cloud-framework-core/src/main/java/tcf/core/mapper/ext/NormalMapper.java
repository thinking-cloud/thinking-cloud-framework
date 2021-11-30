package tcf.core.mapper.ext;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.simple.PageMapper;

/**
 * 常用的CRUD及分页查询组合
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see SimpleMapper
 * @see PageMapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface NormalMapper<PK extends Serializable, T extends Entity<PK>> 
	extends SimpleMapper<PK, T>, PageMapper<PK, T>{
	
}
