package com.im.server.general.common.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.util.KeyUtil;
import com.im.server.general.common.bean.GroupChatContent;
import com.im.server.general.common.bean.GroupChatItem;
import com.im.server.general.common.bean.UserLastChat;
import com.im.server.general.common.dao.GroupChatDAO;
import com.im.server.general.common.dao.UserLastChatDAO;
import com.im.server.general.common.data.ChatItem;
import com.onlyxiahui.im.message.data.UserData;

/**
 * @author: XiaHui
 * @date: 2017年4月26日 下午4:44:17
 */
@Service
public class GroupChatManager {

	@Resource
	GroupChatDAO groupChatDAO;
	@Resource
	UserLastChatDAO userLastChatDAO;

	@Transactional
	public void addGroupChatLog(
			UserData userData,
			String messageId,
			String groupId,
			List<ChatItem> chatItemList,
			long timestamp) {
		messageId = (null == messageId || "".equals(messageId)) ? KeyUtil.getKey() : messageId;

		List<String> messageIds = new ArrayList<String>();
		messageIds.add(messageId);
		List<GroupChatItem> hasList = groupChatDAO.getGroupChatItemListByMessageIds(messageIds);
		if (null != hasList && !hasList.isEmpty()) {
			return;
		}
		String userId = userData.getId();
		if (null != userData) {
			GroupChatContent content = createGroupChatContent(messageId, groupId, userData, timestamp);
			groupChatDAO.save(content);
			List<GroupChatItem> list = new ArrayList<GroupChatItem>();
			for (ChatItem item : chatItemList) {
				GroupChatItem chatLog = new GroupChatItem();
				chatLog.setMessageId(messageId);
				chatLog.setGroupChatContentId(content.getId());
				chatLog.setGroupId(content.getGroupId());
				chatLog.setUserId(content.getUserId());
				chatLog.setRank(item.getRank());
				chatLog.setSection(item.getSection());
				chatLog.setType(item.getType());
				chatLog.setValue(item.getValue());
				chatLog.setFilterValue(item.getFilterValue());
				list.add(chatLog);
			}
			groupChatDAO.saveList(list);
		}

		UserLastChat userLastChat = createUserLastChat(messageId, messageId, userId, groupId, UserLastChat.TYPE_GROUP);
		saveOrUpdateLast(userLastChat);
	}

	private GroupChatContent createGroupChatContent(String messageId, String groupId, UserData userData, long timestamp) {
		GroupChatContent chatLog = new GroupChatContent();
		chatLog.setMessageId(messageId);
		chatLog.setGroupId(groupId);
		chatLog.setGroupName("");
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

	private void saveOrUpdateLast(UserLastChat userLastChat) {
		saveOrUpdate(userLastChat);
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
