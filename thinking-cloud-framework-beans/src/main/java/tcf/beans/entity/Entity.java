package tcf.beans.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tcf.beans.annotation.IgnoreSwaggerParameter;

/**
 * 所有model类都要实现的接口
 * 
 * @param <PK> 唯一标识的类型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface Entity<PK extends Serializable> extends Serializable {
	/**
	 * 获取唯唯一标识
	 * @return 唯一标识
	 */
	public PK getId();
	
	/**
	 * 设置唯一标识
	 * @param id 唯一标识
	 */
	public void setId(PK id);
			
}
