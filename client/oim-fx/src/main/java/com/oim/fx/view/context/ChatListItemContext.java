package com.oim.fx.view.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.chat.util.ChatUtil;
import com.oim.fx.view.function.GroupChatItemFunction;
import com.oim.fx.view.function.UserChatItemFunction;
import com.oim.ui.fx.classics.chat.ChatItem;
import com.only.fx.common.component.choose.ChooseGroup;
import com.only.fx.common.tools.ComponentSyncBuild;
import com.only.fx.common.tools.SyncBuild;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Platform;

/**
 * @author: XiaHui
 * @date: 2017-12-25 09:53:20
 */
public class ChatListItemContext extends AbstractComponent {

	protected Map<String, ChatItem> userChatItemMap = new ConcurrentHashMap<String, ChatItem>();
	protected Map<String, ChatItem> groupChatItemMap = new ConcurrentHashMap<String, ChatItem>();
	protected ComponentSyncBuild sb = new ComponentSyncBuild();
	private final ChooseGroup chatHeadchooseGroup = new ChooseGroup();
	private String selectedKey;
	
	public ChatListItemContext(AppContext appContext) {
		super(appContext);
	}

	
	public String getSelectedKey() {
		return selectedKey;
	}

	public void setSelectedKey(String selectedKey) {
		this.selectedKey = selectedKey;
	}
	
	public ChatItem getUserChatItem(String userId) {
		ChatItem item = userChatItemMap.get(userId);
		return item;
	}

	public ChatItem getUserChatItem(UserData userData) {
		UserChatItemFunction ucif = appContext.getObject(UserChatItemFunction.class);
		String userId = userData.getId();
		ChatItem item = userChatItemMap.get(userId);
		if (null == item) {
			item = sb.getComponent(userId, new SyncBuild<ChatItem>() {

				@Override
				public ChatItem build() {
					ChatItem item = new ChatItem();
					item.setChooseGroup(chatHeadchooseGroup);
					ucif.setChatUserDataHeadEvent(item);
					return item;
				}
			});
			userChatItemMap.put(userId, item);
		}
		ucif.setChatUserDataHead(item, userData);
		return item;
	}

	public void updateUserChatInfo(String userId, String text, String time) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem head = userChatItemMap.get(userId);
				if (null != head) {
					head.setText(text);
					head.setTime(time);
					if (null == text) {
						head.setText("");
					} else {
						head.setText(text);
					}
					head.setTime(time);
				}
			}
		});
	}

	public void setChatUserItemRed(String userId, boolean red, int count) {
		ChatItem head = userChatItemMap.get(userId);
		if (null != head) {
			redHead(head, red, count);
		}
	}

	/****************************************************/

	public ChatItem getGroupChatItem(String groupId) {
		ChatItem item = groupChatItemMap.get(groupId);
		return item;
	}

	public ChatItem getGroupChatItem(Group group) {
		GroupChatItemFunction gcif = appContext.getObject(GroupChatItemFunction.class);
		String groupId = group.getId();
		ChatItem item = groupChatItemMap.get(groupId);
		if (null == item) {
			item = sb.getComponent(groupId, new SyncBuild<ChatItem>() {

				@Override
				public ChatItem build() {
					ChatItem item = new ChatItem();
					item.setChooseGroup(chatHeadchooseGroup);
					gcif.setChatGroupHeadEvent(item);
					return item;
				}
			});
			groupChatItemMap.put(groupId, item);
		}
		gcif.setChatGroupHead(item, group);
		return item;
	}

	public void updateGroupChatInfo(String groupId, String text, String time) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem head = userChatItemMap.get(groupId);
				if (null != head) {
					head.setText(text);
					head.setTime(time);
					if (null == text) {
						head.setText("");
					} else {
						head.setText(text);
					}
					head.setTime(time);
				}
			}
		});
	}

	public void setChatGroupItemRed(String groupId, boolean red, int count) {
		ChatItem head = groupChatItemMap.get(groupId);
		if (null != head) {
			redHead(head, red, count);
		}
	}

	public void redHead(ChatItem head, boolean red, int count) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				head.setRed(red);
				head.setRedText(ChatUtil.getCountText(red, count));
			}
		});
	}
}
