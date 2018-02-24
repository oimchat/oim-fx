/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.ChatListStage;
import com.oim.ui.fx.classics.chat.ChatItem;
import com.oim.ui.fx.classics.chat.ChatPane;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class ChatListStageViewTest extends Application {

	ChatListStage stage = new ChatListStage();
	ChatPane cp=new ChatPane();
	@Override
	public void start(Stage primaryStage) {
		stage.show();
		stage.getRootPane().getStylesheets().add(this.getClass().getResource("/classics/css/chat.css").toString());
		//stage.getRootPane().getStylesheets().add(this.getClass().getResource("/multiple/css/main.css").toString());
		initList();
		stage.setChatPane(cp);
		
		cp.setName("脑门的");
		cp.setText("ddddddddddddsds");
		
		cp.addTopTool(new Button("X"));
		
		stage.setBackgroundColor(Color.BLUE);
		
	}

	public void initList() {
		Image image = ImageBox.getImageClassPath("/images/head/101_100.gif");
		for (int i = 0; i < 50; i++) {
			String text = "好傻水水水水？【】[图片]";

			String name = "恢复大师";

			ChatItem head = new ChatItem();
			head.setHeadImage(image);
			head.setName(name);
			head.setText(text);
			head.setTime("12:20");
			String key = i + "";
			head.setOnMouseClicked(m -> {

				boolean red = !head.isRed();
				String redText = red ? "5" : "";
				head.setRed(red);
				head.setRedText(redText);
			});
			
			head.setOnCloseAction(a->{
				stage.remove(key);
			});
			
			VBox v = new VBox();
			v.getChildren().add(new Button(key));
			stage.addItem(key, head, v);
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
