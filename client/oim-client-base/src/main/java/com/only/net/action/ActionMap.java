package com.only.net.action;

import java.lang.reflect.Method;

/**
 * @Author XiaHui
 * @Date 2017-09-21 02:14:54
 */
public interface ActionMap {
	
	
	public Object getAction(String path);

	public Method getMethod(String path) ;
	
}
