package tcf.httpclient.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import tcf.utils.reflect.ReflectBeanUtils;
import tcf.utils.reflect.ReflectUtils;

/**
 * 请求参数处理工具类
 * 
 * @author think
 * @Date 2021-11-30
 * @version 1.0.0
 */
public class HttpUrlPramsUtils {
	/**
	 * 将对象转换为MultiValueMap
	 * 
	 * @param obj               目标对象
	 * @param isSupper          是否包含父类属性
	 * @param exculdeFieldNames 排除的字段
	 * @return
	 */
	public static MultiValueMap<String, Object> obj2MultiValueMap(Object obj, boolean isSupper,
			String... exculdeFieldNames) {
		MultiValueMap<String, Object> params = null;
		if (obj == null)
			return params;
		try {
			// 获取所有属性列表
			List<Field> fields = ReflectUtils.fields(obj.getClass(), true, exculdeFieldNames);
			if (fields != null) {
				params = new LinkedMultiValueMap<>();
				for (Field field : fields) {
					String varName = field.getName();
					boolean accessFlag = field.isAccessible();
					field.setAccessible(true);
					Object o = field.get(obj);
					if (o != null) {
						params.add(varName, o);
					}
					field.setAccessible(accessFlag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return params;
	}

	/**
	 * 将对象转换为url上的请求参数
	 * <P>
	 * 格式: url?name1=value1&name2=value2
	 * <p>
	 * 
	 * @param url               请求路径,在此路径后面拼接
	 * @param obj               目标对象
	 * @param isSupper          是否包含父类属性
	 * @param exculdeFieldNames 排除的字段
	 * @return
	 */
	public static String obj2UrlParams(String url, Object obj, boolean isSupper, String... exculdeFieldNames) {
		StringBuilder urlParams = new StringBuilder(url).append("?");
		if (obj == null)
			return urlParams.toString();
		try {
			// 获取所有属性列表
			List<Field> fields = ReflectUtils.fields(obj.getClass(), true, exculdeFieldNames);
			if (fields != null) {
				for (Field field : fields) {
					String name = field.getName();
					boolean accessFlag = field.isAccessible();
					field.setAccessible(true);
					Object value = field.get(obj);
					if (value != null) {
						urlParams.append(name).append("=").append(value).append("&");
					}
					field.setAccessible(accessFlag);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		urlParams.substring(urlParams.length() - 1);
		return urlParams.toString();
	}

	/**
	 * 替换配置中{}变量为属性值的方法.
	 * 
	 * @param path   未替换的路径
	 * @param params 要填充的值
	 * @return 替换完成的的路径
	 */
	public static String replaceVariablePath(String path, Object params) {

		try {
			if (params == null) {
				return path;
			}

			int start = 0;
			int end = 0;
			String variable = null;

			Object m = null;
			Method method = null;
			Object v = null;
			while ((start = path.indexOf("{")) >= 0) {
				end = path.indexOf("}");
				variable = path.substring(start + 1, end);
				String[] split = variable.split("\\.");
				m = params;
				v = null;
				method = null;
				for (String name : split) {
					method = m.getClass().getMethod(ReflectBeanUtils.fieldName2MethodName("get", name));
					v = method.invoke(m);
					if (v instanceof String || v instanceof Integer || v instanceof Boolean || v instanceof Long
							|| v instanceof Double || v instanceof Character || v instanceof Byte || v instanceof Short
							|| v instanceof Float || v == null) {
						path = path.substring(0, start) + v + path.substring(end + 1);
					} else {
						m = v;

					}
				}

			}
			return path;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
