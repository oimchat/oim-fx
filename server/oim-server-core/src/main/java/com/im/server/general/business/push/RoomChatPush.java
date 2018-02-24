package com.im.server.general.business.push;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.base.common.util.KeyUtil;
import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.server.PushMessage;

/**
 * @author: XiaHui
 * @date: 2016年8月26日 上午9:21:41
 */
@Service
public class RoomChatPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;

	/**
	 * 推送聊天室聊天信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午11:31:49
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午11:31:49
	 */
	public void pushRoomChat(String key, String roomId, String userId, Content content, List<String> userIdList) {
		PushMessage message = new PushMessage();
		message.put("roomId", roomId);
		message.put("userId", userId);
		message.put("content", content);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.2.3001");
		head.setKey(key);
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}

	public void pushRoomChatInfo(String roomId, UserData userData, Content content, List<String> userIdList) {
		PushMessage message = new PushMessage();
		message.put("roomId", roomId);
		message.put("userData", userData);
		message.put("content", content);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.2.3003");
		head.setKey(KeyUtil.getKey());
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}
}
