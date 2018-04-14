/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.imageio.ImageIO;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.oim.core.common.util.ColorUtil;
import com.oim.fx.common.DataConverter;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.common.util.ContentUtil;
import com.oim.fx.ui.ChatListFrame;
import com.oim.fx.ui.chat.ChatItem;
import com.oim.fx.ui.chat.ChatPanel;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.classics.chat.FacePopup;
import com.only.common.util.OnlyDateUtil;
import com.only.common.util.OnlyFileUtil;
import com.only.common.util.OnlyStringUtil;
import com.only.fx.common.action.EventAction;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.oim.face.bean.FaceInfo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class ChatListFrameUseTest extends Application {

	ChatListFrame chatListFrame = new ChatListFrame();
	Map<String, UserData> userMap = new ConcurrentHashMap<String, UserData>();
	private Map<String, ChatPanel> chatPanelMap = new ConcurrentHashMap<String, ChatPanel>();
	private Map<String, ChatItem> chatItemMap = new ConcurrentHashMap<String, ChatItem>();
	private ConcurrentSkipListMap<String, ChatItem> itemMap = new ConcurrentSkipListMap<String, ChatItem>();

	private Map<String, ListRootPanel> groupUserListMap = new ConcurrentHashMap<String, ListRootPanel>();

	private FileChooser imageFileChooser;
	private FileChooser fileChooser;
	EmojiConverter ec = EmojiConverter.getInstance();
	// FacePanel facePanel = FacePanel.getFacePanel();
	protected FacePopup facePopup = new FacePopup();
	//ScreenShotWindow ssw = ScreenShotWindow.getScreenShotWindow();

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSS");

	ChatItem tempChatItem;
	private long shakeTime = 0;// 记录收到或者发送抖动信息的时间，为了不过于频繁抖动。
	DataConverter<String, String> emojiConverter;
	@Override
	public void start(Stage primaryStage) {
		chatListFrame.show();
		imageFileChooser = new FileChooser();
		imageFileChooser.getExtensionFilters().add(new ExtensionFilter("图片文件", "*.png", "*.jpg", "*.bmp", "*.gif"));

		fileChooser = new FileChooser();
		//List<FaceInfo> list = faceConfig.createFaceList();
		//facePopup.set("classical", "经典", list);
		//list = faceConfig.emotionList();
		//facePopup.set("emotion", "大表情", list);
		
		emojiConverter = new DataConverter<String, String>() {

			@Override
			public String converter(String data) {
				//String path = "Resources/Images/Face/emoji/23x23/";
				//String text = FaceUtil.toEmojiImage(data, path, ".png");
				
//				String text =ec.toHtml(data);
//				System.out.println(text);
				return "";
			}
		};
		initTest();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private void initTest() {
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(100) + 1;

			int v = i % 2;

			int sex = (v == 0) ? 2 : 1;

			String name = (sex == 1) ? "男" : "女";

			UserData ud = new UserData();
			ud.setId("user_" + i);
			ud.setHead(index + "");
			ud.setRemarkName(name + "神经" + i);
			ud.setNickname("(哈加额)");
			ud.setStatus("[2G]");
			ud.setSignature("哈哈哈，又有新闻了");
			ud.setGender(sex + "");
			show(ud);
			String key = this.getUserKey(ud.getId());
			userMap.put(key, ud);
		}
	}

	public void show(UserData userData) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ChatItem chatItem = getUserChatItem(userData);
				ChatPanel chatPanel = getUserChatPanel(userData);
				select(chatItem, chatPanel);
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

		if (null != userData.getRemarkName() && !"".equals(userData.getRemarkName())) {
			item.setText(userData.getRemarkName());
		} else {
			item.setText(userData.getNickname());
		}
		String head = (OnlyStringUtil.isNotBlank(userData.getHead())) ? userData.getHead() : "1";

		Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + head + ".png", 34, 34, 8, 8);
		item.setImage(image);
		return item;
	}

	private ChatPanel getUserChatPanel(UserData userData) {
		String key = this.getUserKey(userData.getId());
		ChatPanel item = chatPanelMap.get(key);
		if (null == item) {
			item = new ChatPanel();
			item.setEmojiConverter(emojiConverter);
			item.addAttribute("key", key);
			item.addAttribute("userId", userData.getId());
			item.setSendAction(new ChatItemUserSendEvent(key));
			item.setCloseAction(new ChatItemCloseEvent(key));
			item.setOnWriteKeyReleased(e -> {// 回车发送
				if (!e.isShiftDown() && e.getCode() == KeyCode.ENTER) {
					sendUserMessage(key);
					e.consume();
				}
			});
			// ExecuteAction faceAction = new ExecuteAction() {
			//
			// ChatPanel item;
			//
			// @Override
			// public <T, E> E execute(T value) {
			// if (null != value) {
			// Platform.runLater(new Runnable() {
			// public void run() {
			// insertFace(item, value.toString());
			// }
			// });
			// }
			// return null;
			// }
			//
			// ExecuteAction set(ChatPanel item) {
			// this.item = item;
			// return this;
			// }
			// }.set(item);

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
					//facePopup.setAction(faceAction);
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

//			ScreenShotAction screenShotAction = new ScreenShotAction() {
//
//				ChatPanel item;
//
//				@Override
//				public void saveImage(BufferedImage image) {
//
//					Platform.runLater(new Runnable() {
//						public void run() {
//							if (null != image) {
//								saveBufferedImage(item, image);
//							}
//						}
//					});
//				}
//
//				ScreenShotAction set(ChatPanel item) {
//					this.item = item;
//					return this;
//				}
//			}.set(item);
			// 截屏按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");

			iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
//					ssw.setAction(screenShotAction);
//					ssw.setVisible(true);
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

				}
			});

			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register_hover.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_register.png");

			iconButton = new IconPane("消息记录", normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {

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
			//item.setRightPane(rightPane);

			Image i = ImageBox.getImageClassPath("/resources/chat/images/user/file/aio_toobar_send_hover.png");

			IconButton isb = new IconButton();
			isb.setNormalImage(i);
			item.addTopTool(isb);

			isb.setOnAction(a -> {
				File file = fileChooser.showOpenDialog(chatListFrame);
				if (null != file) {
				}
			});

			item.setFileEventAction(file -> {
				if (null != file) {
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
		String head = (OnlyStringUtil.isNotBlank(group.getHead())) ? group.getHead() : "1";
		Image image = ImageBox.getImagePath("Resources/Images/Head/Group/" + head + ".png", 34, 34, 8, 8);
		item.setImage(image);
		return item;
	}

	private ChatPanel getGroupChatPanel(Group group) {
		String key = this.getGroupKey(group.getId());
		ChatPanel item = chatPanelMap.get(key);
		if (null == item) {
			item = new ChatPanel();
			item.addAttribute("key", key);
			item.addAttribute("groupId", group.getId());
			item.setSendAction(new ChatItemGroupSendEvent(key));
			item.setCloseAction(new ChatItemCloseEvent(key));
			chatPanelMap.put(key, item);
			setGroupUserListPane(item, group.getId());

			// ExecuteAction faceAction = new ExecuteAction() {
			//
			// ChatPanel item;
			//
			// @Override
			// public <T, E> E execute(T value) {
			// if (null != value) {
			// Platform.runLater(new Runnable() {
			// public void run() {
			// insertFace(item, value.toString());
			// }
			// });
			// }
			// return null;
			// }
			//
			// ExecuteAction set(ChatPanel item) {
			// this.item = item;
			// return this;
			// }
			// }.set(item);

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
					//facePopup.setAction(faceAction);
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

//			ScreenShotAction screenShotAction = new ScreenShotAction() {
//
//				ChatPanel item;
//
//				@Override
//				public void saveImage(BufferedImage image) {
//
//					Platform.runLater(new Runnable() {
//						public void run() {
//							if (null != image) {
//								saveBufferedImage(item, image);
//							}
//						}
//					});
//				}
//
//				ScreenShotAction set(ChatPanel item) {
//					this.item = item;
//					return this;
//				}
//			}.set(item);
			// 截屏按钮
			normalImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			hoverImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");
			pressedImage = ImageBox.getImageClassPath("/resources/chat/images/middletoolbar/aio_quickbar_cut.png");

			iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			iconButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
//					ssw.setAction(screenShotAction);
//					ssw.setVisible(true);
				}
			});
			item.addMiddleTool(iconButton);

		}
		item.setName(group.getName());
		item.setText(group.getPublicNotice());
		return item;
	}

	//////////////////////////////////////////////////////////
	private void select(ChatItem item, ChatPanel chatPanel) {
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

	public boolean hasGroupChat(String groupId) {
		String key = getGroupKey(groupId);
		return chatItemMap.containsKey(key);
	}

	public boolean hasUserChat(String userId) {
		String key = getUserKey(userId);
		return chatItemMap.containsKey(key);
	}

	public void userChat(boolean isOwn, UserData userData, Content content) {
		if (null != userData) {
			String name = userData.getNickname();
			String time = OnlyDateUtil.dateToString(new Date(content.getTimestamp()), "yyyy-MM-dd HH:mm:ss");
			//String color = ColorUtil.getColorInHexFromRGB(Color.blue.getRed(), Color.blue.getGreen(), Color.blue.getBlue());// ColorUtil.getColorInHexFromRGB(32,
			ChatPanel chatPanel = getUserChatPanel(userData);
			String head = (OnlyStringUtil.isNotBlank(userData.getHead())) ? userData.getHead() : "1";

			String orientation = isOwn ? "right" : "left";
			String headPath = "file:/" + new File("Resources/Images/Head/User/" + head + ".png").getAbsolutePath();

			insertShow(chatPanel, name, headPath, time, orientation, content); // 143,
		}
	}

	public void groupChat(Group group, UserData userData, Content content) {
		if (null != userData) {
			// boolean isOwn = user.getId().equals(userData.getId());
			// String name = userData.getNickname();
			// String time = OnlyDateUtil.getCurrentDateTime();
			// String color = (isOwn) ? ColorUtil.getColorInHexFromRGB(32, 143,
			// 62) : ColorUtil.getColorInHexFromRGB(Color.blue.getRed(),
			// Color.blue.getGreen(), Color.blue.getBlue());
			// ChatPanel chatPanel = getGroupChatPanel(group);
			// insertShowChat(chatPanel, name, color, time, content);
		}
	}

	// private void insertShowChat(ChatPanel cp, String name, String color,
	// String time, Content content) {
	// if (null != cp) {
	// String nameTag = ContentUtil.getInsertNameTag(name, color, time);
	// String contentTag = ContentUtil.getInsertContentTag(content);
	//
	// Platform.runLater(new Runnable() {
	// @Override
	// public void run() {
	// cp.insertShowLastHtml(nameTag);
	// cp.insertShowLastHtml(contentTag);
	// }
	// });
	// }
	// }

	private void insertShow(ChatPanel cp, String name, String head, String time, String orientation, Content content) {
		if (null != cp) {
			//String contentTag = ContentUtil.getInsertContentTag(content);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					System.out.println(head);
					String p = head.replace("\\", "/");
					//cp.insertShowLastHtml(name, p, time, orientation, contentTag);
				}
			});
		}
	}

	private void sendUserMessage(String key) {
		UserData sendUser = userMap.get(key);
		ChatPanel chatPanel = chatPanelMap.get(key);
		if (null != chatPanel) {
			String html = chatPanel.getHtml();
			// html = html.replace("&amp;", "&");
			System.out.println(html);
			boolean underline = chatPanel.isUnderline();
			boolean bold = chatPanel.isBold();
			String color = chatPanel.getWebColor();
			boolean italic = chatPanel.isItalic();
			String fontName = chatPanel.getFontName();
			int fontSize = chatPanel.getFontSize();
			Content content = ContentUtil.getContent(html, fontName, fontSize, color, underline, bold, italic);
			// String receiveUserId = chatPanel.getAttribute("userId");
			if (null != content) {
				chatPanel.initializeWriteHtml();

				String name = sendUser.getNickname();
				String time = OnlyDateUtil.getCurrentDateTime();
				String nameColor = ColorUtil.getColorInHexFromRGB(32, 143, 62);

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						content.setTimestamp(System.currentTimeMillis());
						String orientation = "right";
						String headPath = "file:/" + new File("Resources/Images/Head/User/" + 1 + ".png").getAbsolutePath();

						insertShow(chatPanel, name, headPath, time, orientation, content); // 143,
					}
				});
			}
		}
	}

	private void sendGroupMessage(String key) {
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
			// String groupId = chatPanel.getAttribute("groupId");
			if (null != content) {
				chatPanel.initializeWriteHtml();

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

	private String getUserKey(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("user_");
		sb.append(userId);
		return sb.toString();
	}

	private String getGroupKey(String groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append("group_");
		sb.append(groupId);
		return sb.toString();
	}

	private void setGroupUserListPane(ChatPanel item, String groupId) {
		ListRootPanel groupUserList = groupUserListMap.get(groupId);
		if (null == groupUserList) {
			updateGroupUserList(groupId);
		} else {
			if (null != item) {
				item.setRightPane(groupUserList);
			}
		}
	}

	public void setGroupUserList(final String groupId) {

	}

	public void updateGroupUserList(final String groupId) {

	}

	private class ChatItemGroupSendEvent implements EventHandler<ActionEvent> {

		String key;

		public ChatItemGroupSendEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			sendGroupMessage(key);
		}
	}

	private class ChatItemUserSendEvent implements EventHandler<ActionEvent> {

		String key;

		public ChatItemUserSendEvent(String key) {
			this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			sendUserMessage(key);
		}
	}

	private class ChatItemCloseEvent implements EventHandler<ActionEvent> {

		// String key;

		public ChatItemCloseEvent(String key) {
			// this.key = key;
		}

		@Override
		public void handle(ActionEvent event) {
			//wlf.show();

			//String url = this.getClass().getResource("/html/chat.html").toString();
			//wlf.load(url);
		}
	}

	private class ChatItemClickedEvent implements EventHandler<MouseEvent> {

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
	private void sendShake(String receiveId) {
		shake();// 自己执行抖动
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

	public void saveBufferedImage(ChatPanel cp, BufferedImage image) {
		String fileName = dateFormat.format(new Date()).toString() + ".jpg";

		String path = "images/" + fileName;
		try {
			OnlyFileUtil.checkOrCreateFolder(path);
			File imageFile = new File(path);
			ImageIO.write(image, "jpg", imageFile);
			String fullPath = imageFile.getAbsolutePath();
			cp.insertImage("", "image", fullPath, fullPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getPicture() {
		String fullPath = null;
		File file = imageFileChooser.showOpenDialog(chatListFrame);
		if (file != null) {
			if (file.exists()) {
				fullPath = file.getAbsolutePath();
			}
		}
		return fullPath;
	}

	public void insertFace(ChatPanel cp, FaceInfo fd) {
		//String path = fd.getHoverPath();
		String categoryId = fd.getCategoryId();
		String key = fd.getKey();

		// String path = "Resources/Images/Face/" + value + ".gif ";
		//File file = new File(path);
//		if (file.exists()) {
//			String fullPath = file.getAbsolutePath();
//			String v = categoryId + "," + key;
//			// String v = "classical" + "," + value;
//			cp.insertImage("", "face", v, fullPath);
//		}
	}
	// private void insertFace(ChatPanel cp, String value) {
	// String path = "Resources/Images/Face/" + value + ".gif ";
	// File file = new File(path);
	// if (file.exists()) {
	// String fullPath = file.getAbsolutePath();
	// String v = "classical" + "," + value;
	// cp.insertImage("", "face", v, fullPath);
	// }
	// }
}
