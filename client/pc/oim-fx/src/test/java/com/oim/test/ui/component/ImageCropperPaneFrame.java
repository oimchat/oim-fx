/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.image.ImageCropperPane;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Only
 */
public class ImageCropperPaneFrame extends BaseStage {

	ImageCropperPane mp = new ImageCropperPane();
	BorderPane rootPane = new BorderPane();

	Button button=new Button("确定");
	
	ScrollPane scrollPane = new ScrollPane();

	StackPane imagePane = new StackPane();
	ImageView imageView = new ImageView();
	
	public ImageCropperPaneFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("分页");
		this.setWidth(440);
		this.setHeight(460);
		this.setCenter(rootPane);

		HBox box = new HBox();
		box.setPrefHeight(50);
		box.setMaxHeight(50);
		
		box.getChildren().add(button);
		rootPane.setTop(box);
		
		VBox vBox = new VBox();
		vBox.getChildren().add(mp);
		//vBox.getChildren().add(scrollPane);
		
		rootPane.setCenter(vBox);

		Image i=new	Image(getClass().getResource("/resources/common/images/cropper/CircleMask.png").toExternalForm());
		mp.setCoverImage(i);
		
		Image image = ImageBox.getImagePath("Resources/Temp/1.jpg");
		mp.setImage(image);
		rootPane.setPrefSize(120, 140);
		rootPane.setMaxSize(120, 140);
		mp.setMaxSize(120, 140);
		
		//imageView.setPreserveRatio(true);
		imagePane.getChildren().add(imageView);
		scrollPane.setContent(imagePane);
		scrollPane.setPrefSize(120, 140);
		rootPane.setBottom(scrollPane);
	}

	private void initEvent() {
		button.setOnAction(a->{
			Image image=mp.getImage();
			imageView.setImage(image);
		});
	}
}
