/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.test.control;

import com.only.fx.OnlyFrame;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class BoxFrame extends OnlyFrame {

	BorderPane rootPane = new BorderPane();
	VBox vBox = new VBox();

	public BoxFrame() {
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
		rootPane.setCenter(vBox);

		Button b1=new Button("1");
		Button b2=new Button("2");
		
		HBox h1=new HBox();
		HBox h2=new HBox();
		
		h1.getChildren().add(b1);
		h2.getChildren().add(b2);
		
		HBox.setHgrow(b1, Priority.ALWAYS);
		HBox.setHgrow(b2, Priority.ALWAYS);

		vBox.getChildren().add(h1);
		vBox.getChildren().add(h2);
		
	}
}
