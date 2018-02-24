package com.im.server.general.file.bean;

import javax.persistence.Entity;

/**
 * 文件信息
 * 
 * @author XiaHui
 * @date 2014-12-25 11:09:32
 */
@Entity(name = "im_group_head_data")
public class GroupHeadData extends FileBaseData {

	private String userId;
	private String groupId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
