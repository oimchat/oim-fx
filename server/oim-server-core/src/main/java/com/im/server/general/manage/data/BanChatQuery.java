package com.im.server.general.manage.data;

import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午11:02:39
 */
public class BanChatQuery extends ChatQuery {

	private String roomId;
	private String nickname;
	private String userId;
	private String address;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
