/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.test.control;

import com.oim.fx.common.component.ScreenShotFrame;
import com.only.fx.OnlyFrame;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ScreenShotFrameShowFrame extends OnlyFrame {

	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();
	ScreenShotFrame ss=new ScreenShotFrame();
	
	public ScreenShotFrameShowFrame() {
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
		rootPane.setCenter(componentBox);

		Button b=new Button("截屏");
		componentBox.getChildren().add(b);
		
		b.setOnAction(a->{
			ss.setVisible(true);
		});
	}
}
