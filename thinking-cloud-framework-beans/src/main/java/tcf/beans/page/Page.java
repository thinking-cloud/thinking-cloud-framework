package tcf.beans.page;

import java.util.LinkedList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 分页对象
 * 
 * @param <T> 分页查询结果实体对象
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Schema(name="Page",description = "分页数据对象")
public class Page<T> {

	private static final long serialVersionUID = -3146313205433588374L;
	
	@Schema(description = "当前页码")
	private Integer pageNo=Limit.DEFAULT_PAGE_N0;

	@Schema(description = "每页显示的条数")
	private Integer pageSize=Limit.DEFAULT_PAGE_SIZE;
	
	@Schema(description = "本页记录")
	private List<T> records;
	
	@Schema(description = "总记录数")
	private Long totalRecord;

	/**
	 * 设置页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo<=0 ? 1 : pageNo;
	}

	/**
	 * 设置每页显示的条数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize<=0 ? 10 : pageSize;
	}

	/**
	 * 获取本页记录
	 * @return
	 */
	public List<T> getRecords() {
		if(records == null){
			synchronized (this){
				if(records == null){
					records = new LinkedList<>();
				}
			}
		}
		return records;
	}
	/**
	 * 设置本页记录
	 * @param records
	 */
	public void setRecords(List<T> records) {
		this.records = records;
	}

	/**
	 * 获取记录总数
	 * @return
	 */
	public Long getTotalRecord() {
		if(totalRecord == null) return 0L;
		return totalRecord;
	}

	/**
	 * 设置记录总数
	 * @param totalRecord
	 */
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord<=0 ? 0 : totalRecord;
	}

	/**
	 * 获取总页数
	 * @return
	 */
	public Long getTotalPage() {
		return (getTotalRecord() + getPageSize() -1) /  getPageSize();
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	
}
