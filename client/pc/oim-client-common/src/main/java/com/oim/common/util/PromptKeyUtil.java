package com.oim.common.util;

/**
 * @author XiaHui
 * @date 2017-12-25 22:20:26
 */
public class PromptKeyUtil {

	public static String getUserChatPromptKey(String userId) {
		String key = "user_chat_" + userId;
		return key;
	}

	public static String getRoomChatPromptKey(String roomId) {
		String key = "room_chat_" + roomId;
		return key;
	}
}
