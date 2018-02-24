package com.oim.core.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.common.chat.util.ChatUtil;
import com.oim.common.chat.util.CoreContentUtil;
import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.GroupLastManager;
import com.oim.core.business.manager.UserLastManager;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.LastChat;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;

public class LastListService extends AbstractService {

	public LastListService(AppContext appContext) {
		super(appContext);
	}

	public void setLastChatList(String userId, List<LastChat> lastChatList) {

		if (null != lastChatList) {
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			GroupBox gb = appContext.getBox(GroupBox.class);

			UserLastManager ulm = this.appContext.getManager(UserLastManager.class);
			GroupLastManager glm = this.appContext.getManager(GroupLastManager.class);

			for (LastChat lc : lastChatList) {
				String chatId = lc.getChatId();
				String type = lc.getType();
				boolean isUser = LastChat.TYPE_USER.equals(type);
				boolean isGroup = LastChat.TYPE_GROUP.equals(type);
				if (isUser) {
					UserData userData = ub.getUserData(chatId);
					if (null != userData) {
						ulm.addOrUpdateLastUser(userData);
					}
				}
				if (isGroup) {
					Group group = gb.getGroup(chatId);
					if (null != group) {
						glm.addOrUpdateLastGroup(group);
					}
				}
			}
		}
	}

	public void setLastChatWithContentList(
			String userId,
			List<LastChat> lastChatList,
			List<UserChatHistoryData> userLastList,
			List<GroupChatHistoryData> groupLastList) {

		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		GroupBox gb = appContext.getBox(GroupBox.class);

		UserLastManager ulm = this.appContext.getManager(UserLastManager.class);
		GroupLastManager glm = this.appContext.getManager(GroupLastManager.class);

		if (null != lastChatList) {

			for (LastChat lc : lastChatList) {
				String chatId = lc.getChatId();
				String type = lc.getType();
				boolean isUser = LastChat.TYPE_USER.equals(type);
				boolean isGroup = LastChat.TYPE_GROUP.equals(type);
				if (isUser) {
					UserData userData = ub.getUserData(chatId);
					if (null != userData) {
						ulm.addOrUpdateLastUser(userData);
					}
				}
				if (isGroup) {
					Group group = gb.getGroup(chatId);
					if (null != group) {
						glm.addOrUpdateLastGroup(group);
					}
				}
			}
		}

		UserData u = pb.getUserData();
		String ownerUserId = u.getId();

		if (null != userLastList) {

			for (UserChatHistoryData uc : userLastList) {
				Content content = uc.getContent();
				UserData sd = uc.getSendUserData();
				UserData rd = uc.getReceiveUserData();

				UserData showUserData = (ownerUserId.equals(sd.getId())) ? rd : sd;
				String id = showUserData.getId();
				UserData userData = ub.getUserData(id);
				if (null != userData) {
					showUserData = userData;
				}
				long timestamp = content.getTimestamp();
				String time = ChatUtil.getTimeText(timestamp);
				String text = CoreContentUtil.getText(content);
				ulm.updateLastUserChatInfo(id, text, time);
			}
		}

		if (null != groupLastList) {
			for (GroupChatHistoryData gc : groupLastList) {
				Content content = gc.getContent();
				String groupId = gc.getGroupId();
				long timestamp = content.getTimestamp();
				String time = ChatUtil.getTimeText(timestamp);
				String text = CoreContentUtil.getText(content);
				glm.updateLastGroupChatInfo(groupId, text, time);
			}
		}
	}

	public void getLastChatWithDataList(
			String userId,
			List<LastChat> lastChatList,
			List<UserData> userDataList,
			List<Group> groupList) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		GroupBox gb = appContext.getBox(GroupBox.class);
		
		UserLastManager ulm = this.appContext.getManager(UserLastManager.class);
		GroupLastManager glm = this.appContext.getManager(GroupLastManager.class);

		if (null != lastChatList) {

			Map<String, UserData> userMap = new HashMap<String, UserData>();
			Map<String, Group> groupMap = new HashMap<String, Group>();
			if (null != lastChatList) {
				for (UserData d : userDataList) {
					String id=d.getId();
					userMap.put(d.getId(), d);
					if(ub.getUserData(id)==null){
						ub.putUserData(d);
					}
				}
			}
			if (null != lastChatList) {
				for (Group d : groupList) {
					String id=d.getId();
					groupMap.put(d.getId(), d);
					if(gb.getGroup(id)==null){
						gb.putGroup(d);
					}
				}
			}

			for (LastChat lc : lastChatList) {
				String chatId = lc.getChatId();
				String type = lc.getType();
				boolean isUser = LastChat.TYPE_USER.equals(type);
				boolean isGroup = LastChat.TYPE_GROUP.equals(type);
				if (isUser) {
					UserData userData = userMap.get(chatId);
					if (null != userData) {
						ulm.addOrUpdateLastUser(userData);
					}
				}
				if (isGroup) {
					Group group = groupMap.get(chatId);
					if (null != group) {
						glm.addOrUpdateLastGroup(group);
					}
				}
			}
		}
	}
}
