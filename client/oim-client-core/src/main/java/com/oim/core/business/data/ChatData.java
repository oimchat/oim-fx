package com.oim.core.business.data;

import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * @author XiaHui
 * @date 2018-01-18 10:18:25
 */
public class ChatData {
	
	private UserData userData;
	private Content content;

	public ChatData(UserData userData, Content content) {
		this.userData = userData;
		this.content = content;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
