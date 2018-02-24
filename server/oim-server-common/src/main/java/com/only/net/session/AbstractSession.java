package com.only.net.session;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月16日 上午11:30:40
 */
public abstract class AbstractSession implements SocketSession{
	
	String key;
	Map<Object, Object> attributeMap = new HashMap<Object, Object>();
	boolean auth=false;
	
	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	@Override
	public void setKey(String key) {
		this.key=key;
	}

	@Override
	public String getKey() {
		return key;
	}

	
	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	@Override
	public void write(Object object) {
		
	}

	@Override
	public void close() {
		
	}
}
