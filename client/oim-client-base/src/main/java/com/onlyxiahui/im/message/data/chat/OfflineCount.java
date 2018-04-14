package com.onlyxiahui.im.message.data.chat;

/**
 * @author XiaHui
 * @date 2017-12-01 16:51:14
 */
public class OfflineCount {

	String sendUserId;
	int count;

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
