package com.im.server.general.business.push;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.server.PushMessage;

/**
 * 群消息推送
 * 
 * @author: XiaHui
 * @date: 2016-08-26 9:21:41
 */
@Service
public class GroupChatPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;

	/**
	 * 推送群聊信息
	 * 
	 * @author XiaHui
	 * @date 2017-11-23 13:57:06
	 * @param key
	 * @param groupId
	 * @param userId
	 * @param content
	 * @param userIdList
	 */
	public void groupChat(String key, String groupId, String userId, Content content, List<String> userIdList) {
		PushMessage message = new PushMessage();
		message.put("groupId", groupId);
		message.put("userId", userId);
		message.put("content", content);
		message.put("messageKey", key);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.2.2001");
		head.setKey(key);
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}
}
