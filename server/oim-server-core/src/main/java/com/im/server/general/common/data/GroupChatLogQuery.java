package com.im.server.general.common.data;

import java.util.Date;
import java.util.List;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午10:53:36
 */
public class GroupChatLogQuery {

	private String groupId;
	private String userId;
	private String userNickname;
	private String userRole;
	private String text;
	private Date startDate;
	private Date endDate;
	private List<String> messageIds;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
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

}
