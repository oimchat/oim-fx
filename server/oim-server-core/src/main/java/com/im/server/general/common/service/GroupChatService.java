package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.base.common.task.ExecuteTask;
import com.im.base.common.task.QueueTaskThread;
import com.im.server.general.business.push.GroupChatPush;
import com.im.server.general.common.bean.GroupMember;
import com.im.server.general.common.dao.GroupMemberDAO;
import com.im.server.general.common.data.ChatItem;
import com.im.server.general.common.manager.ChatManager;
import com.im.server.general.common.manager.GroupChatManager;
import com.im.server.general.common.service.api.UserBaseService;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月16日 上午9:38:51
 */
@Service
public class GroupChatService {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	GroupChatPush groupChatPush;
	@Resource
	private GroupMemberDAO groupMemberDAO;
	@Resource
	ChatManager chatManager;
	@Resource
	UserBaseService userBaseService;
	@Resource
	QueueTaskThread queueTaskThread;
	@Resource
	GroupChatManager groupChatManager;

	public void groupChat(String key, String userId, String groupId, Content content) {
		List<GroupMember> list = groupMemberDAO.getListByGroupId(groupId);
		List<ChatItem> chatItemList = chatManager.wordsFilter(content);
		List<String> ids = new ArrayList<String>();
		for (GroupMember gm : list) {
			ids.add(gm.getUserId());
		}
		groupChatPush.groupChat(key, groupId, userId, content, ids);

		UserData userData = userBaseService.getUserData(userId);
		if (null != userData) {
			queueTaskThread.add(new ExecuteTask() {

				@Override
				public void execute() {

					long timestamp = content.getTimestamp();
					groupChatManager.addGroupChatLog(userData, key, groupId, chatItemList, timestamp);
				}
			});
		}
	}
}
