package com.im.server.general.manage.data;

import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午11:02:39
 */
public class UserChatQuery extends ChatQuery {

	private String nickname;
	private String userId;
	private String userRole;
	private String sendUserId;
	private String sendNickname;
	private String receiveUserId;
	private String receiveNickname;

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

	public String getSendNickname() {
		return sendNickname;
	}

	public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}

	public String getReceiveNickname() {
		return receiveNickname;
	}

	public void setReceiveNickname(String receiveNickname) {
		this.receiveNickname = receiveNickname;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
