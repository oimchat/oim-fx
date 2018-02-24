package com.im.server.general.business.push;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.PushMessage;
import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.im.base.common.util.KeyUtil;

/**
 * @author: XiaHui
 * @date: 2016年8月26日 上午9:21:41
 */
@Service
public class RoomPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;

	/**
	 * 推送用户加入聊天室信息
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午10:42:50
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午10:42:50
	 */
	public void pushUserJoinRoom(String roomId, UserData userData, List<String> userIdList) {
		PushMessage message=new PushMessage();
		message.put("roomId", roomId);
		message.put("userId", userData.getId());
		message.put("userData", userData);

		Head head = new Head();
		head.setAction("1-300");
		head.setMethod("1-20001");
		head.setKey(KeyUtil.getKey());
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}

	/**
	 * 推送用户退出聊天室信息
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午10:43:10
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午10:43:10
	 */
	public void pushRoomRemoveUser(String roomId, String userId, List<String> userIdList) {
		PushMessage message=new PushMessage();
		message.put("roomId", roomId);
		message.put("userId", userId);

		Head head = new Head();
		head.setAction("1-300");
		head.setMethod("1-20002");
		head.setKey(KeyUtil.getKey());
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}

	/**
	 * 推送聊天室禁言信息
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午11:30:09
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午11:30:09
	 */
	public void pushRoomBan(String roomId, boolean isBan, List<String> userIdList) {
		PushMessage message=new PushMessage();
		message.put("roomId", roomId);
		message.put("isBan", isBan);

		Head head = new Head();
		head.setAction("1-300");
		head.setMethod("1-20012");
		head.setKey(KeyUtil.getKey());
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userIdList, message);
	}

	public void pushRoomUserBan(String roomId, String userId, boolean isBan,long time) {
		PushMessage message=new PushMessage();
		message.put("roomId", roomId);
		message.put("userId", userId);
		message.put("isBan", isBan);
		message.put("time", time);

		Head head = new Head();
		head.setAction("1-300");
		head.setMethod("1-20013");
		head.setKey(KeyUtil.getKey());
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		messageQueueWriteHandler.push(userId, message);
	}
}
