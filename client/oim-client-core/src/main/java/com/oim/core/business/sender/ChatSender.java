package com.oim.core.business.sender;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class ChatSender extends BaseSender {

	public ChatSender(AppContext appContext) {
		super(appContext);
	}

	public void getLastChatList(int count) {
		Message message = new Message();
		message.put("userId", "");
		message.put("count", count);
		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	public void getLastChatWithDataList(int count) {
		Message message = new Message();
		message.put("userId", "");
		message.put("count", count);
		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.0002");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
