package com.oim.common.util;

import java.util.Map;

/**
 * @author: XiaHui
 * @date: 2018-01-16 16:56:31
 */
public class MapUtil {
	public static <V, K> V getOrDefault(Map<K, V> map, Object key, V defaultValue) {
		V v;
		return (((v = map.get(key)) != null) || map.containsKey(key)) ? v : defaultValue;
	}
}
