package com.onlyxiahui.im.bean;

import java.util.Date;

/**
 * @author: XiaHui
 * @date: 2017年6月5日 下午3:21:13
 */
public class SecurityQuestion {
	
	private String id;
	private String userId;
	private String question;
	private String answer;
	private Date createTime;// 建立时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
