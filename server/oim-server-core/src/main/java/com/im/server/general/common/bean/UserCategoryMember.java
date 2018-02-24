package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

/**
 * 描述：好友分组成员
 * 
 * @author XiaHui
 * @date 2014年6月15日 下午2:32:18
 * @version 0.0.1
 */
@Entity(name = "im_user_category_member")
public class UserCategoryMember extends BaseBean{

	private String ownUserId;//拥有者id
	private String userCategoryId;//分组id
	private String memberUserId;//成员id
	private String remark;//备注名

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

}
