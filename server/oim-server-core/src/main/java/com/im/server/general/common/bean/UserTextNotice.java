package com.im.server.general.common.bean;

import java.util.Date;

import javax.persistence.Entity;

import com.im.base.bean.BaseBean;

/**
 * 
 * @author XiaHui
 * @date 2016年1月4日 下午9:18:55
 * @version 0.0.1
 */
@Entity(name = "im_user_text_notice")
public class UserTextNotice extends BaseBean {

	private String userId;
	private String textNoticeId;
	private Date createTime;// 建立时间
	private String isRead;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTextNoticeId() {
		return textNoticeId;
	}

	public void setTextNoticeId(String textNoticeId) {
		this.textNoticeId = textNoticeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

}
