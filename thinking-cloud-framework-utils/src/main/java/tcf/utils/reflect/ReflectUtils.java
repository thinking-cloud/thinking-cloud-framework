package tcf.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 反射的工具类
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public class ReflectUtils {
	
	
	/**
	 * 获取指定类及父类的指定注解
	 * @param clazz 指定类
	 * @return
	 */
	public static Annotation annotationBySupperClass(Class clazz,Class annotationClass){
		Annotation annotation = clazz.getAnnotation(annotationClass);

		while(annotation==null && clazz!=Object.class) {
			annotation = clazz.getAnnotation(annotationClass);
			clazz = clazz.getSuperclass();
		}
		return annotation;
	}
	
    /**
     *  获取类中所有属性
     * @param cl 指定類
     * @param isSupper 是否获取所有父类中的属性
     * @param exculdeFieldNames 排除的属性名
     * @return
     */
    public static List<Field> fields(Class<?> cl, boolean isSupper, String... exculdeFieldNames) {
        Field[] fields = null;
        List<Field> resultlist = new ArrayList<>();
        List<String> exculdeFieldNameList = null;
        if(exculdeFieldNames!=null && exculdeFieldNames.length > 0) {
            exculdeFieldNameList = Arrays.asList(exculdeFieldNames);
        }
        while(cl!=null && cl!=Object.class) {
            if(isSupper) {
                fields = cl.getDeclaredFields();
                cl = cl.getSuperclass();
            }else {
                fields = cl.getDeclaredFields();
            }
            for (Field field : fields) {
                if(exculdeFieldNameList == null || !exculdeFieldNameList.contains(field.getName())) {
                    resultlist.add(field);
                }
            }
        }
        return resultlist;
    }

    /**
     *  获取类中指定的属性名
     * @param cl 指定類
     * @param isSupper 是否获取所有父类中的属性
     * @param fieldName 指定的属性名
     * @return
     */
    public static Field field(Class<?> cl,boolean isSupper,String fieldName)  {
		Field field = null;
        while(cl!=null && cl!=Object.class) {
        	try {
	             field = cl.getDeclaredField(fieldName);
	             break;
        	}catch (NoSuchFieldException e) {
        		// 异常, 表示没有找到
        		if(isSupper) {
        			// 需要继续查找父类
        			cl = cl.getSuperclass();
        		}else {
        			// 不需要查找父类
        			break;
        		}
			}
 
        }
		return field;
    }

    /**
     * 获取指定对象上父类的泛型
     * @param obj
     * @return
     */
    public static Type[] genericBySuperClass(Object obj) {
        Type genericSuperclass = obj.getClass().getGenericSuperclass();
        if(genericSuperclass == Object.class) {
            throw new RuntimeException("指定对象上找不到有效父类");
        }else {
            return ((ParameterizedType)genericSuperclass).getActualTypeArguments();
        }
    }


    /**
     * 获取指定对象上父接口的泛型
     * @param obj
     * @return
     */
    @SuppressWarnings({ "rawtypes"})
    public static Type[] genericBySuperClass(Object obj,Class interfaceClass) {
        Type[] interfaces = obj.getClass().getGenericInterfaces();
        if(interfaces != null && interfaces.length > 0) {
            for (Type type : interfaces) {
                if(type.getClass() == interfaceClass) {
                    return ((ParameterizedType)type).getActualTypeArguments();
                }
            }
        }
        throw new RuntimeException("指定对象上找不到指定的接口");
    }
    
    /**
     * 获取指定的属性值
     * @param <T> 属性值类型的泛型
     * @param target 目标对象
     * @param isSupper 是否包含父类
     * @param fieldName 属性名
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
	public static <T> T fieldValue(Object target,boolean isSupper,String fieldName) {
    	T value = null;
    	try {
	    	Field field = field(target.getClass(), isSupper, fieldName);
	    	if(field  != null) {
	    		field.setAccessible(true);
	    		value = (T) field.get(target);
	    	}
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		}
    	return value;
    }
    
    /**
     * 设置指定的属性值
     * @param <T> 属性值类型的泛型
     * @param target 目标对象
     * @param isSupper 是否包含父类
     * @param fieldName 属性名
     * @param fieldValue 属性值
     */
	public static <T> void fieldValue(Object target,boolean isSupper,String fieldName, T fieldValue) {

    	try {
	    	Field field = field(target.getClass(), isSupper, fieldName);
	    	if(field  != null) {
	    		field.setAccessible(true);
	    		field.set(target, fieldValue);
	    	}
    	}catch (Exception e) {
    		throw new RuntimeException(e);
		}
    }
}
