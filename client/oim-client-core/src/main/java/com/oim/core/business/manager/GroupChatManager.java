package com.oim.core.business.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.util.MapUtil;
import com.oim.core.business.box.UnreadBox;
import com.oim.core.business.sender.GroupChatSender;
import com.oim.core.business.view.ChatListView;
import com.oim.core.business.view.GroupChatHistoryView;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.query.ChatQuery;

public class GroupChatManager extends AbstractManager {

	private Map<String, Boolean> groupChatLoadMap = new ConcurrentHashMap<String, Boolean>();
	private Map<String, Long> chatTimeMap = new ConcurrentHashMap<String, Long>();
	private Map<String, List<String>> messageIdMap = new HashMap<String, List<String>>();

	public GroupChatManager(AppContext appContext) {
		super(appContext);
	}

	public void putGroupMessageId(String groupId, String messageId) {
		List<String> ids = messageIdMap.get(groupId);
		if (null == ids) {
			ids = new ArrayList<String>();
			messageIdMap.put(groupId, ids);
		}
		ids.add(0, messageId);
		int size = ids.size();
		if (size > 500) {
			ids.remove(size - 1);
		}
	}

	public int getGroupMessageCount(String groupId) {
		int size = 0;
		List<String> ids = messageIdMap.get(groupId);
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
	 * @param groupId
	 * @createDate: 2017-12-28 20:57:14
	 * @update: XiaHui
	 * @updateDate: 2017-12-28 20:57:14
	 */
	public void plusUnread(String groupId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		ub.plusGroupUnread(groupId);
	}

	public void setUnreadCount(String groupId, int count) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		ub.setGroupUnreadCount(groupId, count);
	}

	public int getUnreadCount(String groupId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		return ub.getGroupUnreadCount(groupId);
	}

	public int getTotalUnreadCount(String ownerUserId) {
		UnreadBox ub = appContext.getBox(UnreadBox.class);
		int count = ub.getTotalUnreadCount();
		return count;
	}

	public boolean isGroupChatShowing(String groupId) {
		ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		return chatListView.isGroupChatShowing(groupId);
	}

	public void showGroupChat(Group group) {
		//String groupId = group.getId();
		//readGroupChat(groupId);
		showGroupChatView(group);
		loadGroupChat(group);
	}

	public void showGroupChatView(Group group) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.show(group);
		chatListView.setVisible(true);
	}

	public void loadGroupChat(Group group) {
		String groupId = group.getId();
		if (!groupChatLoadMap.containsKey(groupId)) {
			groupChatLoadMap.put(groupId, true);
			int messageCount = getGroupMessageCount(groupId);
			if (messageCount < 100) {
				loadChatHistory(group);
			}
		}
	}

	public void readGroupChat(String groupId) {
		this.setUnreadCount(groupId, 0);
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.setChatGroupItemRed(groupId, false, 0);// TODO
	}
	
	public void setChatGroupItemRed(String groupId, boolean red, int count) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.setChatGroupItemRed(groupId, red, count);// TODO
	}

	public String getTimeText(String groupId, long timestamp) {
		String timeText = "";
		String key = "group_chat_" + groupId;

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
		chatTimeMap.put(groupId, System.currentTimeMillis());
		return timeText;
	}

	public void updateGroupChatItemInfo(String groupId, String text, String time) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.updateGroupChatInfo(groupId, text, time);
	}

	private void loadChatHistory(final Group group) {
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Back
			public void back(Info info,
					@Define("groupId") String groupId,
					@Define("contents") List<GroupChatHistoryData> contents,
					@Define("page") PageData page) {
				if (info.isSuccess()) {
					if (null != contents && !contents.isEmpty()) {
						List<String> ids = messageIdMap.get(groupId);
						Map<String, String> idMap = new HashMap<String, String>();
						if (null != ids) {
							for (String id : ids) {
								idMap.put(id, id);
							}
						}

						ChatListView view = appContext.getSingleView(ChatListView.class);
						List<GroupChatHistoryData> list = new ArrayList<GroupChatHistoryData>();
						int size = contents.size();

						for (int i = size - 1; i >= 0; i--) {
							GroupChatHistoryData content = contents.get(i);
							String messageKey = content.getMessageKey();
							if (!idMap.containsKey(messageKey)) {
								list.add(content);
							}
						}
						view.loadGroupChatHistory(group, page, list);
					}
				}
			}
		};

		String groupId = group.getId();
		PageData page = new PageData();
		page.setPageSize(50);
		page.setPageNumber(1);

		GroupChatSender cs = appContext.getSender(GroupChatSender.class);
		cs.queryGroupChatLog(groupId, new ChatQuery(), page, dataBackAction);
	}

	public void groupChat(boolean isOwn, Group group, UserData chatUserData, Content content) {
		ChatListView chatListView = this.appContext.getSingleView(ChatListView.class);
		chatListView.groupChat(isOwn, group, chatUserData, content);
	}

	public void showGroupHistory(String groupId) {
		GroupChatHistoryView chf = appContext.getSingleView(GroupChatHistoryView.class);
		chf.setGroupId(groupId);
		chf.setVisible(true);
	}
}
