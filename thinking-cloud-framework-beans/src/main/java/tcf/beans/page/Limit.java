package tcf.beans.page;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import tcf.beans.annotation.IgnoreSwaggerParameter;
import tcf.beans.cache.ThreadLocalTables;

/** 
 * 分页顶级接口
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public interface Limit extends Serializable {
	int DEFAULT_PAGE_N0 = 1;
	int DEFAULT_PAGE_SIZE = 25;
	
	/**
	 * 获取当前页码
	 * @return
	 */
	default Integer getPageNo() {
		Integer pageNo = ThreadLocalTables.THREADLOCAL_PAGENO.get();
		return pageNo == null || pageNo<=0 ? DEFAULT_PAGE_N0:pageNo;
	}

	/**
	 * 设置当前页码
	 * @param pageNo
	 */
	default void setPageNo(int pageNo) {
		ThreadLocalTables.THREADLOCAL_PAGENO.set(pageNo<=0 ? DEFAULT_PAGE_N0 : pageNo);
	}

	/**
	 * 获取每页显示的条数
	 * @return
	 */
	default Integer getPageSize() {
		Integer pageSize = ThreadLocalTables.THREADLOCAL_PAGESIZE.get();
		return pageSize == null || pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
	}

	/**
	 * 设置每页显示的条数
	 * @param pageSize
	 */
	default void setPageSize(int pageSize) {
		ThreadLocalTables.THREADLOCAL_PAGESIZE.set(pageSize <= 0? DEFAULT_PAGE_SIZE : pageSize);
	}
	
	default void setTotalRecord(long total) {
		ThreadLocalTables.THREADLOCAL_TOTALRECORD.set(total);
	}
	
	@IgnoreSwaggerParameter
	@JsonIgnore
	@ApiModelProperty(value="总条数",example = "0")
	default Long getTotalRecord() {
		return ThreadLocalTables.THREADLOCAL_TOTALRECORD.get();
	}

	/**
	 * 获取数据起始索引
	 * @return
	 */
	@IgnoreSwaggerParameter
	@JsonIgnore
	@ApiModelProperty(value="起始索引",example = "0")
	default int getStartIndex(){
		return (getPageNo() - 1) * getPageSize();
	}	
}
