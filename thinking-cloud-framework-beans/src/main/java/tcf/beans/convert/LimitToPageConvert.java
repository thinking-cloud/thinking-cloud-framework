
package tcf.beans.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import tcf.beans.page.Limit;
import tcf.beans.page.Page;
import tcf.beans.vo.VO;

/**
 * limit对象转为page对象
 * 
 * @param <T> Entity泛型
 * @param <V> vo泛型
 * @see Converter
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class LimitToPageConvert<T, V extends VO<T>> implements Converter<T, V> {

	protected EntityToVoConvert<T, V> entityToVoConvert;

	public LimitToPageConvert(Class<V> voClass) {
		
		this.entityToVoConvert = new EntityToVoConvert<>(voClass);
	}

	@Override
	public V convert(T source) {
		return this.entityToVoConvert.convert(source);
	}

	/**
	 * limit对象转为page对象
	 * 
	 * @param limit limit查询对象
	 * @param list  查询结果
	 * @return page对象
	 */
	public Page<V> limitToPage(Limit limit, List<T> list, long count) {
		Page<V> page = new Page<>();
		page.setPageNo(limit.getPageNo());
		page.setPageSize(limit.getPageSize());
		page.setRecords(this.entityToVoConvert.convert(list));
		page.setTotalRecord(count);
		return page;
	}

	/**
	 * limit对象转为page对象
	 * 
	 * @param limit limit查询对象
	 * @param page  查询结果
	 * @return page对象
	 */
	public Page<V> limitToPage(Limit limit, Page<T> page) {
		Page<V> pageVo = limitToPage(limit, page.getRecords(), page.getTotalRecord());
		return pageVo;
	}

	/**
	 * limit对象转为page对象
	 * 
	 * @param limit limit查询对象
	 * @param list  查询结果
	 * @return page对象
	 */
	public Page<V> limitToPage(Limit limit, List<T> list) {
		List<T> subList = subList(list, limit.getPageNo(), limit.getPageSize());
		Page<V> pageVo = limitToPage(limit, subList, list.size());
		return pageVo;
	}

	/**
	 * 切割list
	 * 
	 * @param list     源list
	 * @param pageNo   页码
	 * @param pageSize 每页显示的条数
	 * @return 切割后的list
	 */
	private List<T> subList(List<T> list, int pageNo, int pageSize) {
		int count = list.size();
		if (count / pageSize + 1 < pageNo) {
			return new ArrayList<>(0);
		}
		int fromIndex = pageSize * (pageNo - 1);
		int to = pageSize * pageNo;
		int toIndex = to > count ? count : to;
		return list.subList(fromIndex, toIndex);
	}

}
