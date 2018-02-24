package com.im.server.general.business.push;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.server.PushMessage;

/**
 * @author: XiaHui
 * @date: 2016年8月26日 上午9:21:41
 */
@Service
public class UserChatPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;

	/**
	 * 推送私聊信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午11:31:04
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午11:31:04
	 */
	public void pushUserChat(String key, String sendUserId, String receiveUserId, Content content) {

		List<String> idList = new ArrayList<String>();
		idList.add(receiveUserId);
		idList.add(sendUserId);

		PushMessage message = new PushMessage();
		message.put("sendUserId", sendUserId);
		message.put("receiveUserId", receiveUserId);
		message.put("content", content);
		message.put("messageKey", key);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.2.1001");
		head.setKey(key);
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(idList, message);
	}

	public void userShake(String key, String sendUserId, String receiveUserId) {

		List<String> idList = new ArrayList<String>();
		idList.add(receiveUserId);
		idList.add(sendUserId);

		PushMessage message = new PushMessage();
		message.put("sendUserId", sendUserId);
		message.put("receiveUserId", receiveUserId);
		message.put("messageKey", key);
		
		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.2.1002");
		head.setKey(key);
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(idList, message);
	}
}
