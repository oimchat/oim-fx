package com.im.server.general.common.data;

import java.util.Date;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午10:53:36
 */
public class UserChatLogQuery {

	private String chatSendNickname;
	private String chatReceiveNickname;
	private String chatReceiveUserId;
	private String chatSendUserId;
	private String chatUserRole;
	private String text;
	private Date startDate;
	private Date endDate;

	public String getChatSendNickname() {
		return chatSendNickname;
	}

	public void setChatSendNickname(String chatSendNickname) {
		this.chatSendNickname = chatSendNickname;
	}

	public String getChatReceiveNickname() {
		return chatReceiveNickname;
	}

	public void setChatReceiveNickname(String chatReceiveNickname) {
		this.chatReceiveNickname = chatReceiveNickname;
	}

	public String getChatReceiveUserId() {
		return chatReceiveUserId;
	}

	public void setChatReceiveUserId(String chatReceiveUserId) {
		this.chatReceiveUserId = chatReceiveUserId;
	}

	public String getChatSendUserId() {
		return chatSendUserId;
	}

	public void setChatSendUserId(String chatSendUserId) {
		this.chatSendUserId = chatSendUserId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getChatUserRole() {
		return chatUserRole;
	}

	public void setChatUserRole(String chatUserRole) {
		this.chatUserRole = chatUserRole;
	}

}
