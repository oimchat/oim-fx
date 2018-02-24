package com.im.server.general.common.data;

import java.util.Date;
import java.util.List;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午10:53:36
 */
public class RoomChatQuery {

	private String roomId;
	private List<String> messageIds;
	private String text;
	private Date startDate;
	private Date endDate;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
	}

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
