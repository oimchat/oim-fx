package com.onlyxiahui.im.bean;



/**
 * 描述：群成员
 * 
 * @author XiaHui
 * @date 2015年3月13日 下午8:30:06
 * @version 0.0.1
 */
public class GroupMember {


	private String id;
	private String groupId;// 帐号
	private String userId;
	private String position;

	//
	public static final String position_owner = "1";
	public static final String position_admin = "2";
	public static final String position_normal = "3";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
