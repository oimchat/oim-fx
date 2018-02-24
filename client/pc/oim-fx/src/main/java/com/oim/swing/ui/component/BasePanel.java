package com.oim.swing.ui.component;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;


/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月24日 下午9:59:25
 * @version 0.0.1
 */
public class BasePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}
}
