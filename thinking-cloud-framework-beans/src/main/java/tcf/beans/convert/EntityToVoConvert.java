
package tcf.beans.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import tcf.beans.vo.VO;


/**
 * 
 * Entity对象转VO对象的转换类
 * 
 * @param <T> 源类型
 * @param <V> VO类型
 * @see Converter
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class EntityToVoConvert<T ,V extends VO<T>> implements Converter<T, V> {
	
	private Class<V> voClass;
	public EntityToVoConvert(Class<V> voClass) {
		this.voClass = voClass;
	}
	
	/**
	 * sourceList 转为 VO的list
	 * @param sourceList 对象模型的集合
	 * @return VO对象的集合
	 */
	public List<V> convert(List<T> sourceList){
		List<V> voList = new ArrayList<>();
		if(sourceList != null && !sourceList.isEmpty()) {
			for (T source : sourceList) {
				V vo = convert(source);
				voList.add(vo);
			}	
		}
		return voList;
	}
	
	/**
	 * source 转为 VO对象
	 */
	public V convert(T source) {
		try {
			return voClass.newInstance().convert(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
