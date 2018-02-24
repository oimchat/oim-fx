package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月15日 下午2:32:18
 * @version 0.0.1
 */
@Entity(name = "im_group_category_member")
public class GroupCategoryMember extends BaseBean{


	private String userId;// 所属用户id
	private String groupCategoryId;//群分组id
	private String groupId;//群id
	private String remark;//备注


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupCategoryId() {
		return groupCategoryId;
	}

	public void setGroupCategoryId(String groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
