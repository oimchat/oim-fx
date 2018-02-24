package com.im.server.general.common.data;

import java.util.Date;

public class ChatData {
	
	private String receiveUserId;
	private String receiveUserHead;// 用户头像
	private String receiveUserRole;//
	private String receiveUserName;//
	private String receiveUserNickname;//

	private String sendUserId;// 发送消息用户id
	private String sendUserHead;// 用户头像
	private String sendUserRole;//
	private String sendUserName;//
	private String sendUserNickname;//

	public Date time;

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserHead() {
		return receiveUserHead;
	}

	public void setReceiveUserHead(String receiveUserHead) {
		this.receiveUserHead = receiveUserHead;
	}

	public String getReceiveUserRole() {
		return receiveUserRole;
	}

	public void setReceiveUserRole(String receiveUserRole) {
		this.receiveUserRole = receiveUserRole;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getReceiveUserNickname() {
		return receiveUserNickname;
	}

	public void setReceiveUserNickname(String receiveUserNickname) {
		this.receiveUserNickname = receiveUserNickname;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserHead() {
		return sendUserHead;
	}

	public void setSendUserHead(String sendUserHead) {
		this.sendUserHead = sendUserHead;
	}

	public String getSendUserRole() {
		return sendUserRole;
	}

	public void setSendUserRole(String sendUserRole) {
		this.sendUserRole = sendUserRole;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getSendUserNickname() {
		return sendUserNickname;
	}

	public void setSendUserNickname(String sendUserNickname) {
		this.sendUserNickname = sendUserNickname;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
