package com.oim.core.business.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.util.MapUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UnreadBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.sender.UserChatSender;
import com.oim.core.business.view.ChatListView;
import com.oim.core.business.view.UserChatHistoryView;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;
import com.onlyxiahui.im.message.data.query.ChatQuery;

public class UserChatManager extends AbstractManager {

	private Map<String, Boolean> userChatLoadMap = new HashMap<String, Boolean>();
	private Map<String, Long> chatTimeMap = new ConcurrentHashMap<String, Long>();
	private Map<String, List<String>> messageIdMap = new HashMap<String, List<String>>();

	public UserChatManager(AppContext appContext) {
		super(appContext);
	}

	public void putUserMessageId(String userId, String messageId) {
		List<String> ids = messageIdMap.get(userId);
		if (null == ids) {
			ids = new ArrayList<String>();
			messageIdMap.put(userId, ids);
		}
		ids.add(0, messageId);
		int size = ids.size();
		if (size > 500) {
			ids.remove(size - 1);
		}
	}

	public int getUserMessageCount(String userId) {
		int size = 0;
		List<String> ids = messageIdMap.get(userId);
		if (null != ids) {
			size = ids.size();
		}
		return size;
	}

	/**
	 * 加一次未读消息
	 * 
	 * @author: XiaHui
	 * @param ownerUserId
	 * @param userId
	 * @createDate: 2017-12-28 20:57:14
	 * @update: XiaHui
	 * @updateDate: 2017-12-28 20:57:14
	 */
	public void plusUnread(String userId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		ub.plusUserUnread(userId);
	}

	public void setUnreadCount(String userId, int count) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		ub.setUserUnreadCount(userId, count);
	}

	public int getUnreadCount(String userId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		return ub.getUserUnreadCount(userId);
	}

	public int getTotalUnreadCount(String ownerUserId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		int count = ub.getTotalUnreadCount();
		return count;
	}

	public boolean isUserChatShowing(String userId) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		return chatListView.isUserChatShowing(userId);
	}

	public void showUserChat(UserData userData) {
		//String userId = userData.getId();
		//readUserChat(userId);
		showUserChatView(userData);
		loadUserChat(userData);
	}

	
	public void showUserChatView(UserData userData) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.show(userData);
		chatListView.setVisible(true);
	}
	
	public void loadUserChat(UserData userData) {
		String userId = userData.getId();
		if (!userChatLoadMap.containsKey(userId)) {
			userChatLoadMap.put(userId, true);
			int messageCount = getUserMessageCount(userId);
			if (messageCount < 100) {
				loadChatHistory(userData);
			}
			UserChatSender cs = appContext.getSender(UserChatSender.class);
			cs.updateUserOfflineMessageToRead(userId);
		}
	}

	public void readUserChat(String userId) {
		this.setUnreadCount(userId, 0);
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.setChatUserItemRed(userId, false, 0);// TODO
	}
	
	public void setChatUserItemRed(String userId, boolean red, int count) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.setChatUserItemRed(userId, red, count);// TODO
	}
	
	public String getTimeText(String userId, long timestamp) {
		String timeText = "";

		String key = "user_chat_" + userId;

		long nowTime = System.currentTimeMillis();
		long time = MapUtil.getOrDefault(chatTimeMap, key, 0L);

		long interval = nowTime - time;
		long duration = nowTime - timestamp;

		Date date = new Date(timestamp);

		if (interval > (1000 * 60 * 2)) {
			if (duration > (1000 * 60 * 60 * 24)) {// 一天以上显示年月日
				timeText = OnlyDateUtil.dateToDateTime(date);
			} else {
				timeText = OnlyDateUtil.dateToTime(date);
			}
		}
		chatTimeMap.put(userId, System.currentTimeMillis());
		return timeText;
	}

	public void updateUserChatItemInfo(String userId, String text, String time) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.updateUserChatInfo(userId, text, time);
	}

	private void loadChatHistory(final UserData userData) {
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Back
			public void back(Info info,
					@Define("sendUserId") String sendUserId,
					@Define("receiveUserId") String receiveUserId,
					@Define("contents") List<UserChatHistoryData> contents,
					@Define("page") PageData page) {
				if (info.isSuccess()) {
					if (null != contents && !contents.isEmpty()) {
						List<String> ids = messageIdMap.get(userData.getId());
						Map<String, String> idMap = new HashMap<String, String>();
						if (null != ids) {
							for (String id : ids) {
								idMap.put(id, id);
							}
						}

						ChatListView view = appContext.getSingleView(ChatListView.class);
						List<UserChatHistoryData> list = new ArrayList<UserChatHistoryData>();
						int size = contents.size();

						for (int i = size - 1; i >= 0; i--) {

							UserChatHistoryData content = contents.get(i);
							String messageKey = content.getMessageKey();
							if (!idMap.containsKey(messageKey)) {
								list.add(content);
							}
						}
						view.loadUserChatHistory(userData, page, list);
					}
				}
			}
		};
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		String ownerId = pb.getOwnerUserId();
		PageData page = new PageData();
		page.setPageSize(50);
		page.setPageNumber(1);

		UserChatSender cs = appContext.getSender(UserChatSender.class);
		String sendUserId = (ownerId == null || "".equals(ownerId)) ? "00000" : ownerId;
		String receiveUserId = userData.getId();
		cs.queryUserChatLog(sendUserId, receiveUserId, new ChatQuery(), page, dataBackAction);
	}

	public void showUserHistory(String userId) {
		UserChatHistoryView uchw = appContext.getSingleView(UserChatHistoryView.class);
		uchw.setUserId(userId);
		uchw.setVisible(true);
	}

	public void userChat(boolean isOwn, UserData showUserData, UserData chatUserData, Content content) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		chatListView.userChat(isOwn, showUserData, chatUserData, content);
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
