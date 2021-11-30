package tcf.beans.entity;

import java.util.Date;
import java.util.List;

/**
 * 为model对象添加时间戳属性
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface Timestamp {
	
	/**
	 * 获取创建数据的时间
	 * @return 创建时间
	 */
	public Date getCreateTime();
	
	/**
	 * 设置创建时间
	 * @param date
	 */
	public void setCreateTime(Date date);
	
	/**
	 * 最后修改时间
	 * @return 最后修改时间
	 */
	public Date getLastUpdateTime();
	
	/**
	 * 设置最哈偶修改时间
	 * @param date
	 */
	public void setLastUpdateTime(Date date);
}
