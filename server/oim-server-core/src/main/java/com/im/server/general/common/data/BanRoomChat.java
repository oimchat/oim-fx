package com.im.server.general.common.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: XiaHui
 * @date: 2016年8月24日 下午2:03:53
 */
public class BanRoomChat extends BanUserChat {

	private Map<String, String> outUserIdMap = new HashMap<String, String>();
	private Map<String, String> outAddressMap = new HashMap<String, String>();

	public void putExcludeUserId(String userId) {
		outUserIdMap.put(userId, userId);
	}

	public boolean isExcludeUserId(String userId) {
		return outUserIdMap.containsKey(userId);
	}

	public void putExcludeAddress(String address) {
		outAddressMap.put(address, address);
	}

	public boolean isExcludeAddress(String address) {
		return outAddressMap.containsKey(address);
	}
}
