package com.im.server.general.manage.data;

import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午11:02:39
 */
public class RoomChatQuery extends ChatQuery {

	private String roomId;
	private String userNickname;;
	private String userId;
	private String userRole;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
