package com.oim.core.common.action;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月17日 下午12:23:41
 * @version 0.0.1
 */
public abstract class CallActionAdapter implements CallAction {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	@Override
	public void execute() {
	}
}
