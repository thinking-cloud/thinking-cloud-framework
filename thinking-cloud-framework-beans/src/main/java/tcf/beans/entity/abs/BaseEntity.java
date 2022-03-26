package tcf.beans.entity.abs;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import tcf.beans.annotation.IgnoreRequestParameter;
import tcf.beans.entity.AuthorInfo;
import tcf.beans.entity.Entity;
import tcf.beans.entity.Timestamp;

/**
 * 基本的Entity
 *
 * @param <PK> 唯一标识泛型
 * @param <U> 操作用户的ID泛型
 * @author zxk
 * @date 2021年2月24日
 * @version 1.0.0
 */
@Data
public abstract class BaseEntity<PK extends Serializable,U extends Serializable> implements Entity<PK>, Timestamp,AuthorInfo<U> {

	private static final long serialVersionUID = -5208780711395858867L;
	
    @IgnoreRequestParameter
	private PK id;

    @IgnoreRequestParameter
	private U createUserId ;
    
    @IgnoreRequestParameter
	private Date createTime;
    
    @IgnoreRequestParameter
	private U lastUpdateUserId;
    
    @IgnoreRequestParameter
	private Date lastUpdateTime;
    
	public BaseEntity() { }
	public BaseEntity(PK id) {
		this.id = id;
	}	
	public BaseEntity(PK id, Date lastUpdateTime) {
		this.id = id;
		this.lastUpdateTime = lastUpdateTime;
	}
	public BaseEntity(PK id, Date createTime, Date lastUpdateTime) {
		this.id = id;
		this.createTime = createTime;
		this.lastUpdateTime = lastUpdateTime;
	}
}