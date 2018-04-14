package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 16:32:57
 */
public class GroupChatSender extends BaseSender {

	public GroupChatSender(AppContext appContext) {
		super(appContext);
	}

	public void sendGroupChatMessage(String messageId, String groupId, String userId, Content content, DataBackActionAdapter action) {
		Message message = new Message();

		message.put("groupId", groupId);// 接受信息的群id
		message.put("userId", userId);// 发送人的id
		message.put("content", content);// 聊天内容

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.2001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	public void queryGroupChatLog(String groupId, ChatQuery chatQuery, PageData page, DataBackAction action) {
		Message message = new Message();

		message.put("groupId", groupId);// 接受信息的群id
		message.put("chatQuery", chatQuery);// 聊天内容
		message.put("page", page);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.2004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

}
