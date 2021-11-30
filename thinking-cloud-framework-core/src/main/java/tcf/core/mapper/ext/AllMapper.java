package tcf.core.mapper.ext;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.simple.BatchDeleteMapper;
import tcf.core.mapper.simple.BatchInsertMapper;

/**
 * 全部的数据库操作接口组合
 *
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @See NormalMapper
 * @see BatchDeleteMapper
 * @see BatchInsertMapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface AllMapper<PK extends Serializable, T extends Entity<PK>>
						extends NormalMapper<PK,T>, BatchDeleteMapper<PK, T>,BatchInsertMapper<PK, T> {

}
