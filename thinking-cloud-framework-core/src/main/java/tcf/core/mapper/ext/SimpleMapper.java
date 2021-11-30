package tcf.core.mapper.ext;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.simple.DeleteMapper;
import tcf.core.mapper.simple.InsertMapper;
import tcf.core.mapper.simple.SelectMapper;
import tcf.core.mapper.simple.UpdateMapper;


/**
 * 含有简单CRUD的接口
 * 
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see InsertMapper
 * @see DeleteMapper
 * @see UpdateMapper
 * @see SelectMapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface SimpleMapper<PK extends Serializable, T extends Entity<PK>> 
		extends InsertMapper<PK, T>, DeleteMapper<PK, T>, UpdateMapper<PK, T>, SelectMapper<PK, T>{
	
}
