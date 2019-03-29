package com.jjn.mall.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	
	public static ApplicationContext getContext(){
		return context;
	}
	
	public final static Object getBean(String beanName){
		return context.getBean(beanName);
	}

	public final static Object getBean(String beanName, Class<?> requiredType) {
		return context.getBean(beanName, requiredType);
	}
}
