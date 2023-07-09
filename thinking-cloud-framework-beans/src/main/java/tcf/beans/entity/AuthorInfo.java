package tcf.beans.entity;

import java.io.Serializable;

/**
 * 操作数据的用户信息
 * 
 * @param <UID> 用户ID类型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface AuthorInfo<UID extends Serializable> {
	
	/**
	 * 获取创建用户id
	 * @return 用户ID
	 */
	public UID getCreateUserId() ;
	/**
	 * 设置创建用户id
	 * @param createUserId 用户id
	 */
	public void setCreateUserId(UID createUserId); 
	
	/**
	 * 获取最后修改用户id
	 * @return 用户ID
	 */
	public UID getLastUpdateUserId(); 
	
	/**
	 * 设置最后修改用户id
	 * @param updateUserId 用户ID
	 */
	public void setLastUpdateUserId(UID	 updateUserId);
	
	
}
