package com.oim.fx.view;

//import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.imageio.ImageIO;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.core.business.box.FaceBox;
import com.oim.core.business.box.GroupMemberBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.constant.RemoteConstant;
import com.oim.core.business.controller.ChatController;
import com.oim.core.business.controller.RemoteController;
import com.oim.core.business.manager.FaceManager;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.manager.LastManager;
import com.oim.core.business.manager.PathManager;
import com.oim.core.business.manager.PromptManager;
import com.oim.core.business.manager.RemoteManager;
import com.oim.core.business.manager.SettingManager;
import com.oim.core.business.manager.VideoManager;
import com.oim.core.business.sender.ChatSender;
import com.oim.core.business.sender.GroupSender;
import com.oim.core.business.sender.VideoSender;
import com.oim.core.business.service.ContentToHtmlService;
import com.oim.core.business.view.ChatListView;
import com.oim.core.business.view.UserChatHistoryView;
import com.oim.core.common.AppConstant;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.file.FileHandleInfo;
import com.oim.core.common.component.file.FileInfo;
import com.oim.core.common.function.ChatInterfaceImpl;
import com.oim.core.common.util.AppCommonUtil;
import com.oim.core.js.ChatInterface;
import com.oim.fx.common.DataConverter;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.common.component.ScreenShotFrame;
import com.oim.fx.common.util.ContentUtil;
import com.oim.fx.ui.ChatListFrame;
import com.oim.fx.ui.chat.ChatItem;
import com.oim.fx.ui.chat.ChatPanel;
import com.oim.fx.ui.chat.SimpleHead;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.classics.RequestFrame;
import com.oim.ui.fx.classics.chat.FacePopup;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.common.util.OnlyFileUtil;
import com.only.fx.common.action.EventAction;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.FileData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;
import com.onlyxiahui.oim.face.bean.FaceCategory;
import com.onlyxiahui.oim.face.bean.FaceInfo;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2015年3月14日 上午11:14:38
 * @version 0.0.1
 */
public class ChatListViewImpl extends AbstractView implements ChatListView {

	protected ScreenShotFrame frame = new ScreenShotFrame();
	protected RequestFrame requestRemoteFrame = new RequestFrame();
	protected ChatListFrame chatListFrame = new ChatListFrame();

	protected Map<String, ChatPanel> chatPanelMap = new ConcurrentHashMap<String, ChatPanel>();
	protected Map<String, ChatItem> chatItemMap = new ConcurrentHashMap<String, ChatItem>();
	protected ConcurrentSkipListMap<String, ChatItem> itemMap = new ConcurrentSkipListMap<String, ChatItem>();

	protected Map<String, ListRootPanel> groupUserListMap = new ConcurrentHashMap<String, ListRootPanel>();

	protected FileChooser imageFileChooser;
	protected FileChooser fileChooser;

	protected FacePopup facePopup = new FacePopup();
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSS");

	ChatItem tempChatItem;
	private long shakeTime = 0;// 记录收到或者发送抖动信息的时间，为了不过于频繁抖动。
	DataConverter<String, String> emojiConverter;

	EventAction<FaceInfo> faceSelectAction;
	ChatInterface chatInterface;
	public ChatListViewImpl(AppContext appContext) {
		super(appContext);
		chatInterface=new ChatInterfaceImpl(appContext);
		imageFileChooser = new FileChooser();
		imageFileChooser.getExtensionFilters().add(new ExtensionFilter("图片文件", "*.png", "*.jpg", "*.bmp", "*.gif"));

		fileChooser = new FileChooser();
		// fileChooser.getExtensionFilters().add(new ExtensionFilter("文件"));
		requestRemoteFrame.setTitle("请求远程控制");
		initEvent();

		emojiConverter = new DataConverter<String, String>() {

			@Override
			public String converter(String data) {
				FaceManager fm = appContext.getManager(FaceManager.class);
				String text =fm.toEmojiImage(data);
				return text;
			}
		};
		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;
				initFace();
				return iterations;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void initFace() {
		FaceBox fb = appContext.getBox(FaceBox.class);
		List<FaceCategory> list = fb.getAllFaceCategoryList();
		for (FaceCategory fc : list) {
			set(fc.getId(), fc.getName(), fc.getFaceInfoList());
		}
	}

	private void set(String key, String name, List<FaceInfo> list) {
		List<IconButton> faceList = new ArrayList<IconButton>();

		if (null != list && !list.isEmpty()) {
			for (FaceInfo data : list) {
				// String faceKey = data.getKey();
				String normalPath = data.getShowPath();
				String hoverPath = data.getRealPath();
				String text = data.getText();
				double width = data.getWidth();
				double height = data.getHeight();

				double imageWidth = data.getImageWidth();
				double imageHeight = data.getImageHeight();

				Image normalImage = ImageBox.getImageClassPath(normalPath);
				Image hoverImage = ImageBox.getImageClassPath(hoverPath);
				Tooltip tooltip = new Tooltip(text);

				IconButton button = new IconButton(normalImage, hoverImage, null);
				button.setTooltip(tooltip);

				if (width > 0 && height > 0) {
					button.setPrefSize(width, height);
					button.setMinSize(width, height);
				}

				if (imageWidth > 0 && imageHeight > 0) {
					button.setImageSize(imageWidth, imageHeight);
				}
				//

				button.setOnAction(a -> {
					facePopup.hide();
					if (null != faceSelectAction) {
						faceSelectAction.execute(data);
					}
				});
				faceList.add(button);
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				facePopup.set(key, name, faceList);
			}
		});
	}

	private void initEvent() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

			}
		});
	}

	public boolean isShowing() {
		return chatListFrame.isShowing();
	}

	public boolean hasChatItem(String key) {
		return chatItemMap.containsKey(key);
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					chatListFrame.show();
					chatListFrame.toFront();
				} else {
					chatListFrame.hide();
				}
			}
		});
	}

	public boolean isGroupChatShowing(String groupId) {
		String key = this.getGroupKey(groupId);
		return chatListFrame.isShowing() && (null != tempChatItem && key.equals(tempChatItem.getAttribute("key")));
	}

	public boolean isUserChatShowing(String userId) {
		String key = this.getUserKey(userId);
		return chatListFrame.isShowing() && (null != tempChatItem && key.equals(tempChatItem.getAttribute("key")));
	}

	public void show(UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem chatItem = getUserChatItem(userData);
				ChatPanel chatPanel = getUserChatPanel(userData);
				select(chatItem, chatPanel);
//				ChatManage chatManage = appContext.getManager(ChatManage.class);
//				chatManage.showUserCaht(userData);
			}
		});
	}

	public void show(Group group) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem chatItem = getGroupChatItem(group);
				ChatPanel chatPanel = getGroupChatPanel(group);
				select(chatItem, chatPanel);
//				ChatManage chatManage = appContext.getManager(ChatManage.class);
//				chatManage.showGroupCaht(group);
			}
		});
	}

	private ChatItem getUserChatItem(UserData userData) {
		String key = this.getUserKey(userData.getId());
		ChatItem item = chatItemMap.get(key);
		if (null == item) {
			item = new ChatItem();
			item.addAttribute("key", key);
			chatItemMap.put(key, item);
			item.addCloseAction(new ChatItemCloseEvent(key));
			item.setOnMouseClicked(new ChatItemClickedEvent(key));
		}

		String text = AppCommonUtil.getDefaultShowName(userData);
		item.setText(text);
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getUserHead(userData.getId(), 40);
		item.setImage(image);
		return item;
	}

	protected ChatPanel getUserChatPanel(UserData userData) {
		String userId = userData.getId();
		String key = this.getUserKey(userData.getId());
		ChatPanel item = chatPanelMap.get(key);
		if (null == item) {
			item = new ChatPanel();
			item.addAttribute("key", key);
			item.addAttribute("userId", userData.getId());
			item.setEmojiConverter(emojiConverter);
			item.getShowPanel().setJavaScriptInterface(chatInterface);
			
			item.setSendAction(new ChatItemUserSendEvent(key));
			item.setCloseAction(new ChatItemCloseEvent(key));
			item.setOnWriteKeyReleased(e -> {// 回车发送
				if (!e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
					sendUserMessage(key);
					e.consume();
				}
			});

			EventAction<FaceInfo> faceAction = new EventAction<FaceInfo>() {

				ChatPanel item;

				@Override
				public void execute(FaceInfo value) {
					if (null != value) {
						Platform.runLater(new Runnable() {
							public void run() {
								insertFace(item, value);
							}
						});
					}
				}

				EventAction<FaceInfo> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);

			// 表情按钮
			Image normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face.png");
			Image hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face_hover.png");
			Image pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face_hover.png");
			final IconPane ib = new IconPane(normalImage, hoverImage, pressedImage);
			ib.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					faceSelectAction = (faceAction);
					facePopup.show(ib);
					// facePanel.setSelectAction(faceAction);
					// facePanel.show((int) event.getScreenX() - 240, (int)
					// event.getScreenY());
				}
			});
			item.addMiddleTool(ib);
			// 发送图片按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic_hover.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic_hover.png");

			IconPane iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
				ChatPanel item;

				@Override
				public void handle(MouseEvent event) {
					String fullPath = getPicture();
					item.insertImage("", "image", fullPath, fullPath);
				}

				EventHandler<MouseEvent> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);
			iconButton.setOnMouseClicked(e);
			item.addMiddleTool(iconButton);

			// ScreenShotAction screenShotAction = new ScreenShotAction() {
			//
			// ChatPanel item;
			//
			// @Override
			// public void saveImage(BufferedImage image) {
			//
			// Platform.runLater(new Runnable() {
			// public void run() {
			// if (null != image) {
			// saveBufferedImage(item, image);
			// }
			// }
			// });
			// }
			//
			// ScreenShotAction set(ChatPanel item) {
			// this.item = item;
			// return this;
			// }
			// }.set(item);

			EventAction<Image> screenShotAction = new EventAction<Image>() {
				ChatPanel item;

				@Override
				public void execute(Image image) {
					if (null != image) {
						saveImage(item, image);
					}
				}

				EventAction<Image> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);
			// 截屏按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");

			iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					frame.setOnImageAction(screenShotAction);
					frame.setVisible(true);
				}
			});
			item.addMiddleTool(iconButton);

			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_twitter.png");

			iconButton = new IconPane(normalImage);
			item.addMiddleTool(iconButton);

			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					sendShake(userData.getId());
				}
			});

			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/GVideoTurnOnVideo.png");

			iconButton = new IconPane(normalImage);
			item.addMiddleTool(iconButton);

			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					sendVideo(userData);
				}
			});

			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register_hover.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register.png");

			iconButton = new IconPane("消息记录", normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					showChatHistoryView(userData.getId());
					PromptManager pm = appContext.getManager(PromptManager.class);
					pm.remove("offline," + userData.getId());// 系统托盘停止跳动
				}
			});

			item.addMiddleRightTool(iconButton);

			chatPanelMap.put(key, item);
			StackPane rightPane = new StackPane();
			// rightPane.setPrefHeight(350);
			// rightPane.setMaxHeight(350);
			ImageView imageView = new ImageView();
			rightPane.getChildren().add(imageView);

			if (!"2".equals(userData.getGender())) {
				Image image = ImageBox.getImagePath("Resources/Images/Default/Show/default_av_girl_v3.png", 140, 380);
				imageView.setImage(image);
			} else {
				Image image = ImageBox.getImagePath("Resources/Images/Default/Show/default_av_boy_v3.png", 140, 380);
				imageView.setImage(image);
			}
			// item.setRightPane(rightPane);

			Image i = ImageBox.getImageClassPath("/resources/chat/images/user/file/aio_toobar_send_hover.png");

			IconButton isb = new IconButton();
			isb.setNormalImage(i);
			item.addTopTool(isb);

			isb.setOnAction(a -> {
				UserDataBox ub = appContext.getBox(UserDataBox.class);
				boolean online = ub.isOnline(userId);
				if (online) {
					File file = fileChooser.showOpenDialog(chatListFrame);
					if (null != file) {
						SettingManager sm = appContext.getManager(SettingManager.class);
						if (file.length() > sm.getChatSetting().getFileLimitSize()) {
							chatListFrame.showPrompt(sm.getChatSetting().getFileLimitInfo());
							return;
						}
						ChatController cc = appContext.getController(ChatController.class);
						cc.sendFile(userData.getId(), file);
					}
				} else {
					chatListFrame.showPrompt("对方不在线！");
				}
			});

			item.setFileEventAction(file -> {
				if (null != file) {
					UserDataBox ub = appContext.getBox(UserDataBox.class);
					boolean online = ub.isOnline(userId);
					if (online) {
						SettingManager sm = appContext.getManager(SettingManager.class);
						if (file.length() > sm.getChatSetting().getFileLimitSize()) {
							chatListFrame.showPrompt(sm.getChatSetting().getFileLimitInfo());
							return;
						}
						ChatController cc = appContext.getController(ChatController.class);
						cc.sendFile(userData.getId(), file);
					} else {
						chatListFrame.showPrompt("对方不在线！");
					}
				}
			});

			i = ImageBox.getImageClassPath("/resources/chat/images/user/top/Remote_MenuTextue2.png");

			isb = new IconButton();
			isb.setNormalImage(i);
			item.addTopTool(isb);

			isb.setOnAction(a -> {
				UserDataBox ub = appContext.getBox(UserDataBox.class);
				boolean online = ub.isOnline(userId);
				if (online) {
					RemoteController rc = appContext.getController(RemoteController.class);
					rc.requestRemoteControl(userId);
				} else {
					chatListFrame.showPrompt("对方不在线！");
				}
			});
		}

		if (null != userData.getRemarkName() && !"".equals(userData.getRemarkName())) {
			item.setName(userData.getRemarkName());
		} else {
			item.setName(userData.getNickname());
		}
		item.setText(userData.getSignature());
		return item;
	}

	///////////////////////////////////////////////////////// group
	private ChatItem getGroupChatItem(Group group) {
		String key = this.getGroupKey(group.getId());
		ChatItem item = chatItemMap.get(key);
		if (null == item) {
			item = new ChatItem();
			item.addAttribute("key", key);
			chatItemMap.put(key, item);
			item.addCloseAction(new ChatItemCloseEvent(key));
			item.setOnMouseClicked(new ChatItemClickedEvent(key));
		}

		item.setText(group.getName());

		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		Image image = him.getGroupHead(group.getId());
		item.setImage(image);
		return item;
	}

	protected ChatPanel getGroupChatPanel(Group group) {
		String key = this.getGroupKey(group.getId());
		ChatPanel item = chatPanelMap.get(key);
		if (null == item) {
			item = new ChatPanel();
			item.addAttribute("key", key);
			item.addAttribute("groupId", group.getId());
			item.setEmojiConverter(emojiConverter);
			item.getShowPanel().setJavaScriptInterface(chatInterface);
			
			item.setSendAction(new ChatItemGroupSendEvent(key));
			item.setCloseAction(new ChatItemCloseEvent(key));
			item.setOnWriteKeyReleased(e -> {// 回车发送
				if (!e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
					sendGroupMessage(key);
					e.consume();
				}
			});

			chatPanelMap.put(key, item);
			setGroupUserListPane(item, group.getId());

			EventAction<FaceInfo> faceAction = new EventAction<FaceInfo>() {

				ChatPanel item;

				@Override
				public void execute(FaceInfo value) {
					if (null != value) {
						Platform.runLater(new Runnable() {
							public void run() {
								insertFace(item, value);
							}
						});
					}
				}

				EventAction<FaceInfo> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);

			// 表情按钮
			Image normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face.png");
			Image hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face_hover.png");
			Image pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_face_hover.png");
			IconPane ib = new IconPane(normalImage, hoverImage, pressedImage);
			ib.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					faceSelectAction = (faceAction);
					facePopup.show(ib);
					// facePanel.setSelectAction(faceAction);
					// facePanel.show((int) event.getScreenX() - 240, (int)
					// event.getScreenY());
				}
			});
			item.addMiddleTool(ib);
			// 发送图片按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic_hover.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_sendpic_hover.png");

			IconPane iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			EventHandler<MouseEvent> e = new EventHandler<MouseEvent>() {
				ChatPanel item;

				@Override
				public void handle(MouseEvent event) {
					String fullPath = getPicture();
					item.insertImage("", "image", fullPath, fullPath);
				}

				EventHandler<MouseEvent> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);
			iconButton.setOnMouseClicked(e);
			item.addMiddleTool(iconButton);

			// ScreenShotAction screenShotAction = new ScreenShotAction() {
			//
			// ChatPanel item;
			//
			// @Override
			// public void saveImage(BufferedImage image) {
			//
			// Platform.runLater(new Runnable() {
			// public void run() {
			// if (null != image) {
			// saveBufferedImage(item, image);
			// }
			// }
			// });
			// }
			//
			// ScreenShotAction set(ChatPanel item) {
			// this.item = item;
			// return this;
			// }
			// }.set(item);

			EventAction<Image> screenShotAction = new EventAction<Image>() {
				ChatPanel item;

				@Override
				public void execute(Image image) {
					if (null != image) {
						saveImage(item, image);
					}
				}

				EventAction<Image> set(ChatPanel item) {
					this.item = item;
					return this;
				}
			}.set(item);
			// 截屏按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");

			iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					frame.setOnImageAction(screenShotAction);
					frame.setVisible(true);
				}
			});
			item.addMiddleTool(iconButton);

		}
		item.setName(group.getName());
		item.setText(group.getPublicNotice());
		return item;
	}

	//////////////////////////////////////////////////////////
	protected void select(ChatItem item, ChatPanel chatPanel) {
		if (item != this.tempChatItem) {
			if (null != this.tempChatItem) {
				this.tempChatItem.setSelected(false);
			}
			item.setSelected(true);
		}
		this.tempChatItem = item;
		chatListFrame.addItem(item);
		chatListFrame.setChatPanel(chatPanel);
		if (!itemMap.containsValue(item)) {
			String key = item.getAttribute("key");
			itemMap.put(key, item);
		}
	}

	@Override
	public boolean hasGroupChat(String groupId) {
		String key = getGroupKey(groupId);
		return chatItemMap.containsKey(key);
	}

	@Override
	public boolean hasUserChat(String userId) {
		String key = getUserKey(userId);
		return chatItemMap.containsKey(key);
	}

	protected void sendVideo(UserData userData) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		VideoManager vm = this.appContext.getManager(VideoManager.class);
		vm.showSendVideoFrame(userData);
		VideoSender vh = this.appContext.getSender(VideoSender.class);
		vh.requestVideo(sendUser.getId(), userData.getId());
	}

	public void userChat(boolean isOwn, UserData userData, Content content) {
		if (null != userData) {
			String name = AppCommonUtil.getDefaultShowName(userData);
			// String name = userData.getNickname();
			String time = OnlyDateUtil.dateToString(new Date(content.getTimestamp()), "yyyy-MM-dd HH:mm:ss");
			// String color =
			// ColorUtil.getColorInHexFromRGB(Color.blue.getRed(),
			// Color.blue.getGreen(), Color.blue.getBlue());//
			// ColorUtil.getColorInHexFromRGB(32,
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ChatPanel chatPanel = getUserChatPanel(userData);
					HeadImageManager him = appContext.getManager(HeadImageManager.class);
					String headPath = him.getUserHeadPath(userData.getId());

					String orientation = isOwn ? "right" : "left";
					// String headPath = "file:/" + new
					// File(path).getAbsolutePath();

					insertShow(chatPanel, name, headPath, time, orientation, content); // 143,
				}
			});

			// insertShowChat(chatPanel, name, color, time, content); // 143,
		}
	}

	public void groupChat(Group group, UserData userData, Content content) {
		if (null != userData) {
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserData user = pb.getUserData();
			boolean isOwn = user.getId().equals(userData.getId());
			// String name = userData.getNickname();
			String name = AppCommonUtil.getDefaultShowName(userData);
			String time = OnlyDateUtil.getCurrentDateTime();
			// String color = (isOwn) ? ColorUtil.getColorInHexFromRGB(32, 143,
			// 62) : ColorUtil.getColorInHexFromRGB(Color.blue.getRed(),
			// Color.blue.getGreen(), Color.blue.getBlue());
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					ChatPanel chatPanel = getGroupChatPanel(group);

					HeadImageManager him = appContext.getManager(HeadImageManager.class);
					String headPath = him.getUserHeadPath(userData.getId());

					String orientation = isOwn ? "right" : "left";
					// String headPath = "file:/" + new
					// File(path).getAbsolutePath();

					insertShow(chatPanel, name, headPath, time, orientation, content); // 143,
				}
			});

			// insertShowChat(chatPanel, name, color, time, content);
		}
	}

	protected void insertShow(ChatPanel cp, String name, String head, String time, String orientation, Content content) {
		if (null != cp) {

			ContentToHtmlService cths = appContext.getService(ContentToHtmlService.class);
			String contentTag = cths.getBubbleInsertContentTag(content);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// String p = head.replace("\\", "/");
					String headPath = "";
					if (null != head && head.startsWith("/")) {
						headPath = "file:" + head;
					} else {
						headPath = "file:/" + head;
					}
					cp.insertShowLastHtml(name, headPath, time, orientation, contentTag);
				}
			});
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
										String path = "";

										if (appPath.startsWith("/")) {
											path = "file:" + appPath;
										} else {
											path = "file:/" + appPath;
										}
										cp.replaceImage(id, path);
									}
								});
							} else {
								String id = (null != fi) ? fi.getId() : "";
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										String tempImage = "Resources/Images/Default/ChatFrame/ImageLoading/image_download_fail.png";
										File file = new File(tempImage);
										if (file.exists()) {
											tempImage = file.getAbsolutePath();
										}
										cp.replaceImage(id, tempImage);
									}
								});
							}
						}
					}
				};
				ImageManager im = this.appContext.getManager(ImageManager.class);
				// im.downloadImage(content, fileBackAction);
				im.downloadImage(items, backAction);
			}
		}
	}

	protected void insertShowChat(ChatPanel cp, String name, String color, String time, Content content) {
		if (null != cp) {
			
			ContentToHtmlService cths = appContext.getService(ContentToHtmlService.class);
			String contentTag = cths.getInsertContentTag(content);
			//String nameTag = ContentUtil.getInsertNameTag(name, color, time);
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					//cp.insertShowLastHtml(nameTag);
					cp.insertShowLastHtml(contentTag);
				}
			});
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
										String path = "";

										if (appPath.startsWith("/")) {
											path = "file:" + appPath;
										} else {
											path = "file:/" + appPath;
										}
										cp.replaceImage(id, path);
									}
								});
							} else {
								String id = (null != fi) ? fi.getId() : "";
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										String tempImage = "Resources/Images/Default/ChatFrame/ImageLoading/image_download_fail.png";
										File file = new File(tempImage);
										if (file.exists()) {
											tempImage = file.getAbsolutePath();
										}
										cp.replaceImage(id, tempImage);
									}
								});
							}
						}
					}
				};
				ImageManager im = this.appContext.getManager(ImageManager.class);
				// im.downloadImage(content, fileBackAction);
				im.downloadImage(items, backAction);
			}
		}
	}

	protected void sendUserMessage(String key) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		ChatPanel chatPanel = chatPanelMap.get(key);
		if (null != chatPanel) {
			String html = chatPanel.getHtml();

			boolean underline = chatPanel.isUnderline();
			boolean bold = chatPanel.isBold();
			String color = chatPanel.getWebColor();
			boolean italic = chatPanel.isItalic();
			String fontName = chatPanel.getFontName();
			int fontSize = chatPanel.getFontSize();
			Content content = ContentUtil.getContent(html, fontName, fontSize, color, underline, bold, italic);
			String receiveUserId = chatPanel.getAttribute("userId");
			if (null != content) {
				chatPanel.initializeWriteHtml();

				String name = AppCommonUtil.getDefaultPersonalShowName(sendUser);
				String time = OnlyDateUtil.getCurrentDateTime();
				// String nameColor = ColorUtil.getColorInHexFromRGB(32, 143,
				// 62);

				DataBackActionAdapter action = new DataBackActionAdapter() {
					@Back
					public void back(Info info, @Define("userData") UserData user) {
						if (info.isSuccess()) {
							UserDataBox ub = appContext.getBox(UserDataBox.class);
							UserData userData = ub.getUserData(receiveUserId);
							if (null != userData) {
								LastManager lastManage = appContext.getManager(LastManager.class);
								String text = CoreContentUtil.getText(content);
								//lastManage.addOrUpdateLastUser(userData);
							}
						}
					}
				};
				List<Item> items = CoreContentUtil.getImageItemList(content);
				if (!items.isEmpty()) {
					CallBackAction<List<FileData>> fileBackAction = new CallBackAction<List<FileData>>() {

						@Override
						public void execute(List<FileData> list) {

							for (FileData fd : list) {
								String name = fd.getName();
								String appPath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + sendUser.getNumber() + "/image/" + name;

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										String path = "";

										if (appPath.startsWith("/")) {
											path = "file:" + appPath;
										} else {
											path = "file:/" + appPath;
										}
										chatPanel.replaceImage(fd.getId(), path);
									}
								});
							}
						}
					};

					ImageManager im = this.appContext.getManager(ImageManager.class);
					BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

						@Override
						public void back(BackInfo backInfo, List<FileHandleInfo> t) {
							ChatSender ch = appContext.getSender(ChatSender.class);
							//ch.sendUserChatMessage(receiveUserId, sendUser.getId(), content, action);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {

									HeadImageManager him = appContext.getManager(HeadImageManager.class);
									String headPath = him.getUserHeadPath(sendUser.getId());

									String orientation = "right";
									// String headPath = "file:/" + new
									// File(path).getAbsolutePath();

									insertShow(chatPanel, name, headPath, time, orientation, content); // 143,
									// insertShowChat(chatPanel, name,
									// nameColor, time, content);
								}
							});
							im.handleLocalImage(items, fileBackAction);
						}
					};
					im.uploadImage(items, backAction);
				} else {
					ChatSender ch = appContext.getSender(ChatSender.class);
					///ch.sendUserChatMessage(receiveUserId, sendUser.getId(), content, action);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							HeadImageManager him = appContext.getManager(HeadImageManager.class);
							String headPath = him.getUserHeadPath(sendUser.getId());

							String orientation = "right";
							// String headPath = "file:/" + new
							// File(path).getAbsolutePath();

							insertShow(chatPanel, name, headPath, time, orientation, content);
							// insertShowChat(chatPanel, name, nameColor, time,
							// content);
						}
					});
				}
			}
		}
	}

	private void sendGroupMessage(String key) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		ChatPanel chatPanel = chatPanelMap.get(key);
		if (null != chatPanel) {
			String html = chatPanel.getHtml();

			boolean underline = chatPanel.isUnderline();
			boolean bold = chatPanel.isBold();
			String color = chatPanel.getWebColor();
			boolean italic = chatPanel.isItalic();
			String fontName = chatPanel.getFontName();
			int fontSize = chatPanel.getFontSize();
			Content content = ContentUtil.getContent(html, fontName, fontSize, color, underline, bold, italic);
			String groupId = chatPanel.getAttribute("groupId");
			if (null != content) {
				chatPanel.initializeWriteHtml();
				List<Item> items = CoreContentUtil.getImageItemList(content);
				if (!items.isEmpty()) {// 判断信息中是否有图片，没有图片直接发送，节省响应事件
					ImageManager im = this.appContext.getManager(ImageManager.class);
					BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {
						@Override
						public void back(BackInfo backInfo, List<FileHandleInfo> t) {
							ChatSender ch = appContext.getSender(ChatSender.class);
							//ch.sendGroupChatMessage(groupId, user.getId(), content);
						}
					};
					im.uploadImage(items, backAction);
				} else {
					ChatSender ch = appContext.getSender(ChatSender.class);
					//ch.sendGroupChatMessage(groupId, user.getId(), content);
				}
			}
		}
	}

	private void selectChatItem(String key) {
		ChatItem ci = chatItemMap.get(key);
		ChatPanel cp = chatPanelMap.get(key);
		if (null != ci && null != cp) {
			select(ci, cp);
		}
	}

	private void removeChatItem(String key) {
		ChatItem item = chatItemMap.get(key);
		if (null != item) {
			chatListFrame.removeItem(item);
			itemMap.remove(key);

			if (itemMap.isEmpty()) {
				setVisible(false);
			} else {
				if (item.isSelected()) {
					Iterator<String> iterator = itemMap.keySet().iterator();
					if (iterator.hasNext()) {
						String nextKey = iterator.next();
						selectChatItem(nextKey);
					}
				}
			}
		}
	}

	protected String getUserKey(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("user_");
		sb.append(userId);
		return sb.toString();
	}

	protected String getGroupKey(String groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append("group_");
		sb.append(groupId);
		return sb.toString();
	}

	protected void setGroupUserListPane(ChatPanel item, String groupId) {
		ListRootPanel groupUserList = groupUserListMap.get(groupId);
		if (null == groupUserList) {
			updateGroupUserList(groupId);
		} else {
			if (null != item) {
				groupUserList.setPrefWidth(140);
				item.setRightPane(groupUserList);
			}
		}
	}

	public void setGroupUserList(final String groupId) {
		GroupMemberBox gmb = appContext.getBox(GroupMemberBox.class);
		List<GroupMember> gml = gmb.getGroupMemberList(groupId);
		if (null == gml) {
			DataBackAction dataBackAction = new DataBackActionAdapter() {
				@Back
				public void back(@Define("userDataList") List<UserData> userDataList, @Define("groupMemberList") List<GroupMember> groupMemberList) {
					setGroupUserList(groupId, userDataList, groupMemberList);
				}
			};
			GroupSender gh = this.appContext.getSender(GroupSender.class);
			gh.getGroupMemberListWithUserDataList(groupId, dataBackAction);
		}
	}

	public void updateGroupUserList(final String groupId) {

		DataBackAction dataBackAction = new DataBackActionAdapter() {

			@Back
			public void back(@Define("userDataList") List<UserData> userDataList, @Define("groupMemberList") List<GroupMember> groupMemberList) {
				setGroupUserList(groupId, userDataList, groupMemberList);
			}
		};
		GroupSender gh = this.appContext.getSender(GroupSender.class);
		gh.getGroupMemberListWithUserDataList(groupId, dataBackAction);
	}

	private void setGroupUserList(String groupId, List<UserData> userDataList, List<GroupMember> groupMemberList) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				GroupMemberBox gmb = appContext.getBox(GroupMemberBox.class);
				gmb.putGroupMemberList(groupId, groupMemberList);
				ListRootPanel groupUserList = groupUserListMap.get(groupId);
				if (groupUserList == null) {
					groupUserList = new ListRootPanel();
				}
				groupUserList.clearNode();
				for (UserData userData : userDataList) {
					SimpleHead item = new SimpleHead();

					item.setOnMouseClicked((MouseEvent me) -> {
						if (me.getClickCount() == 2) {
							//ChatManage chatManage = appContext.getManager(ChatManage.class);
							//chatManage.showCahtFrame(userData);
						}
						me.consume();
					});
					item.setText(userData.getNickname());
					HeadImageManager him = appContext.getManager(HeadImageManager.class);
					Image image = him.getUserHead(userData.getId(), 40);
					// Image image =
					// ImageBox.getImagePath("Resources/Images/Head/User/" +
					// userData.getHead() + ".png", 20, 20);
					item.setHeadSize(20);
					item.setHeadRadius(20);
					item.setImage(image);
					item.addAttribute(UserData.class, userData);
					groupUserList.addNode(item);
				}

				String key = getGroupKey(groupId);
				ChatPanel chatPanel = chatPanelMap.get(key);
				if (null != chatPanel) {
					groupUserList.setPrefWidth(140);
					chatPanel.setRightPane(groupUserList);
				}
			}
		});
	}

	protected class ChatItemGroupSendEvent implements EventHandler<ActionEvent> {

		String key;

		public ChatItemGroupSendEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			sendGroupMessage(key);
		}
	}

	protected class ChatItemUserSendEvent implements EventHandler<ActionEvent> {

		String key;

		public ChatItemUserSendEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			sendUserMessage(key);
		}
	}

	protected class ChatItemCloseEvent implements EventHandler<ActionEvent> {

		String key;

		public ChatItemCloseEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			removeChatItem(key);
		}
	}

	protected class ChatItemClickedEvent implements EventHandler<MouseEvent> {

		String key;

		public ChatItemClickedEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(MouseEvent event) {
			selectChatItem(key);
		}
	}

	/**
	 * 发送抖动操作
	 *
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param receiveId
	 */
	protected void sendShake(String receiveId) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		ChatSender ch = this.appContext.getSender(ChatSender.class);
		//ch.sendShake(receiveId, sendUser.getId());// 发送给接受方
		shake();// 自己执行抖动
	}

	@Override
	public void doShake(UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem chatItem = getUserChatItem(userData);
				ChatPanel chatPanel = getUserChatPanel(userData);
				select(chatItem, chatPanel);
				//ChatManage chatManage = appContext.getManager(ChatManage.class);
				//chatManage.showUserCaht(userData);
				shake();
			}
		});
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
							chatListFrame.setX(chatListFrame.getX() + 4);
							chatListFrame.setY(chatListFrame.getY() - 4);
							try {
								Thread.sleep(40);
								chatListFrame.setX(chatListFrame.getX() - 8);
								chatListFrame.setY(chatListFrame.getY());
								Thread.sleep(40);
								chatListFrame.setX(chatListFrame.getX());
								chatListFrame.setY(chatListFrame.getY() + 4);
								Thread.sleep(40);
								chatListFrame.setX(chatListFrame.getX() + 4);
								chatListFrame.setY(chatListFrame.getY());
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

	public void saveImage(ChatPanel cp, Image image) {
		String fileName = dateFormat.format(new Date()).toString() + ".png";
		PathManager pm = appContext.getManager(PathManager.class);
		String savePath = pm.getScreenshotSavePath();
		String path = savePath + fileName;
		try {
			OnlyFileUtil.checkOrCreateFolder(path);
			File file = new File(path);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			cp.insertImage("", "image", path, path);
		} catch (IOException ex) {
		}
	}

	public void saveBufferedImage(ChatPanel cp, BufferedImage image) {
		String fileName = dateFormat.format(new Date()).toString() + ".jpg";
		PathManager pm = appContext.getManager(PathManager.class);
		String savePath = pm.getScreenshotSavePath();
		String path = savePath + fileName;
		try {
			OnlyFileUtil.checkOrCreateFolder(path);
			ImageIO.write(image, "jpg", new File(path));
			cp.insertImage("", "image", path, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPicture() {
		String fullPath = null;
		File file = imageFileChooser.showOpenDialog(chatListFrame);
		if (file != null) {
			if (file.exists()) {
				SettingManager sm = appContext.getManager(SettingManager.class);
				if (file.length() > sm.getChatSetting().getImageLimitSize()) {
					chatListFrame.showPrompt(sm.getChatSetting().getImageLimitInfo());
					return null;
				}
				fullPath = file.getAbsolutePath();
			}
		}
		return fullPath;
	}

	public void insertFace(ChatPanel cp, FaceInfo fi) {

		FaceManager fm = appContext.getManager(FaceManager.class);
		String tag = fm.getFaceImageTag(fi);
		if (null != tag) {
			cp.insertWriteSelectionHtml(tag);
		}
		// String categoryId = fd.getCategoryId();
		// String key = fd.getKey();
		//
		// // String path = "Resources/Images/Face/" + value + ".gif ";
		// File file = new File(path);
		// if (file.exists()) {
		// String fullPath = file.getAbsolutePath();
		// String v = categoryId + "," + key;
		// // String v = "classical" + "," + value;
		// cp.insertImage("", "face", v, fullPath);
		// }
	}

	///////////////////////////////////
	private Map<String, UserChatHistoryView> chvMap = new ConcurrentHashMap<String, UserChatHistoryView>();

	/**
	 * 显示历史记录
	 *
	 * @param userId
	 */
	public void showChatHistoryView(String userId) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				UserChatHistoryView chf = getChatHistoryView(userId);
				chf.setVisible(true);
			}
		});
	}

	public UserChatHistoryView getChatHistoryView(String userId) {

		UserChatHistoryView chv = chvMap.get(userId);
		if (null == chv) {
			chv = appContext.getObject(UserChatHistoryView.class, true);// .getNewView(UserChatHistoryView.class);
			chv.setUserId(userId);
			chvMap.put(userId, chv);
		}
		return chv;
	}

	public void userChatHistory(UserData userData, List<UserChatHistoryData> contents) {

		// StringBuilder div = new StringBuilder();
		// div.append("<div>");
		if (null != contents) {
			for (UserChatHistoryData chd : contents) {
				UserData sendUserData = chd.getSendUserData();
				Content content = chd.getContent();
				String name = sendUserData.getNickname();
				String time = OnlyDateUtil.dateToDateTime(new Date(content.getTimestamp()));
				// String greenColor = ColorUtil.getColorInHexFromRGB(32, 143,
				// 62);
				// String blueColor =
				// ColorUtil.getColorInHexFromRGB(Color.blue.getRed(),
				// Color.blue.getGreen(), Color.blue.getBlue());
				PersonalBox pb = appContext.getBox(PersonalBox.class);
				UserData user = pb.getUserData();
				boolean isOwn = user.getId().equals(sendUserData.getId());

				// String nameTag = ContentUtil.getInsertNameTag(name, isOwn ?
				// greenColor : blueColor, time);
				// div.append(nameTag);
				//String contentTag = ContentUtil.getBubbleInsertContentTag(content);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						HeadImageManager him = appContext.getManager(HeadImageManager.class);
						String head = him.getUserHeadPath(sendUserData.getId());
						String headPath = "";
						if (null != head && head.startsWith("/")) {
							headPath = "file:" + head;
						} else {
							headPath = "file:/" + head;
						}
						String orientation = isOwn ? "right" : "left";

						ChatPanel chatPanel = getUserChatPanel(userData);
						//chatPanel.insertShowBeforeHtml(name, headPath, time, orientation, contentTag);
					}
				});

				// div.append(contentTag);
			}

		}
		// div.append("</div>");

		// Platform.runLater(new Runnable() {
		// @Override
		// public void run() {
		// ChatPanel chatPanel = getUserChatPanel(userData);
		// chatPanel.insertShowBeforeHtml(div.toString());//
		// .insertShowLastHtml(div.toString());
		// }
		// });
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

			@Override
			public void back(BackInfo backInfo, List<FileHandleInfo> list) {
				for (FileHandleInfo info : list) {
					FileInfo fi = info.getFileInfo();
					if (info.isSuccess()) {
						File file = (null != fi) ? fi.getFile() : null;
						String name = (null != file) ? file.getName() : "";
						String id = (null != fi) ? fi.getId() : "";
						String appPath = AppConstant.userHome + "/" + AppConstant.app_home_path + "/" + user.getNumber() + "/image/" + name;

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String path = "";

								if (appPath.startsWith("/")) {
									path = "file:" + appPath;
								} else {
									path = "file:/" + appPath;
								}
								ChatPanel chatPanel = getUserChatPanel(userData);
								chatPanel.replaceImage(id, path);
							}
						});
					} else {
						String id = (null != fi) ? fi.getId() : "";
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								String tempImage = "Resources/Images/Default/ChatFrame/ImageLoading/image_download_fail.png";
								File file = new File(tempImage);
								if (file.exists()) {
									tempImage = file.getAbsolutePath();
								}
								ChatPanel chatPanel = getUserChatPanel(userData);
								chatPanel.replaceImage(id, tempImage);
							}
						});
					}
				}
			}
		};
		if (null != contents) {
			for (UserChatHistoryData chd : contents) {
				Content content = chd.getContent();
				ImageManager im = this.appContext.getManager(ImageManager.class);
				List<Item> items = CoreContentUtil.getImageItemList(content);
				im.downloadImage(items, backAction);
			}
		}
	}

	@Override
	public boolean hasRequestRemoteControl(String userId) {
		return requestRemoteFrame.isShowing();
	}

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
	public void updateUserChatInfo(String userId, String text, String time) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void setChatUserItemRed(String userId, boolean red, int count) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void userChat(boolean isOwn, UserData showUserData, UserData chatUserData, Content content) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void showUserChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void loadUserChatHistory(UserData userData, PageData page, List<UserChatHistoryData> list) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void updateGroupChatInfo(String groupId, String text, String time) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void setChatGroupItemRed(String groupId, boolean red, int count) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void groupChat(boolean isOwn, Group group, UserData chatUserData, Content content) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void showGroupChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void loadGroupChatHistory(Group group, PageData page, List<GroupChatHistoryData> contents) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void addOrUpdateGroupMember(String groupId, GroupMember groupMember, UserData userData) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void removeGroupMember(String groupId, String userId) {
		// TODO 自动生成的方法存根
		
	}
}
