package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.Group;
import com.im.server.general.common.bean.UserChatContent;
import com.im.server.general.common.bean.UserLastChat;
import com.im.server.general.common.dao.GroupDAO;
import com.im.server.general.common.dao.UserChatDAO;
import com.im.server.general.common.dao.UserDAO;
import com.im.server.general.common.dao.UserLastChatDAO;
import com.im.server.general.common.manager.ChatLogManager;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.ResultMessage;

@Service
public class LastChatService {
	@Resource
	UserLastChatDAO userLastChatDAO;
	@Resource
	UserChatDAO userChatDAO;
	@Resource
	ChatLogManager chatLogManager;

	@Resource
	UserDAO userDAO;
	@Resource
	GroupDAO groupDAO;

	/**
	 * 获取最后聊天的列表
	 * 
	 * @param userId
	 * @param count
	 * @return
	 */
	public ResultMessage getLastChatWithContentList(String userId, String type, int count) {
		if (count < 0 || 20 < count) {
			count = 20;
		}
		ResultMessage rm = new ResultMessage();

		List<UserLastChat> list = userLastChatDAO.getUserLastChatList(userId, count);

		List<String> idList = new ArrayList<String>();
		for (UserLastChat ulc : list) {
			idList.add(ulc.getContentId());
		}

		List<UserChatContent> userContentList = userChatDAO.getListByIdList(idList);
		List<Map<String, Object>> contents = chatLogManager.getUserChatLogListMap(userContentList, false);
		rm.put("userId", userId);
		rm.put("count", count);
		rm.put("lastChatList", list);
		rm.put("userLastList", contents);
		// rm.put("groupLastList", new ArrayList<Object>());
		// rm.put("roomLastList", new ArrayList<Object>());
		return rm;
	}

	public ResultMessage getLastChatWithDataList(String userId, String type, int count) {
		if (count < 0 || 20 < count) {
			count = 20;
		}
		ResultMessage rm = new ResultMessage();
		List<UserLastChat> list = userLastChatDAO.getUserLastChatList(userId, count);

		List<String> userIds = new ArrayList<String>();
		List<String> groupIds = new ArrayList<String>();

		for (UserLastChat l : list) {
			String chatId = l.getChatId();

			if (UserLastChat.TYPE_USER.equals(l.getType())) {
				userIds.add(chatId);
			}

			if (UserLastChat.TYPE_GROUP.equals(l.getType())) {
				groupIds.add(chatId);
			}
		}

		List<UserData> userDataList = userDAO.getUserDataList(userIds);
		List<Group> groupList = groupDAO.getGroupList(groupIds);

		rm.put("userId", userId);
		rm.put("count", count);
		rm.put("lastChatList", list);
		rm.put("userDataList", userDataList);
		rm.put("groupList", groupList);
		return rm;
	}

	public ResultMessage getLastChatList(String userId, String type, int count) {
		if (count < 0 || 20 < count) {
			count = 20;
		}
		ResultMessage rm = new ResultMessage();
		List<UserLastChat> list = userLastChatDAO.getUserLastChatList(userId, count);
		rm.put("userId", userId);
		rm.put("count", count);
		rm.put("lastChatList", list);
		return rm;
	}

	public boolean remove(String userId, String chatId, String type) {
		boolean mark = userLastChatDAO.deleteBy(userId, chatId, type);
		return mark;
	}
}
