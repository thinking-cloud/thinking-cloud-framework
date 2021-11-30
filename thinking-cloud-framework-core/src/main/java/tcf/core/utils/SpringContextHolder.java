package tcf.core.utils;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 获取Spring上下文的工具类
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {

	private static DefaultListableBeanFactory defaultListableBeanFactory;
	private static ApplicationContext applicationContext;

	/**
	 * 注册bean到spring容器中
	 *
	 * @param beanName 名称
	 * @param clazz   class
	 */
	public static boolean registerBean(String beanName, Class<?> clazz) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		if (defaultListableBeanFactory.containsBean(beanName)) {
			return false;
		}
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
		return true;
	}

	/**
	 * 注册bean到spring容器中
	 *
	 * @param beanName 名称
	 * @param clazz    class
	 */
	public static boolean registerBean(String beanName, Class<?> clazz, Object... values) {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		if (defaultListableBeanFactory.containsBean(beanName)) {
			return false;
		}
		for (Object val : values) {
			beanDefinitionBuilder.addConstructorArgValue(val);
		}
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getRawBeanDefinition());
		return true;
	}

	/**
	 * 删除ioc中的bean
	 * 
	 * @param beanName bean的ID
	 * @return
	 */
	public static Object removeBean(String beanName) {
		if (defaultListableBeanFactory.containsBean(beanName)) {

			defaultListableBeanFactory.removeBeanDefinition(beanName);
		}
		return true;
	}

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		this.defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
	}

	/** 取得存储在静态变量中的ApplicationContext. */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * 
	 * @param <T>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型. 如果有多个Bean符合Class, 取出第一个.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	/**
	 * 验证是否注入applicaitonContext
	 */
	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}