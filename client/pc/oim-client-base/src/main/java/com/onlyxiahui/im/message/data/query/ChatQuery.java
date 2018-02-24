package com.onlyxiahui.im.message.data.query;

import java.util.Date;

/**
 * @author: XiaHui
 * @date: 2016-08-23 11:02:39
 */
public class ChatQuery {

	private String text;
	private Date startDate;
	private Date endDate;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
