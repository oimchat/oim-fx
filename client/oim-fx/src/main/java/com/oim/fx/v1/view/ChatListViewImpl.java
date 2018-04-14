package com.oim.fx.v1.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.constant.RemoteConstant;
import com.oim.core.business.manager.GroupChatManager;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.RemoteManager;
import com.oim.core.business.manager.UserChatManager;
import com.oim.core.business.view.ChatListView;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.fx.view.context.ChatListItemContext;
import com.oim.fx.view.context.ChatListPaneContext;
import com.oim.fx.view.function.GroupUserHeadFunction;
import com.oim.ui.fx.classics.ChatListStage;
import com.oim.ui.fx.classics.RequestFrame;
import com.oim.ui.fx.classics.chat.ChatItem;
import com.oim.ui.fx.classics.chat.ChatPane;
import com.oim.ui.fx.classics.chat.SimpleListItem;
import com.oim.ui.fx.classics.list.ListRootPane;
import com.only.common.util.OnlyDateUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 22:31:05
 */
public class ChatListViewImpl extends AbstractView implements ChatListView {

	protected RequestFrame requestRemoteFrame = new RequestFrame();

	ChatListStage chatListStage = new ChatListStage();

	private long shakeTime = 0;// 记录收到或者发送抖动信息的时间，为了不过于频繁抖动。

	protected Map<String, ChatPane> chatPanelMap = new ConcurrentHashMap<String, ChatPane>();

	/**  **/
	protected Map<String, ListRootPane> groupUserListMap = new ConcurrentHashMap<String, ListRootPane>();
	/**  **/
	protected Map<String, Map<String, SimpleListItem>> groupUserHeadMap = new ConcurrentHashMap<String, Map<String, SimpleListItem>>();

	public ChatListViewImpl(AppContext appContext) {
		super(appContext);
		initialize();
	}

	private void initialize() {
		chatListStage.getRootPane().getStylesheets().add(this.getClass().getResource("/classics/css/chat.css").toString());
		requestRemoteFrame.setTitle("请求远程控制");
	}

	protected String createKey(String type, String key) {
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append("_");
		sb.append(key);
		return sb.toString();
	}

	@Override
	public boolean isShowing() {
		return chatListStage.isShowing();
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					if(chatListStage.isIconified()){
						chatListStage.setIconified(false);
						chatListStage.toFront();
					}else{
						chatListStage.show();
						chatListStage.toFront();
					}
				} else {
					chatListStage.hide();
				}
			}
		});
	}
	@Override
	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatListStage.showPrompt(text);
			}
		});
	}
	@Override
	public void show(UserData userData) {
		this.setVisible(true);
		String userId = userData.getId();
		String key = this.createKey("user", userId);

		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		ChatListPaneContext paneContext = appContext.getObject(ChatListPaneContext.class);

		ChatItem chatItem = itemContext.getUserChatItem(userData);
		ChatPane cp = paneContext.getUserChatPane(userData);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatListStage.addItem(key, chatItem, cp);
				chatListStage.selected(key);
			}
		});
	}

	@Override
	public void updateUserChatInfo(String userId, String text, String time) {
		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		itemContext.updateUserChatInfo(userId, text, time);
	}

	@Override
	public void setChatUserItemRed(String userId, boolean red, int count) {
		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		itemContext.setChatUserItemRed(userId, red, count);
	}

	@Override
	public boolean hasUserChat(String userId) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean isUserChatShowing(String userId) {
		boolean showing = this.isShowing();
		String key = this.createKey("user", userId);
		boolean chat = chatListStage.isSelected(key);
		return showing && chat;
	}

	@Override
	public void userChat(boolean isOwn, UserData showUserData, UserData chatUserData, Content content) {
		ChatListPaneContext clpc = appContext.getObject(ChatListPaneContext.class);

		String chatUserId = chatUserData.getId();
		String showUserId = showUserData.getId();

		UserChatManager ucm = appContext.getManager(UserChatManager.class);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		String head = him.getUserHeadPath(chatUserId);
		String name = isOwn ? "我" : AppCommonUtil.getDefaultShowName(chatUserData);
		String time = ucm.getTimeText(showUserId, content.getTimestamp());

		String orientation = isOwn ? "right" : "left";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatPane cp = clpc.getUserChatPane(showUserData);
				String headPath = WebImagePathUtil.pathToFileImageSource(head);
				clpc.insertShow(cp, name, headPath, time, orientation, content);
			}
		});
	}

	@Override
	public void showUserChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void loadUserChatHistory(UserData userData, PageData page, List<UserChatHistoryData> list) {
		if (null != list) {
			ChatListPaneContext clpc = appContext.getObject(ChatListPaneContext.class);
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData user = pb.getUserData();
			HeadImageManager him = appContext.getManager(HeadImageManager.class);

			for (UserChatHistoryData chd : list) {
				UserData sendUserData = chd.getSendUserData();
				String chatUserId = sendUserData.getId();
				Content content = chd.getContent();
				String time = OnlyDateUtil.dateToDateTime(new Date(content.getTimestamp()));

				boolean isOwn = user.getId().equals(chatUserId);

				String name = isOwn ? "我" : AppCommonUtil.getDefaultShowName(sendUserData);

				String head = him.getUserHeadPath(chatUserId);
				String orientation = isOwn ? "right" : "left";
				String headPath = WebImagePathUtil.pathToFileImageSource(head);
				
				ChatPane cp = clpc.getUserChatPane(userData);
				clpc.insertBeforeShow(cp, name, headPath, time, orientation, content);
			}
		}
	}

	@Override
	public void show(Group group) {
		String groupId = group.getId();
		String key = this.createKey("group", groupId);

		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		ChatListPaneContext paneContext = appContext.getObject(ChatListPaneContext.class);

		ChatItem chatItem = itemContext.getGroupChatItem(group);
		ChatPane cp = paneContext.getGroupChatPane(group);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				chatListStage.addItem(key, chatItem, cp);
				chatListStage.selected(key);
				
				ListRootPane listPane=getGroupUserListRootPane(groupId);
				cp.setRight(listPane);
			}
		});
	}

	@Override
	public boolean hasGroupChat(String groupId) {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void updateGroupChatInfo(String groupId, String text, String time) {
		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		itemContext.updateGroupChatInfo(groupId, text, time);
	}

	@Override
	public void setChatGroupItemRed(String groupId, boolean red, int count) {
		ChatListItemContext itemContext = appContext.getObject(ChatListItemContext.class);
		itemContext.setChatGroupItemRed(groupId, red, count);
	}

	@Override
	public boolean isGroupChatShowing(String groupId) {
		boolean showing = this.isShowing();
		String key = this.createKey("group", groupId);
		boolean chat = chatListStage.isSelected(key);
		return showing && chat;
	}

	@Override
	public void groupChat(boolean isOwn, Group group, UserData chatUserData, Content content) {

		ChatListPaneContext clpc = appContext.getObject(ChatListPaneContext.class);

		String groupId = group.getId();
		String chatUserId = chatUserData.getId();

		GroupChatManager cm = appContext.getManager(GroupChatManager.class);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		String head = him.getUserHeadPath(chatUserId);
		String name = isOwn ? "我" : AppCommonUtil.getDefaultShowName(chatUserData);
		String time = cm.getTimeText(groupId, content.getTimestamp());

		String orientation = isOwn ? "right" : "left";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatPane cp = clpc.getGroupChatPane(group);
				String headPath = WebImagePathUtil.pathToFileImageSource(head);
				clpc.insertShow(cp, name, headPath, time, orientation, content);
			}
		});
	}

	@Override
	public void showGroupChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void loadGroupChatHistory(Group group, PageData page, List<GroupChatHistoryData> list) {
		if (null != list) {

			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData user = pb.getUserData();
			HeadImageManager him = appContext.getManager(HeadImageManager.class);

			ChatListPaneContext clpc = appContext.getObject(ChatListPaneContext.class);

			for (GroupChatHistoryData chd : list) {
				UserData chatUserData = chd.getUserData();
				String chatUserId = chatUserData.getId();
				Content content = chd.getContent();
				String time = OnlyDateUtil.dateToDateTime(new Date(content.getTimestamp()));

				boolean isOwn = user.getId().equals(chatUserId);

				String head = him.getUserHeadPath(chatUserId);
				String name = isOwn ? "我" : AppCommonUtil.getDefaultShowName(chatUserData);
				String orientation = isOwn ? "right" : "left";
				
				String headPath = WebImagePathUtil.pathToFileImageSource(head);
				
				ChatPane cp = clpc.getGroupChatPane(group);
				
				clpc.insertBeforeShow(cp, name, headPath, time, orientation, content);
			}
		}
	}

	@Override
	public void doShake(UserData userData) {
		show(userData);
		shake();
	}

	@Override
	public boolean hasRequestRemoteControl(String userId) {
		return requestRemoteFrame.isShowing();
	}

	@Override
	public void showRequestRemoteControl(UserData userData) {
		String userId = userData.getId();
		String name = AppCommonUtil.getDefaultShowName(userData);
		StringBuilder content = new StringBuilder();
		content.append(name);
		content.append(" 请求远程控制你的电脑，接受还是拒绝？");
		requestRemoteFrame.setContent(content.toString());

		RemoteManager rm = appContext.getManager(RemoteManager.class);
		requestRemoteFrame.setOnAcceptAction(a -> {
			requestRemoteFrame.hide();
			rm.responseRemoteControl(userId, RemoteConstant.action_type_agree);
		});
		requestRemoteFrame.setOnRefuseAction(a -> {
			requestRemoteFrame.hide();
			rm.responseRemoteControl(userId, RemoteConstant.action_type_shut);
		});
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				requestRemoteFrame.show();
			}
		});

	}

	@Override
	public void addOrUpdateGroupMember(String groupId, GroupMember groupMember, UserData userData) {
		ListRootPane listPane = getGroupUserListRootPane(groupId);
		SimpleListItem head = getGroupUserHeadItem(groupId, groupMember, userData);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				listPane.addNode(head);
			}
		});
	}

	@Override
	public void removeGroupMember(String groupId, String userId) {
		ListRootPane listPane = groupUserListMap.get(groupId);
		Map<String, SimpleListItem> map = groupUserHeadMap.get(groupId);
		SimpleListItem head = null == map ? null : map.get(userId);

		if (null != listPane && null != head) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					listPane.removeNode(head);
				}
			});
		}
	}

	private void shake() {
		if (System.currentTimeMillis() - shakeTime < 3000) {
			return;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				Task<Integer> task = new Task<Integer>() {
					@Override
					protected Integer call() throws Exception {
						int iterations = 0;
						for (int i = 0; i < 3; i++) {
							chatListStage.setX(chatListStage.getX() + 4);
							chatListStage.setY(chatListStage.getY() - 4);
							try {
								Thread.sleep(40);
								chatListStage.setX(chatListStage.getX() - 8);
								chatListStage.setY(chatListStage.getY());
								Thread.sleep(40);
								chatListStage.setX(chatListStage.getX());
								chatListStage.setY(chatListStage.getY() + 4);
								Thread.sleep(40);
								chatListStage.setX(chatListStage.getX() + 4);
								chatListStage.setY(chatListStage.getY());
								Thread.sleep(40);
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}
						return iterations;
					}
				};

				Thread th = new Thread(task);
				th.setDaemon(true);
				th.start();
			}
		});
		shakeTime = System.currentTimeMillis();
	}

	ListRootPane getGroupUserListRootPane(String groupId) {
		ListRootPane listPane = groupUserListMap.get(groupId);
		if (null == listPane) {
			listPane = new ListRootPane();
			groupUserListMap.put(groupId, listPane);

			listPane.setPrefWidth(165);
			listPane.setStyle("-fx-background-color:rgba(235, 235, 235, 0.95)");
			//listPane.setStyle("-fx-background-color:#e9e7e6;");
		}
		return listPane;
	}

	SimpleListItem getGroupUserHeadItem(String groupId, GroupMember groupMember, UserData userData) {
		GroupUserHeadFunction guhf = appContext.getObject(GroupUserHeadFunction.class);
		Map<String, SimpleListItem> map = groupUserHeadMap.get(groupId);
		if (null == map) {
			map = new HashMap<String, SimpleListItem>();
			groupUserHeadMap.put(groupId, map);
		}
		String userId = userData.getId();
		SimpleListItem head = map.get(userId);

		if (null == head) {
			head = new SimpleListItem();
			map.put(userId, head);
			guhf.setUserHeadEvent(head);
		}
		guhf.setUserDataHead(head, userData, groupMember);
		return head;
	}
}
