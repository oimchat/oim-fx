package com.im.base.common.box;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 存放一些全局配置
 * @author: XiaHui
 * @date: 2017-08-11 05:19:19
 */
public class ConfigBox {

	private static final Map<String, String> configMap = new ConcurrentSkipListMap<String, String>();
	private static final Map<String, Map<String, String>> categoryConfigMap = new ConcurrentSkipListMap<String, Map<String, String>>();

	/**
	 * 获取配置
	 * @author XiaHui
	 * @date 2017-11-25 10:29:31
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return configMap.get(key);
	}

	public static String put(String key, String value) {
		return configMap.put(key, value);
	}

	/**
	 * 按类别获取配置
	 * @author XiaHui
	 * @date 2017-11-25 10:29:43
	 * @param category
	 * @param key
	 * @return
	 */
	public static String get(String category, String key) {
		String value = null;
		Map<String, String> map = categoryConfigMap.get(category);
		if (null != map) {
			value = map.get(key);
		}
		return value;
	}

	/**
	 * 按类别存放配置
	 * @author XiaHui
	 * @date 2017-11-25 10:29:52
	 * @param category
	 * @param key
	 * @param value
	 * @return
	 */
	public static String put(String category, String key, String value) {
		Map<String, String> map = categoryConfigMap.get(category);
		if (null == map) {
			map = new ConcurrentSkipListMap<String, String>();
			categoryConfigMap.put(category, map);
		}
		return map.put(key, value);
	}
}
