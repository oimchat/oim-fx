package com.im.server.general.common.bean;

import javax.persistence.Entity;

import com.im.base.bean.BaseBean;

/**
 * 被加好友防止骚扰的设置
 * 
 * @author XiaHui
 * @date 2017-11-24 10:18:13
 */
@Entity(name = "im_user_harass_setting")
public class UserHarassSetting extends BaseBean {

	private String userId;
	/**
	 * 1：允许任何人添加 <br>
	 * 2：需要验证 <br>
	 * 3：需要回答正确的问题 <br>
	 * 4：需要回答问题并由我确认 <br>
	 */
	private String requestVerifyType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRequestVerifyType() {
		return requestVerifyType;
	}

	public void setRequestVerifyType(String requestVerifyType) {
		this.requestVerifyType = requestVerifyType;
	}
}
