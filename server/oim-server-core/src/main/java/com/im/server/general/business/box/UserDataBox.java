package com.im.server.general.business.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.onlyxiahui.im.message.data.UserData;

/**
 * @author Only
 * @date 2016年5月24日 上午9:28:08
 */
public class UserDataBox {

	private static Map<String, UserData> userDataMap = new ConcurrentSkipListMap<String, UserData>();

	/**
	 * 放入用户信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:21
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:21
	 */
	public static void put(String key, UserData userDate) {
		userDataMap.put(key, userDate);
	}

	/**
	 * 获取用户信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:29
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:29
	 */
	public static UserData get(String key) {
		return null == key ? null : userDataMap.get(key);
	}

	/**
	 * 获取所有用户
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:38
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:38
	 */
	public static List<UserData> getAll() {
		return new ArrayList<UserData>(userDataMap.values());
	}

	public static UserData remove(String key) {
		return userDataMap.remove(key);
	}
}
