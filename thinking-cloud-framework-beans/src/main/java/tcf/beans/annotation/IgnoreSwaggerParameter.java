package tcf.beans.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * swagger 忽略url 请求参数
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSwaggerParameter {

}