package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

/**
 * 描述：群成员
 * 
 * @author XiaHui
 * @date 2015年3月13日 下午8:30:06
 * @version 0.0.1
 */
@Entity(name = "im_group_member")
public class GroupMember extends BaseBean {

	private String groupId;// 帐号
	private String userId;// 群成员id
	private String position;// 权限

	//
	public static final String position_owner = "1";// 群主
	public static final String position_admin = "2";// 管理员
	public static final String position_normal = "3";// 普通成员

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
