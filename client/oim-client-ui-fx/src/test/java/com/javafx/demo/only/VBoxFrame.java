/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.only;

import com.only.fx.OnlyFrame;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class VBoxFrame extends OnlyFrame {

	
	HBox checkBoxVBox = new HBox();
	HBox scrollPaneVBox = new HBox();
	HBox textFieldVBox = new HBox();
	BorderPane rootPane = new BorderPane();

	public VBoxFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		VBox rootVBox = new VBox();
		rootVBox.setPrefHeight(30);
		rootVBox.setStyle("-fx-background-color:#26292e");
		
		rootPane.setPadding(new Insets(30, 0, 0, 0));
		rootPane.setTop(rootVBox);

		Button button1=new Button("1");
		StackPane stackPane1 = new StackPane();
		stackPane1.getChildren().add(button1);
		
		Button button2=new Button("2");
		StackPane stackPane2 = new StackPane();
		stackPane2.getChildren().add(button2);
		
		Button button3=new Button("3");
		StackPane stackPane3 = new StackPane();
		stackPane3.getChildren().add(button3);
		
		HBox topHBox = new HBox();
		topHBox.setStyle("-fx-background-color:rgba(194, 194, 173, 1)");
		topHBox.getChildren().add(stackPane1);
		topHBox.getChildren().add(stackPane2);
		topHBox.getChildren().add(stackPane3);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topHBox);
		
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(borderPane);
		
		rootPane.setCenter(stackPane);
	}
}
