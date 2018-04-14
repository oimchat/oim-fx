/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.common.component.image;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.ClassicsStage;

import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ImageCropperStage extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	ImageCropperPane imageCropperPane = new ImageCropperPane();
	Slider slider = new Slider();
	
	public ImageCropperStage() {
		init();
		initData();
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
		rootPane.setCenter(imageCropperPane);
		
		rootPane.setRight(slider);
		
		slider.setValue(50);
		slider.setOrientation(Orientation.VERTICAL);
		
		imageCropperPane.setMaxWidth(350);
	}
	
	private void initData(){
		Image image=ImageBox.getImageClassPath("/images/cropper/2.jpg");
		
		imageCropperPane.setImage(image);
	}
}
