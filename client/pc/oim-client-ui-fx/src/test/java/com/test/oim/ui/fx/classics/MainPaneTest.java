/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.fx.common.component.IconPane;
import com.oim.ui.fx.classics.ClassicsStage;
import com.oim.ui.fx.classics.chat.LastItem;
import com.oim.ui.fx.classics.list.HeadItem;
import com.oim.ui.fx.classics.list.ListNodePane;
import com.oim.ui.fx.classics.list.ListRootPane;
import com.oim.ui.fx.classics.main.MainPane;
import com.only.fx.common.component.choose.ChooseGroup;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class MainPaneTest extends Application {

	ClassicsStage stage = new ClassicsStage();
	MainPane mainPane = new MainPane();
	ListRootPane userList = new ListRootPane();
	ListRootPane groupList = new ListRootPane();
	ListRootPane lastList = new ListRootPane();
	@Override
	public void start(Stage primaryStage) {
		stage.setCenter(mainPane);
		
		initUser();
		initMainPane();
		initListPane();
		initList();
		stage.show();
	}

	private void initUser() {
		Image statusImage = ImageBox.getImageClassPath("/resources/common/images/status/online.png");
		Image headImage = ImageBox.getImageClassPath("/images/head/101_100.gif");
		mainPane.setHeadImage(headImage);
		mainPane.setStatusImage(statusImage);
		mainPane.setNickname("瓦沙啥");
		mainPane.setText("好多想买的东西，也就只能是想买的东西了。");
	}

	private void initMainPane() {
		IconPane iconButton = null;

		///////////////////////////////////////////// function
		Image normalImage = ImageBox.getImageClassPath("/images/classics/main/bottom/message_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/images/classics/main/bottom/message_hover.png");
		Image pressedImage = ImageBox.getImageClassPath("/images/classics/main/bottom/message_down.png");

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		iconButton = new IconPane(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		iconButton = new IconPane("查找", normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(iconButton);

		IconButton ib = new IconButton(normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(ib);

		ib = new IconButton("皮肤", normalImage, hoverImage, pressedImage);
		mainPane.addFunctionIcon(ib);
	}

	private void initListPane() {
		Image normalImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_contacts_normal.png");
		Image hoverImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_contacts_hover.png");
		Image selectedImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_contacts_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, userList);

		normalImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_group_normal.png");
		hoverImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_group_hover.png");
		selectedImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_group_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, groupList);

		normalImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_last_normal.png");
		hoverImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_last_hover.png");
		selectedImage = ImageBox.getImageClassPath("/images/classics/main/tab/icon_last_selected.png");

		mainPane.addTab(normalImage, hoverImage, selectedImage, lastList);
	}

	private void initList() {
		Image headImage = ImageBox.getImageClassPath("/images/head/101_100.gif");
		ChooseGroup chooseGroup = new ChooseGroup();

		for (int j = 0; j < 5; j++) {
			ListNodePane node = new ListNodePane();
			node.setText("我的好友" + j);

			for (int i = 0; i < 5; i++) {
				HeadItem head = new HeadItem();
				head.setHeadImage(headImage);
				head.setRemark("女神经" + (j + 1));
				head.setNickname("(哈加额)");
				head.setStatus("[2G]");
				head.setShowText("哈哈哈，又有新闻了");
				head.setChooseGroup(chooseGroup);
				head.setOnMouseClicked(m -> {
					if (m.getClickCount() == 2) {
						head.setPulse(!head.isPulse());
					}
				});

				node.addItem(head);
			}
			userList.addNode(node);
		}

		for (int j = 0; j < 5; j++) {
			ListNodePane node = new ListNodePane();
			node.setText("我的群" + j);
			node.setNumberText("[5]");

			for (int i = 0; i < 5; i++) {
				HeadItem head = new HeadItem();
				head.setHeadImage(headImage);
				head.setRemark("女神经" + (j + 1));
				head.setNickname("(哈加额)");
				head.setStatus("[2G]");
				head.setShowText("哈哈哈，又有新闻了");
				head.setChooseGroup(chooseGroup);
				head.setOnMouseClicked(m -> {
					if (m.getClickCount() == 2) {
						head.setPulse(!head.isPulse());
					}
				});

				node.addItem(head);
			}
			groupList.addNode(node);
		}
		String text = "好傻水水水水？【】[图片]";
		for (int j = 0; j < 15; j++) {

			LastItem head = new LastItem();
			head.setHeadImage(headImage);
			head.setName("女神经" + (j + 1));
			head.setText(text);
			head.setTime("12:20");

			head.setChooseGroup(chooseGroup);

			head.setOnMouseClicked(m -> {
				boolean red = !head.isRed();
				String redText = red ? "5" : "";
				head.setRed(red);
				head.setRedText(redText);
			});
			lastList.addNode(head);
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
