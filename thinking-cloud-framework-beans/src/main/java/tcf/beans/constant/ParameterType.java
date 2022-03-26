package tcf.beans.constant;

/**
 * 参数类型
 * <p>用于标识，IgnoreRequestParameter注解，所处理的参数类型</p>
 * <p>all：忽略被标注的对象属性</p>
 * <p>json：当标注的对象属性，被处理成json时忽略</p>
 * <p>url：当标注的对象属性，被处理成url上的请求参数时忽略</p>
 * @author think
 * @Date 2022年1月30日
 * @version 1.0.0
 */
public enum ParameterType {
	ALL,JSON,URL
}
