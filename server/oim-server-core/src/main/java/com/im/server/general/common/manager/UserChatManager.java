package com.im.server.general.common.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.util.KeyUtil;
import com.im.server.general.common.bean.UserChatContent;
import com.im.server.general.common.bean.UserChatItem;
import com.im.server.general.common.bean.UserLastChat;
import com.im.server.general.common.dao.UserChatDAO;
import com.im.server.general.common.dao.UserLastChatDAO;
import com.im.server.general.common.data.ChatItem;
import com.onlyxiahui.im.message.data.UserData;

/**
 * 
 * @author XiaHui
 * @date 2017-11-26 13:01:51
 *
 */
@Service
public class UserChatManager {

	@Resource
	UserChatDAO userChatDAO;
	@Resource
	UserLastChatDAO userLastChatDAO;

	@Transactional
	public void addUserChatLog(
			boolean isSend, 
			UserData sendUserData, 
			UserData receiveUserData, 
			String messageId,
			List<ChatItem> chatItemList, 
			long timestamp) {
		messageId = (null == messageId || "".equals(messageId)) ? KeyUtil.getKey() : messageId;
		UserChatContent content = createUserChatContent(isSend, messageId, sendUserData, receiveUserData, timestamp);
		userChatDAO.save(content);
		List<UserChatItem> list = new ArrayList<UserChatItem>();
		for (ChatItem item : chatItemList) {
			UserChatItem chatLog = new UserChatItem();
			chatLog.setMessageId(messageId);
			chatLog.setUserChatContentId(content.getId());
			chatLog.setSendUserId(content.getSendUserId());
			chatLog.setReceiveUserId(content.getReceiveUserId());
			chatLog.setRank(item.getRank());
			chatLog.setSection(item.getSection());
			chatLog.setType(item.getType());
			chatLog.setValue(item.getValue());
			chatLog.setFilterValue(item.getFilterValue());
			list.add(chatLog);
		}
		userChatDAO.saveList(list);
		String sendId = sendUserData.getId();
		String receiveId = receiveUserData.getId();
		UserLastChat userLastChat1 = createUserLastChat(messageId, content.getId(), sendId, receiveId, UserLastChat.TYPE_USER);
		saveOrUpdateLast(userLastChat1);

		if (!sendId.equals(receiveId)) {
			UserLastChat userLastChat2 = createUserLastChat(messageId, content.getId(), receiveId, sendId, UserLastChat.TYPE_USER);
			saveOrUpdateLast(userLastChat2);
		}
	}

	private UserChatContent createUserChatContent(boolean isSend, String messageId, UserData sendUserData, UserData receiveUserData, long timestamp) {
		UserChatContent chatLog = new UserChatContent();
		chatLog.setMessageId(messageId);
		chatLog.setIsDeleted("0");
		chatLog.setIsSend(isSend ? "1" : "0");
		chatLog.setReceiveUserHead(receiveUserData.getHead());
		chatLog.setReceiveUserId(receiveUserData.getId());
		chatLog.setReceiveUserName(receiveUserData.getName());
		chatLog.setReceiveUserNickname(receiveUserData.getNickname());
		chatLog.setTime(new Timestamp(timestamp));
		chatLog.setTimestamp(timestamp);
		chatLog.setSendUserHead(sendUserData.getHead());
		chatLog.setSendUserId(sendUserData.getId());
		chatLog.setSendUserName(sendUserData.getName());
		chatLog.setSendUserNickname(sendUserData.getNickname());
		chatLog.setSendUserRemark("");
		return chatLog;
	}

	private UserLastChat createUserLastChat(String messageId, String contentId, String userId, String chatId, String type) {

		long timestamp = System.currentTimeMillis();

		UserLastChat bean = new UserLastChat();
		bean.setChatId(chatId);
		bean.setContentId(contentId);
		bean.setMessageId(messageId);
		bean.setTime(new Timestamp(timestamp));
		bean.setTimestamp(timestamp);
		bean.setType(type);
		bean.setUserId(userId);
		return bean;
	}

	private void saveOrUpdateLast(UserLastChat userLastChat) {
		saveOrUpdate(userLastChat);
	}

	public void saveOrUpdate(UserLastChat userLastChat) {
		UserLastChat bean = userLastChatDAO.getUserLastChat(userLastChat.getUserId(), userLastChat.getChatId(), userLastChat.getType());
		if (null != bean) {
			long timestamp = System.currentTimeMillis();
			bean.setMessageId(userLastChat.getMessageId());
			bean.setTime(new Timestamp(timestamp));
			bean.setTimestamp(timestamp);
			bean.setContentId(userLastChat.getContentId());
			userLastChatDAO.update(bean);
		} else {
			userLastChatDAO.save(userLastChat);
		}
	}
}
