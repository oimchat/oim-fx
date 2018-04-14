package com.oim.fx.view.context;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.common.chat.util.WebImagePathUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.controller.GroupChatController;
import com.oim.core.business.controller.GroupMemberController;
import com.oim.core.business.controller.UserChatController;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.service.ContentToHtmlService;
import com.oim.core.common.AppConstant;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.component.file.FileInfo;
import com.oim.fx.common.util.ContentUtil;
import com.oim.fx.view.function.ChatFunction;
import com.oim.ui.fx.classics.chat.ChatPane;
import com.oim.ui.fx.common.chat.ChatShowPane;
import com.oim.ui.fx.common.chat.ChatWritePane;
import com.only.fx.common.tools.ComponentSyncBuild;
import com.only.fx.common.tools.SyncBuild;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

/**
 * @author: XiaHui
 * @date: 2017-12-25 09:53:20
 */
public class ChatListPaneContext extends AbstractComponent {

	protected Map<String, ChatPane> chatPanelMap = new ConcurrentHashMap<String, ChatPane>();
	protected ComponentSyncBuild sb = new ComponentSyncBuild();
	protected ChatFunction chatFunction;

	public ChatListPaneContext(AppContext appContext) {
		super(appContext);
		chatFunction = appContext.getObject(ChatFunction.class);
	}

	/**
	 * 获取用户聊天面板
	 * 
	 * @author XiaHui
	 * @date 2017-12-30 21:52:42
	 * @param ownerUserId
	 * @param userData
	 * @return
	 */
	public ChatPane getUserChatPane(UserData userData) {
		String userId = userData.getId();
		String key = this.getKey("user", userId);
		ChatPane chatPane = chatPanelMap.get(key);
		if (chatPane == null) {
			chatPane = sb.getComponent(userId, new SyncBuild<ChatPane>() {

				@Override
				public ChatPane build() {
					ChatPane chatPane = new ChatPane();
					return chatPane;
				}
			});
			chatPanelMap.put(key, chatPane);

			chatPane.setOnSendAction(a -> {
				sendUserMessage(key);
			});

			chatPane.setOnWriteKeyReleased(e -> {// 回车发送
				if (!e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
					e.consume();
					sendUserMessage(key);
				}
			});
			initUserChatPaneTask(chatPane, userId);
		}
		chatPane.addAttribute("key", key);
		chatPane.addAttribute("userId", userId);
		chatPane.addAttribute(UserData.class, userData);

		String nickname = userData.getNickname();// 昵称
		String remarkName = userData.getRemarkName();
		String signature =userData.getSignature();
		String name = (null != remarkName && !"".equals(remarkName)) ? remarkName : nickname;
		chatPane.setText(signature);
		chatPane.setName(name);
		
		return chatPane;
	}

	/**
	 * 获取群聊面板
	 * 
	 * @author XiaHui
	 * @date 2017-12-30 21:52:59
	 * @param ownerUserId
	 * @param group
	 * @return
	 */
	public ChatPane getGroupChatPane(Group group) {
		String groupId = group.getId();
		String key = this.getKey("group", groupId);
		ChatPane chatPane = chatPanelMap.get(key);
		if (chatPane == null) {
			chatPane = sb.getComponent(groupId, new SyncBuild<ChatPane>() {

				@Override
				public ChatPane build() {
					ChatPane chatPane = new ChatPane();
					return chatPane;
				}
			});
			chatPanelMap.put(key, chatPane);

			chatPane.setOnSendAction(a -> {
				sendGroupMessage(key);
			});

			chatPane.setOnWriteKeyReleased(e -> {// 回车发送
				if (!e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
					e.consume();
					sendGroupMessage(key);
				}
			});
			initGroupChatPaneTask(chatPane);
			
			GroupMemberController gmc=appContext.getController(GroupMemberController.class);
			gmc.loadGroupMember(groupId);
		}
		chatPane.addAttribute("key", key);
		chatPane.addAttribute("groupId", groupId);

		chatPane.addAttribute(Group.class, group);

		String name = group.getName();// 昵称
		chatPane.setName(name);
		return chatPane;
	}

	private void initUserChatPaneTask(ChatPane chatPane, String userId) {
		appContext.add(new ExecuteTask() {

			@Override
			public void execute() {
				chatFunction.initChatPane(chatPane);
				chatFunction.initUserChatPane(chatPane);
				chatFunction.initTopUserChatPane(chatPane);
			}
		});
	}

	private void initGroupChatPaneTask(ChatPane chatPane) {
		appContext.add(new ExecuteTask() {

			@Override
			public void execute() {
				chatFunction.initChatPane(chatPane);
				chatFunction.initGroupChatPane(chatPane);
			}
		});
	}

	protected String getKey(String type, String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append("_");
		sb.append(id);
		return sb.toString();
	}

	public void insertBeforeShow(ChatPane chatPane, String name, String head, String time, String orientation, Content content) {
		if (null != chatPane) {
			ChatShowPane chatShowPane = chatPane.getChatShowPane();
			ContentToHtmlService cths = appContext.getService(ContentToHtmlService.class);
			String contentTag = cths.getBubbleInsertContentTag(content);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					chatShowPane.insertBeforeShowHtml(name, head, time, orientation, contentTag);
				}
			});
			loadImages(chatShowPane, content);
		}
	}

	public void insertShow(ChatPane chatPane, String name, String head, String time, String orientation, Content content) {
		if (null != chatPane) {
			ChatShowPane chatShowPane = chatPane.getChatShowPane();
			ContentToHtmlService cths = appContext.getService(ContentToHtmlService.class);
			String contentTag = cths.getBubbleInsertContentTag(content);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					chatShowPane.insertLastShowHtml(name, head, time, orientation, contentTag);
				}
			});
			loadImages(chatShowPane, content);
		}
	}

	public void loadImages(ChatShowPane chatShowPane, Content content) {
		List<Item> items = CoreContentUtil.getImageItemList(content);
		if (!items.isEmpty()) {

			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData sendUser = pb.getUserData();

			BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

				@Override
				public void back(BackInfo backInfo, List<FileHandleInfo> list) {
					for (FileHandleInfo info : list) {
						FileInfo fi = info.getFileInfo();
						if (info.isSuccess()) {
							File file = (null != fi) ? fi.getFile() : null;
							String name = (null != file) ? file.getName() : "";
							String id = (null != fi) ? fi.getId() : "";
							String appPath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + sendUser.getNumber() + "/image/" + name;

							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									String image = WebImagePathUtil.pathToFileImageSource(appPath);
									chatShowPane.replaceImage(id, image);
								}
							});
						} else {
							String id = (null != fi) ? fi.getId() : "";
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									String tempImage = "Resources/Images/Default/Chat/ImageLoading/image_download_fail.png";
									File file = new File(tempImage);
									if (file.exists()) {
										tempImage = file.getAbsolutePath();
									}
									tempImage = WebImagePathUtil.pathToFileImageSource(tempImage);
									chatShowPane.replaceImage(id, tempImage);
								}
							});
						}
					}
				}
			};
			ImageManager im = this.appContext.getManager(ImageManager.class);
			im.downloadImage(items, backAction);
		}
	}

	protected void sendUserMessage(String key) {
		ChatPane chatPane = chatPanelMap.get(key);
		if (null != chatPane) {
			ChatWritePane chatWritePane = chatPane.getChatWritePane();
			String html = chatWritePane.getHtml();
			boolean underline = chatWritePane.isUnderline();
			boolean bold = chatWritePane.isBold();
			String color = chatWritePane.getWebColor();
			boolean italic = chatWritePane.isItalic();
			String fontName = chatWritePane.getFontName();
			int fontSize = chatWritePane.getFontSize();
			Content content = ContentUtil.getContent(html, fontName, fontSize, color, underline, bold, italic);
			String receiveUserId = chatPane.getAttribute("userId");
			if (null != content) {
				chatWritePane.clearBody();
				content.setTimestamp(System.currentTimeMillis());
				UserChatController  ucc=appContext.getController(UserChatController.class);
				ucc.sendUserChatMessage(receiveUserId, content);
			}
		}
	}

	protected void sendGroupMessage(String key) {
		ChatPane chatPane = chatPanelMap.get(key);
		if (null != chatPane) {
			ChatWritePane chatWritePane = chatPane.getChatWritePane();
			String html = chatWritePane.getHtml();
			boolean underline = chatWritePane.isUnderline();
			boolean bold = chatWritePane.isBold();
			String color = chatWritePane.getWebColor();
			boolean italic = chatWritePane.isItalic();
			String fontName = chatWritePane.getFontName();
			int fontSize = chatWritePane.getFontSize();
			Content content = ContentUtil.getContent(html, fontName, fontSize, color, underline, bold, italic);
			String groupId = chatPane.getAttribute("groupId");
			if (null != content) {
				chatWritePane.clearBody();
				GroupChatController  gcc=appContext.getController(GroupChatController.class);
				gcc.sendGroupChatMessage(groupId, content);
			}
		}
	}
}
