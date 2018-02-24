package com.oim.core.business.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.sender.ChatSender;
import com.oim.core.business.view.ChatListView;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;
import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * 对聊天相关的一些管理，如不同用户聊天界面
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午1:37:57
 */
public class ChatManage extends AbstractManager {

	Map<String, Boolean> userChatLoadMap = new HashMap<String, Boolean>();
	Map<String, List<String>> messageIdMap = new HashMap<String, List<String>>();

	Map<String, List<Content>> userChatMap = new HashMap<String, List<Content>>();
	Map<String, List<GroupChatData>> groupChatMap = new HashMap<String, List<GroupChatData>>();

	public ChatManage(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	public void putUserMessageId(String userId, String messageId) {
		List<String> ids = messageIdMap.get(userId);
		if (null == ids) {
			ids = new ArrayList<String>();
			messageIdMap.put(userId, ids);
		}
		ids.add(0, messageId);
		int size = ids.size();
		if (size > 50) {
			ids.remove(size - 1);
		}
	}

	public void putUserCaht(String userId, Content content) {
		List<Content> list = userChatMap.get(userId);
		if (list == null) {
			list = new ArrayList<Content>();
			userChatMap.put(userId, list);
		}
		list.add(content);
	}

	public void putGroupCaht(String groupId, UserData userData, Content content) {
		List<GroupChatData> list = groupChatMap.get(groupId);
		if (list == null) {
			list = new ArrayList<GroupChatData>();
			groupChatMap.put(groupId, list);
		}
		list.add(new GroupChatData(userData, content));
	}

	public boolean isGroupChatShowing(String groupId) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		return chatListView.isGroupChatShowing(groupId);
	}

	public boolean isUserChatShowing(String userId) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		return chatListView.isUserChatShowing(userId);
	}

	public void userChat(boolean isOwn, UserData userData, Content content) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.userChat(isOwn, userData, content);
	}

	public void groupChat(Group group, UserData userData, Content content) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.groupChat(group, userData, content);
	}

	public void showCahtFrame(UserData userData) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.show(userData);
		chatListView.setVisible(true);

		PromptManage pm = this.appContext.getManager(PromptManage.class);
		pm.showUserHeadPulse(userData.getId(), false);// 停止头像跳动
		pm.remove(userData.getId());// 系统托盘停止跳动

	}

	public void showCahtFrame(Group group) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.show(group);
		chatListView.setVisible(true);

		PromptManage pm = this.appContext.getManager(PromptManage.class);
		pm.showGroupHeadPulse(group.getId(), false);// 停止头像跳动
		pm.remove(group.getId());// 系统托盘停止跳动
	}

	public void showUserCaht(UserData userData) {
		String userId = userData.getId();
		List<Content> list = userChatMap.remove(userData.getId());
		ChatManage chatManage = this.appContext.getManager(ChatManage.class);
		if (null != userData && list != null && !list.isEmpty()) {
			for (Content content : list) {
				chatManage.userChat(false, userData, content);
			}
		}
		if (!userChatLoadMap.containsKey(userId)) {

			List<String> ids = messageIdMap.get(userId);
			if (null == ids || ids.size() < 50) {
				showChatHistory(userData);
			}
			userChatLoadMap.put(userId, true);

			ChatSender cs = appContext.getSender(ChatSender.class);
			cs.updateUserOfflineMessageToRead(userData.getId());
		}
	}

	public void showChatHistory(final UserData userData) {
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
			}

			@Override
			public void timeOut() {
			}

			@Back
			public void back(Info info, @Define("sendUserId") String sendUserId, @Define("receiveUserId") String receiveUserId, @Define("contents") List<UserChatHistoryData> contents, @Define("page") PageData page) {
				if (info.isSuccess()) {
					if (null != contents && !contents.isEmpty()) {
						ChatListView view = appContext.getSingleView(ChatListView.class);

						List<String> ids = messageIdMap.get(userData.getId());
						Map<String, String> idMap = new HashMap<String, String>();
						if (null != ids) {
							for (String id : ids) {
								idMap.put(id, id);
							}
						}
						List<UserChatHistoryData> list = new ArrayList<UserChatHistoryData>();
						for (UserChatHistoryData content : contents) {
							String messageKey = content.getMessageKey();
							if (!idMap.containsKey(messageKey)) {
								list.add(content);
							}
						}
						view.userChatHistory(userData, list);
					}
				}
			}
		};
		
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		PageData page = new PageData();
		page.setPageSize(50);
		page.setPageNumber(1);
		ChatSender ds = appContext.getSender(ChatSender.class);
		ds.queryUserChatLog(sendUser.getId(), userData.getId(), new ChatQuery(), page, dataBackAction);
		// ChatManage chatManage = appContext.getManage(ChatManage.class);
		// chatManage.showCahtFrame(userData);
	}

	public void showGroupCaht(Group group) {
		List<GroupChatData> list = groupChatMap.remove(group.getId());
		ChatManage chatManage = this.appContext.getManager(ChatManage.class);
		if (null != group && list != null && !list.isEmpty()) {
			for (GroupChatData ucd : list) {
				chatManage.groupChat(group, ucd.userData, ucd.content);
			}
		}
	}

	public void showShake(String sendUserId) {

	}

	class GroupChatData {

		private UserData userData;
		private Content content;

		public GroupChatData(UserData userData, Content content) {
			this.userData = userData;
			this.content = content;
		}

		public UserData getUserData() {
			return userData;
		}

		public void setUserData(UserData userData) {
			this.userData = userData;
		}

		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			this.content = content;
		}
	}

	public void updateGroupUserList(String groupId) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.updateGroupUserList(groupId);
	}

	public void doShake(String sendUserId) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(sendUserId);
		if (null == userData) {// 如果发送抖动的不是好友，暂时不支持抖动
			return;
		}
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.doShake(userData);
	}
}
