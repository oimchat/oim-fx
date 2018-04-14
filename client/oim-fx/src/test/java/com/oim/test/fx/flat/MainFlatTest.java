/*
 * To change mainFrame license header, choose License Headers in Project Properties.
 * To change mainFrame template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.fx.flat;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconPane;
import com.oim.fx.common.component.IconToggleButton;
import com.oim.fx.ui.chat.SimpleChatPanel;
import com.oim.fx.ui.list.HeadItem;
import com.oim.fx.ui.list.ListRootPanel;
import com.oim.ui.fx.bine.main.ItemListPane;
import com.oim.ui.fx.bine.main.MainPane;
import com.oim.ui.fx.flat.MainFlat;
import com.oim.ui.fx.flat.main.ChatTopPane;
import com.oim.ui.fx.flat.main.MainFlatPane;
import com.onlyxiahui.im.bean.UserData;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class MainFlatTest extends Application {

	MainFlat flat = new MainFlat();
	MainFlatPane mainFlatPane = new MainFlatPane();

	ItemListPane userListPane = new ItemListPane();
	ListRootPanel userFindPane = userListPane.getFindListPane();
	ListRootPanel userRoot = userListPane.getListRootPane();
	ListRootPanel lastRoot = new ListRootPanel();

	MainPane userMainPane = new MainPane();
	MainPane lastMainPane = new MainPane();

	BorderPane chatBorderPane = new BorderPane();
	ChatTopPane chatTopPane = new ChatTopPane();

	protected Map<String, SimpleChatPanel> chatPanelMap = new ConcurrentHashMap<String, SimpleChatPanel>();

	@Override
	public void start(Stage primaryStage) {
		flat.show();
		flat.setCenter(mainFlatPane);

		flat.getScene().getStylesheets().add(this.getClass().getResource("/flat/css/flat.css").toString());

		initOwn();
		initTest();
		initUserList();

		chatTopPane.setPrefHeight(50);
		chatBorderPane.setTop(chatTopPane);
		
		
		lastMainPane.setCenter(chatBorderPane);

		chatTopPane.setStyle("-fx-background-color:rgba(240, 243, 246, 1)");
		chatTopPane.setPadding(new Insets(15, 0, 0, 20));
		// chatTopPane.getLabel().setFont(Font.font("微软雅黑", 14));
		chatTopPane.getLabel().setStyle("-fx-font-size:19px;-fx-text-fill:rgba(50, 50, 50, 1);");
	}

	private void initOwn() {
		Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + 4 + ".png");
		mainFlatPane.setHeadImage(image);
	}

	/**
	 * 测试数据
	 */
	private void initTest() {
		// stage.setBackgroundColor(Color.rgb(245,245,245,1));

		userFindPane.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");

		userListPane.getBorderPane().setStyle("-fx-background-color:rgba(246, 246, 246, 1)");

		lastMainPane.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");

		lastRoot.setStyle("-fx-background-color:rgba(246, 246, 246, 1)");
		userRoot.setStyle("-fx-background-color:rgba(246, 246, 246, 1)");

		userMainPane.setLeftWidth(230);
		lastMainPane.setLeftWidth(230);

		userMainPane.setLeftCenter(userListPane);
		lastMainPane.setLeftCenter(lastRoot);

		Image normalImage = ImageBox.getImageClassPath("/flat/images/main/tab/message_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/flat/images/main/tab/message_hover.png");
		Image selectedImage = ImageBox.getImageClassPath("/flat/images/main/tab/message_selected.png");
		IconToggleButton tb = new IconToggleButton(normalImage, hoverImage, selectedImage);
		mainFlatPane.addTab(tb, lastMainPane);
		tb.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					mainFlatPane.setTopText("消息列表");
				}
			}
		});

		normalImage = ImageBox.getImageClassPath("/flat/images/main/tab/user_normal.png");
		hoverImage = ImageBox.getImageClassPath("/flat/images/main/tab/user_hover.png");
		selectedImage = ImageBox.getImageClassPath("/flat/images/main/tab/user_selected.png");
		tb = new IconToggleButton(normalImage, hoverImage, selectedImage);
		mainFlatPane.addTab(tb, userMainPane);
		tb.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					mainFlatPane.setTopText("通讯录");
				}
			}
		});
		normalImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_normal.png");
		hoverImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_hover.png");
		selectedImage = ImageBox.getImageClassPath("/bine/main/images/tab/group_selected.png");

		// lastRoot = new ListRootPanel();
		// lastRoot.setStyle("-fx-background-color:rgba(144, 50, 59, 0.8)");
		// mainFlatPane.addTab(normalImage, hoverImage, selectedImage,
		// lastRoot);
		TextField textField = userListPane.getTextField();
		textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String text = textField.getText();
			}
		});

		textField.setPromptText("手机号/昵称");
		
		textField.setPrefWidth(210);
		textField.setPrefHeight(25);

		Image findImage = ImageBox.getImageClassPath("/flat/images/common/find_8x8.png");
		userListPane.setFindImage(findImage);
		// textField.setStyle("-fx-border-color: #666666;\r\n" +
		// " -fx-border-width: 1px;\r\n" +
		// " -fx-border-style: solid;");
	}

	private void initUserList() {

		Random random = new Random();

		for (int i = 0; i < 25; i++) {
			int index = random.nextInt(100) + 1;
			HeadItem head = new HeadItem();
			Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + index + ".png", 40, 40);
			head.setHeadImage(image);
			head.setRemark("女神经" + (i));
			// head.setNickname("(哈加额)");
			// head.setStatus("[2G]");
			// head.setShowText("哈哈哈，又有新闻了");
			UserData userData = new UserData();
			userData.setNickname("女神经" + (i));
			userData.setId(i + "");
			head.setOnMouseClicked(m -> {
				SimpleChatPanel cp = getRoomUserChatPanel(userData);
				chatBorderPane.setCenter(cp);
				chatTopPane.setText(userData.getNickname());
			});

			lastRoot.addNode(head);
		}
	}

	public SimpleChatPanel getRoomUserChatPanel(UserData userData) {
		String userId = userData.getId();
		String key = (userData.getId());
		SimpleChatPanel item = chatPanelMap.get(key);
		if (null == item) {
			item = new SimpleChatPanel();
			item.addAttribute("key", key);
			item.addAttribute("userId", userId);
			// item.setShowCloseButton(false);

			item.getMiddleBox().setPadding(new Insets(10, 0, 0, 20));

			Image normalImage = ImageBox.getImageClassPath("/flat/images/chat/middle/font_icon_n.png");
			Image hoverImage = ImageBox.getImageClassPath("/flat/images/chat/middle/font_icon_h.png");
			Image pressedImage = ImageBox.getImageClassPath("/flat/images/chat/middle/font_icon_p.png");
			item.setFontIconImage(normalImage, hoverImage, pressedImage);
			// 表情按钮
			normalImage = ImageBox.getImageClassPath("/flat/images/chat/middle/face_n.png");
			hoverImage = ImageBox.getImageClassPath("/flat/images/chat/middle/face_h.png");
			pressedImage = ImageBox.getImageClassPath("/flat/images/chat/middle/face_p.png");
			final IconPane ib = new IconPane(normalImage, hoverImage, pressedImage);
			item.addMiddleTool(ib);
			// 发送图片按钮
			normalImage = ImageBox.getImageClassPath("/flat/images/chat/middle/picture_n.png");
			hoverImage = ImageBox.getImageClassPath("/flat/images/chat/middle/picture_h.png");
			pressedImage = ImageBox.getImageClassPath("/flat/images/chat/middle/picture_p.png");

			IconPane iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			item.addMiddleTool(iconButton);

			// 截屏按钮
			normalImage = ImageBox.getImageClassPath("/flat/images/chat/middle/cut_n.png");
			hoverImage = ImageBox.getImageClassPath("/flat/images/chat/middle/cut_h.png");
			pressedImage = ImageBox.getImageClassPath("/flat/images/chat/middle/cut_p.png");

			iconButton = new IconPane(normalImage, hoverImage, pressedImage);
			item.addMiddleTool(iconButton);

			normalImage = ImageBox.getImageClassPath("/flat/images/chat/middle/history_n.png");
			hoverImage = ImageBox.getImageClassPath("/flat/images/chat/middle/history_n.png");
			pressedImage = ImageBox.getImageClassPath("/flat/images/chat/middle/history_p.png");

			iconButton = new IconPane("消息记录", normalImage, hoverImage, pressedImage);
			item.addMiddleRightTool(iconButton);

		}
		String nickname = userData.getNickname();
		// item.setText(nickname);
		return item;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
