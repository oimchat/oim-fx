package com.im.common.interceptor;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 
 * @author XiaHui
 * @date 2017-11-25 11:22:34
 *
 */
public class WebMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object object, Method method, Object[] array, MethodProxy proxy) throws Throwable {
		System.out.println(object.getClass()+"Method:"+method.getName());
		return null;
	}
}
