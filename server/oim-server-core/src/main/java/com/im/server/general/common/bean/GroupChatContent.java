package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * @description:群的聊天记录
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_group_chat_content")
public class GroupChatContent extends BaseBean {

	private String messageId;// 消息id
	private String groupId;// id
	private String groupName;// 聊天室名称
	private String userId;// 发送消息用户id
	private String userHead;// 用户头像
	private String userHeadType;
	private int userPosition;//
	private String userName;//
	private String userNickname;//
	private String userRemark;//
	private String isDeleted;//
	private String isSend;//
	private Timestamp time;//
	private long timestamp;//

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
