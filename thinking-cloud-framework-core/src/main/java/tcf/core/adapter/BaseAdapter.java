package tcf.core.adapter;

import java.io.Serializable;
import java.lang.reflect.Type;

import tcf.beans.vo.VO;
import tcf.core.converts.EntityToVoConvert;
import tcf.core.converts.LimitToPageConvert;
import tcf.utils.reflect.ReflectUtils;

/**
 * adapter层的基类
 * 
 * @param <T> 实体泛型
 * @param <V> VO泛型
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public abstract class BaseAdapter<T extends Serializable, V extends VO<T>> {
	/**
	 * 创建默认 实体类转VO类的Convert
	 */
	@SuppressWarnings("unchecked")
	protected  EntityToVoConvert<T,V> entityConvert(){
		Type[] types = ReflectUtils.genericBySuperClass(this);
		return new EntityToVoConvert<T, V>((Class<V>)types[1]);
    }

	/**
	 * 创建默认 Limit类转page类的Convert
	 */
	@SuppressWarnings("unchecked")
	protected  LimitToPageConvert<T,V> limitConvert(){
		Type[] types = ReflectUtils.genericBySuperClass(this);
    	return new LimitToPageConvert<>((Class<V>)types[1]);
	}
}
