package com.onlyxiahui.im.bean;

/**
 * 描述：好友分组成员
 * 
 * @author XiaHui
 * @date 2014-06-15 2:32:18
 * @version 0.0.1
 */
public class UserCategoryMember implements CategoryMember {

	private String id;
	private String ownUserId;
	private String userCategoryId;
	private String memberUserId;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnUserId() {
		return ownUserId;
	}

	public void setOwnUserId(String ownUserId) {
		this.ownUserId = ownUserId;
	}

	public String getUserCategoryId() {
		return userCategoryId;
	}

	public void setUserCategoryId(String userCategoryId) {
		this.userCategoryId = userCategoryId;
	}

	public String getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getCategoryId() {
		return this.getUserCategoryId();
	}

	@Override
	public String getMemberId() {
		return this.getMemberUserId();
	}
}
