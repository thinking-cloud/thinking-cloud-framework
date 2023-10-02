package tcf.core.mapper.ext;

import java.io.Serializable;

import tcf.beans.entity.Entity;
import tcf.core.mapper.simple.DeleteMultipleMapper;
import tcf.core.mapper.simple.InsertMultipleMapper;

/**
 * 全部的数据库操作接口组合
 *
 * @param <PK> 主键泛型
 * @param <T> 实体泛型
 * @see NormalMapper
 * @see DeleteMultipleMapper
 * @see InsertMultipleMapper
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface AllMapper<PK extends Serializable, T extends Entity<PK>> extends 
																			NormalMapper<PK,T>, 
																			DeleteMultipleMapper<PK, T>,
																			InsertMultipleMapper<PK, T> {

}
