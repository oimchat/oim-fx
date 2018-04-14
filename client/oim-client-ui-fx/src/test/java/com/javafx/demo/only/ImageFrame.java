/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.only;

import com.only.fx.OnlyFrame;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ImageFrame extends OnlyFrame {

	VBox rootVBox = new VBox();

	BorderPane rootPane = new BorderPane();
	
	public ImageFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);
		
		Image image=new Image("com/javafx/demo/only/wallpaper1.jpg");
		ImageView iv=new ImageView(image);
		
		
		rootPane.setPadding(new Insets(30, 0, 0, 0));
		rootPane.getChildren().add(rootVBox);
		
		Button button=new Button("",iv);
		rootVBox.getChildren().add(button);
	}
}
