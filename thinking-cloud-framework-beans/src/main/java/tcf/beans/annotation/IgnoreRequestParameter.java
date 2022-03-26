package tcf.beans.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tcf.beans.constant.ParameterType;
 
/**
 * 忽略url 请求参数
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreRequestParameter {
	// 标识忽略的属性，在被处理成什么类型的数据时生效。
	ParameterType type() default ParameterType.ALL;
	// 只在携带指定标签的接口中忽略
	String[] tags() default {};
	
}