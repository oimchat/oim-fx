package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @description:聊天室的聊天记录
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_room_chat_content")
public class RoomChatContent extends BaseBean {

	private String messageId;// 消息id
	private String roomId;// 聊天室id
	private String roomName;// 聊天室名称
	private String userId;// 发送消息用户id
	@Column(length = 500)
	private String userHead;// 用户头像
	private String userHeadType;
	private int userPosition;// 权限;//
	private String userRole;//
	private String userName;//
	private String userNickname;//
	private String userRemark;//
	@Column(length = 2)
	private String isDeleted;//
	@Column(length = 2)
	private String isSend;//
	private Timestamp time;//
	private long timestamp;//

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUserHeadType() {
		return userHeadType;
	}

	public void setUserHeadType(String userHeadType) {
		this.userHeadType = userHeadType;
	}

	public int getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(int userPosition) {
		this.userPosition = userPosition;
	}

}
