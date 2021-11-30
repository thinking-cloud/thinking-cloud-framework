package tcf.utils.reflect;

/**
 * 对象名name转换
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class ReflectBeanUtils {
	/**
	 * 属性名转方法名
	 * 
	 * @param prefix 方法名前缀
	 * @param fieldName 属性名
	 * @return 转换后的方法名
	 */
	public static String fieldName2MethodName(String prefix, String fieldName) {
		String firstChar = fieldName.substring(0, 1);
		return prefix + firstChar.toUpperCase() + fieldName.substring(1);
	}

	/**
	 * 方法名转属性名
	 * 
	 * @param prefix 方法名前缀
	 * @param methodName 方法名
	 * @return 转换后的属性名
	 */
	public static String methodName2FieldName(String prefix, String methodName) {
		methodName = methodName.replaceFirst(prefix, "");
		String firstChar = methodName.substring(0, 1);
		return firstChar.toLowerCase() + methodName.substring(1);
	}

	/**
	 * 
	 * @param prefix
	 * @param className
	 * @return
	 */
	public static String className2FieldName(String prefix, String className) {
		if (prefix != null && !prefix.equals("")) {
			className = className.replaceFirst(prefix, "");
		}
		String first = className.substring(0, 1).toLowerCase();
		String other = className.substring(1);
		return first + other;
	}

	/**
	 * 蛇形命名 转 驼峰命名
	 * 
	 * @param prefix        忽略的前后缀 前缀: xx_ 后缀:_xx
	 * @param firstLower    第一个字母是否小写 true: xxXxxXxx false:XxxXxx
	 * @param split         蛇形命名的分隔符
	 * @param snakeCaseName 蛇形命名
	 * @return 驼峰命名
	 */
	public static String snakeCase2CamelCase(String prefix, boolean firstLower, String split, String snakeCaseName) {
		// 忽略前缀
		if (prefix != null && !prefix.trim().equals("")) {
			snakeCaseName = snakeCaseName.replace(prefix, "");
		}
		// 如果没有split分隔符
		if (snakeCaseName.indexOf(split) == -1) {
			return snakeCaseName;
		}
		// 开始转换
		String[] snakeCaseIteamArray = snakeCaseName.split(split);
		StringBuilder className = new StringBuilder();
		String fisrt = null;
		String other = null;
		for (int i = 0; i < snakeCaseIteamArray.length; i++) {
			String tfn = snakeCaseIteamArray[i];
			if (i == 0 && firstLower) {
				fisrt = tfn.substring(0, 1).toLowerCase();
			} else {
				fisrt = tfn.substring(0, 1).toUpperCase();
			}
			other = tfn.substring(1);
			className.append(fisrt).append(other);
		}
		return className.toString();
	}

	/**
	 * 蛇形命名 转 驼峰命名
	 * 
	 * @param firstLower    第一个字母是否小写 true: xxXxxXxx false:XxxXxx
	 * @param snakeCaseName 蛇形命名
	 * @return 驼峰命名
	 */
	public static String snakeCase2CamelCase(boolean firstLower, String snakeCaseName) {
		return snakeCase2CamelCase(null, firstLower, "_", snakeCaseName);
	}

	/**
	 * 蛇形命名 转 rest请求命名
	 * 
	 * @return
	 */
	public static String snakeCase2RestPath(String prefix, String split, String snakeCaseName) {
		// 忽略前缀
		if (prefix != null && !prefix.trim().equals("")) {
			snakeCaseName = snakeCaseName.replace(prefix, "");
		}
		// 如果没有split分隔符
		if (snakeCaseName.indexOf(split) == -1) {
			return snakeCaseName;
		}
		// 开始转换
		String[] snakeCaseIteamArray = snakeCaseName.split(split);
		StringBuilder restPath = new StringBuilder();

		for (int i = 0; i < snakeCaseIteamArray.length; i++) {
			String tfn = snakeCaseIteamArray[i];
			restPath.append("/").append(tfn);
		}
		return restPath.toString();
	}
}
