/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;
import com.oim.fx.ui.chat.pane.MessageItemLeft;
import com.oim.fx.ui.chat.pane.MessageItemRight;
import com.oim.fx.ui.chat.pane.NodeChatPane;
import com.only.common.util.OnlyDateUtil;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Only
 */
public class ImageTextPaneFrame extends BaseStage {
	NodeChatPane nodeChatPane = new NodeChatPane();
	BorderPane rootPane = new BorderPane();
	public ImageTextPaneFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("登录");
		this.setWidth(340);
		this.setHeight(560);
		this.setCenter(rootPane);

		VBox b = new VBox();
		b.setPrefHeight(40);

		
		rootPane.setTop(b);
		rootPane.setCenter(nodeChatPane);
		rootPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");
	}

	private void initEvent() {
		nodeChatPane.getSendButton().setOnAction(a -> {
			send();
		});
	}

	int i = 0;

	private void send() {
		String time = OnlyDateUtil.getCurrentTime();
		Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + 1 + ".png", 34, 34, 8, 8);
		String c = nodeChatPane.getTextArea().getText();
		TextFlow textFlow = new TextFlow();

		Text text = new Text(c);
		text.setFont(Font.font("微软雅黑", FontPosture.ITALIC, 12));
		ImageView imageView = new ImageView(image);
		textFlow.getChildren().addAll(text, imageView);
		String name = "sasassa哈哈";

		if (i % 2 > 0) {
			MessageItemLeft mil = new MessageItemLeft();
			mil.setHeadImage(image);
			mil.setTimeText(time);
			mil.setName(name);
			mil.addContentNode(textFlow);
			nodeChatPane.getMessageList().addNode(mil);

		} else {
			MessageItemRight mil = new MessageItemRight();
			mil.setHeadImage(image);
			mil.setName(name);
			mil.setTimeText(time);
			mil.addContentNode(textFlow);
			nodeChatPane.getMessageList().addNode(mil);
		}
		i++;
	}
}
