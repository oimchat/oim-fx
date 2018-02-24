package com.im.base.common.util;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月16日 上午11:08:16
 */
public class CacheKeyUtil {
	/**
	 * 用户令牌缓存key前缀
	 */
	private static final String CACHE_KEY_TOKEN = "cache_key_token";

	public static String getTokenCacheKey(String key) {
		StringBuilder sb = new StringBuilder(CACHE_KEY_TOKEN);
		sb.append(key);
		return sb.toString();
	}
}
