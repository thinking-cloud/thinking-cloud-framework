
package tcf.core.mapper;

import java.io.Serializable;

import tcf.beans.entity.Entity;

/**
 * 所有mapper接口的基类
 * 
 * @param <PK> 主键的泛型
 * @param <T> 实体的泛型 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface Mapper<PK extends Serializable, T extends Entity<PK>> {

}
