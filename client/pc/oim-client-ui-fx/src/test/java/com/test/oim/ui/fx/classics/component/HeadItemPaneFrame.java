/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics.component;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.ClassicsStage;
import com.oim.ui.fx.classics.list.HeadItem;
import com.only.fx.common.component.choose.ChooseGroup;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class HeadItemPaneFrame extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();

	public HeadItemPaneFrame() {
		init();
	}

	private void init() {

		this.getRootPane().getStylesheets().add(this.getClass().getResource("/multiple/css/main.css").toString());

		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		VBox topBox = new VBox();
		topBox.setPrefHeight(50);
		rootPane.setTop(topBox);

		rootPane.setCenter(componentBox);

		Image image = ImageBox.getImageClassPath("/images/head/101_100.gif");
		ChooseGroup chooseGroup = new ChooseGroup();
		for (int i = 0; i < 5; i++) {
			String text = "好傻水水水水？【】[图片]";

			String name = "(恢复大师)";

			HeadItem head = new HeadItem();
			head.setHeadImage(image);
			
			head.setRemark("张思");
			head.setNickname(name);
			head.setShowText(text);
			head.setTime("12:20");

			head.setChooseGroup(chooseGroup);

			head.setOnMouseClicked(m -> {
				boolean pulse = !head.isPulse();
				head.setPulse(pulse);
				chooseGroup.selectedChoose(head);
			});
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					componentBox.getChildren().add(head);
				}
			});
		}
	}
}
