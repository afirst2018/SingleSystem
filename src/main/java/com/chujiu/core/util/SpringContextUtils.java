package com.chujiu.core.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @ClassName: SpringContextUtils 
 * @Description: 获取ApplicationContext和Object的工具类 
 * @author suliang
 * @date 2016年5月16日 上午11:47:42 
 *
 */
@SuppressWarnings("all")
public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	/**
	 * 
	 * @Title: getApplicationContext 
	 * @Description: 获取spring上下文
	 * @author suliang
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}
	
	/**
	 * 
	 * @Title: getApplicationContext 
	 * @Description: 获取applicationContext对象
	 * @author suliang
	 *
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext(){
		return SpringContextUtils.applicationContext;
	}
	
	/**
	 * 
	 * @Title: getBeanById 
	 * @Description: 根据bean的id来查找对象
	 * @author suliang
	 *
	 * @param id
	 * @return Object
	 */
	public static Object getBeanById(String id){
		return SpringContextUtils.applicationContext.getBean(id);
	}
	
	/**
	 * 
	 * @Title: getBeanByClass 
	 * @Description: 根据bean的class来查找对象
	 * @author suliang
	 *
	 * @param clazz
	 * @return Object
	 */
	public static Object getBeanByClass(Class clazz){
		return SpringContextUtils.applicationContext.getBean(clazz);
	}
	
	/**
	 * 
	 * @Title: getAllBeansByClass 
	 * @Description: 根据bean的class来查找所有的对象(包括子类)
	 * @author suliang
	 *
	 * @param clazz
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> getAllBeansByClass(Class clazz){
		return SpringContextUtils.applicationContext.getBeansOfType(clazz);
	}
}