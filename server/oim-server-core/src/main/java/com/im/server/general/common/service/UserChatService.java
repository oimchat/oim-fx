package com.im.server.general.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.base.common.task.ExecuteTask;
import com.im.base.common.task.QueueTaskThread;
import com.im.server.general.business.push.UserChatPush;
import com.im.server.general.business.thread.SessionServerHandler;
import com.im.server.general.common.dao.UserChatDAO;
import com.im.server.general.common.data.ChatItem;
import com.im.server.general.common.manager.ChatManager;
import com.im.server.general.common.manager.LastChatManager;
import com.im.server.general.common.manager.UserChatManager;
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
public class UserChatService {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	UserBaseService userBaseService;
	@Resource
	RoomBaseService roomBaseService;
	@Resource
	ChatManager chatManager;
	@Resource
	UserChatDAO userChatDAO;
	@Resource
	UserChatPush userChatPush;
	@Resource
	SessionServerHandler sessionServerHandler;
	@Resource
	LastChatManager lastChatManager;
	@Resource
	QueueTaskThread queueTaskThread;
	@Resource
	UserChatManager userChatManager;

	/**
	 * 用户对用户聊天
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月19日 下午3:26:32
	 * @update: XiaHui
	 * @updateDate: 2016年8月19日 下午3:26:32
	 */
	public void userChat(String key, String sendUserId, String receiveUserId, Content content) {
		List<ChatItem> chatItemList = chatManager.wordsFilter(content);
		long timestamp = content.getTimestamp();
		UserData sendUserData = userBaseService.getUserData(sendUserId);
		UserData receiveUserData = userBaseService.getUserData(receiveUserId);
		boolean isSend = sessionServerHandler.hasSession(receiveUserId);
		userChatPush.pushUserChat(key, sendUserId, receiveUserId, content);
		if (null != sendUserData && null != receiveUserData) {
			queueTaskThread.add(new ExecuteTask() {

				@Override
				public void execute() {
					userChatManager.addUserChatLog(isSend, sendUserData, receiveUserData, key, chatItemList, timestamp);
				}
			});
		}
	}
}
