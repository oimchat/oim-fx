package com.onlyxiahui.im.message.data.chat;

/**
 * @author XiaHui
 * @date 2018-01-06 22:51:17
 */
public class LastChat {

	private String chatId;
	private String type;

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static String TYPE_USER = "1";// 联系人
	public static String TYPE_GROUP = "2";// 群
	public static String TYPE_TEAM = "3";// 讨论组
	public static String TYPE_ROOM = "4";// 聊天室
	public static String TYPE_OFFICIAL = "5";// 公众号
}
