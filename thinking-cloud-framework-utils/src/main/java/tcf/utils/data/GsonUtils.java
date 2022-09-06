package tcf.utils.data;

import com.google.gson.Gson;

/**
 * Gson的工具类
 *
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class GsonUtils {
	private static final Gson gson = new Gson();

	/**
	 * 将目标对象转为json字符串
	 * @param object 目标对象
	 * @return json字符串
	 */
	public static String toJson(Object object) {
		if(object != null) {
			return gson.toJson(object);
		}
		return null;
	}

	/**
	 * 将json转为指定类型
	 * @param <T> 指定的目标类型
	 * @param json json数据
	 * @param clazz 指定的目标类型字节码
	 * @return 指定类型的对象
	 */
	public static <T> T toObj(String json, Class<T> clazz) {
		if(json!=null) {
			return (T)gson.fromJson(json, clazz);
		}
		return null;
	}
}
