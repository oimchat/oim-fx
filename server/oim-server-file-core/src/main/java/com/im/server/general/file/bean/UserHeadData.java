package com.im.server.general.file.bean;

import javax.persistence.Entity;

/**
 * 文件信息
 * 
 * @author XiaHui
 * @date 2014-12-25 11:09:32
 */
@Entity(name = "im_user_head_data")
public class UserHeadData extends FileBaseData {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
