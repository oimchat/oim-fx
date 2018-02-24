package com.only.common.resolver;

/**
 * @author: XiaHui
 * date: 2016-09-28 10:47:43
 */
public interface BeanBox {

	void register(Class<?> requiredType, Object object);

	<T> T getBean(Class<T> requiredType);
}