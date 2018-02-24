/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics.component;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.ImageNode;
import com.oim.ui.fx.classics.ClassicsStage;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author XiaHui
 */
public class HeadImageFrame extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();

	public HeadImageFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		VBox topBox = new VBox();
		topBox.setPrefHeight(50);
		rootPane.setTop(topBox);

		Image image = ImageBox.getImageClassPath("/images/head/101_100.gif");

		ImageNode imageButton = new ImageNode();

		imageButton.setImage(image);
		imageButton.setImageSize(80);
		imageButton.setImageRadius(20);
		imageButton.setGray(true);
		imageButton.setEffect(new DropShadow(20, Color.BLACK));

		ImageView imageView = new ImageView();
		imageView.setImage(image);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(imageButton);
		stackPane.setStyle("-fx-background-color:rgba(150,120,210,1)");

		rootPane.setCenter(stackPane);
	}
}
