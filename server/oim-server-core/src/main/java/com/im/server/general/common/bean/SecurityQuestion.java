package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.util.Date;

import javax.persistence.Entity;

/**
 * @author: XiaHui
 * @date: 2017年6月5日 下午3:21:13
 */
@Entity(name = "im_security_question")
public class SecurityQuestion extends BaseBean {

	private String userId;
	private String question;
	private String answer;
	private Date createTime;// 建立时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
