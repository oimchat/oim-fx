package com.im.server.general.common.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.util.KeyUtil;
import com.im.server.general.business.push.RoomChatPush;
import com.im.server.general.business.thread.SessionServerHandler;
import com.im.server.general.common.bean.RoomChatContent;
import com.im.server.general.common.bean.RoomChatItem;
import com.im.server.general.common.dao.RoomChatDAO;
import com.im.server.general.common.data.ChatItem;
import com.im.server.general.common.manager.ChatManager;
import com.im.server.general.common.manager.LastChatManager;
import com.im.server.general.common.service.api.RoomBaseService;
import com.im.server.general.common.service.api.UserBaseService;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月16日 上午9:38:51
 */
@Service
public class RoomChatService {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	UserBaseService userBaseService;
	@Resource
	RoomBaseService roomBaseService;
	@Resource
	ChatManager chatManager;
	@Resource
	RoomChatDAO roomChatDAO;
	@Resource
	RoomChatPush roomChatPush;
	@Resource
	SessionServerHandler sessionServerHandler;
	@Resource
	LastChatManager lastChatManager;

	/**
	 * 聊天室聊天信息推送，将用户所发的信息推送到其所在聊天室的其它用户
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月19日 下午3:25:08
	 * @update: XiaHui
	 * @updateDate: 2016年8月19日 下午3:25:08
	 */
	@Transactional
	public void roomChat(String key, String userId, String roomId, Content content) {
		List<ChatItem> chatItemList = chatManager.wordsFilter(content);
		long timestamp = content.getTimestamp();
		UserData userData = userBaseService.getUserData(userId);
		pushRoomChat(key, userId, roomId, content);
		addRoomChatLog(userData, key, roomId, chatItemList, timestamp);
	}

	public void pushRoomChat(String key, String userId, String roomId, Content content) {
		List<String> userIdList = roomBaseService.getUserIdList(roomId);
		roomChatPush.pushRoomChat(key, roomId, userId, content, userIdList);
	}

	/**
	 * 记录聊天室的聊天内容
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月19日 下午3:26:10
	 * @update: XiaHui
	 * @updateDate: 2016年8月19日 下午3:26:10
	 */
	@Transactional
	private void addRoomChatLog(UserData userData, String messageId, String roomId, List<ChatItem> chatItemList, long timestamp) {
		messageId = (null == messageId || "".equals(messageId)) ? KeyUtil.getKey() : messageId;
		if (null != userData) {
			RoomChatContent content = createRoomChatContent(messageId, roomId, userData, timestamp);
			roomChatDAO.save(content);
			List<RoomChatItem> list = new ArrayList<RoomChatItem>();
			for (ChatItem item : chatItemList) {
				RoomChatItem chatLog = new RoomChatItem();
				chatLog.setMessageId(messageId);
				chatLog.setRoomChatContentId(content.getId());
				chatLog.setRoomId(content.getRoomId());
				chatLog.setUserId(content.getUserId());
				chatLog.setRank(item.getRank());
				chatLog.setSection(item.getSection());
				chatLog.setType(item.getType());
				chatLog.setValue(item.getValue());
				chatLog.setFilterValue(item.getFilterValue());
				list.add(chatLog);
			}
			roomChatDAO.saveList(list);
		}
	}

	private RoomChatContent createRoomChatContent(String messageId, String roomId, UserData userData, long timestamp) {
		RoomChatContent chatLog = new RoomChatContent();
		chatLog.setMessageId(messageId);
		chatLog.setRoomId(roomId);
		chatLog.setRoomName("");
		chatLog.setTime(new Timestamp(timestamp));
		chatLog.setTimestamp(timestamp);
		chatLog.setUserHead(userData.getHead());
		chatLog.setUserId(userData.getId());
		chatLog.setUserName(userData.getName());
		chatLog.setUserNickname(userData.getNickname());
		chatLog.setUserRemark("");
		chatLog.setIsSend("1");
		return chatLog;
	}

	public void pushRoomChatInfo(String roomId, UserData userData, Content content) {
		List<String> userIdList = roomBaseService.getUserIdList(roomId);
		roomChatPush.pushRoomChatInfo(roomId, userData, content, userIdList);
	}
}
