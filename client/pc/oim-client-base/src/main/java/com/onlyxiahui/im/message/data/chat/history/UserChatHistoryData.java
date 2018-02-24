package com.onlyxiahui.im.message.data.chat.history;

import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

public class UserChatHistoryData {
	
	private String messageKey;
	private String contentId;
	private UserData sendUserData;
	private UserData receiveUserData;
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

	public UserData getSendUserData() {
		return sendUserData;
	}

	public void setSendUserData(UserData sendUserData) {
		this.sendUserData = sendUserData;
	}

	public UserData getReceiveUserData() {
		return receiveUserData;
	}

	public void setReceiveUserData(UserData receiveUserData) {
		this.receiveUserData = receiveUserData;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

}
