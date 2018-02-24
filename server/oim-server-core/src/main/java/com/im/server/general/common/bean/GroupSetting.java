package com.im.server.general.common.bean;

/**
 * @author XiaHui
 * @date 2017-11-24 10:44:00
 */
public class GroupSetting {

	private String groupId;
	private String joinType;
	private String inviteType;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getInviteType() {
		return inviteType;
	}

	public void setInviteType(String inviteType) {
		this.inviteType = inviteType;
	}

}
