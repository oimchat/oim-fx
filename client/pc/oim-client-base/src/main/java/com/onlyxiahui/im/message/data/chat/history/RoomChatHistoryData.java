package com.onlyxiahui.im.message.data.chat.history;

import com.onlyxiahui.im.bean.Room;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

public class RoomChatHistoryData {

	private String messageKey;
	private String contentId;
	private UserData userData;
	private Room room;
	private Content content;

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
