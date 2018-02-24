/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics.component;

import com.oim.ui.fx.classics.ClassicsStage;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class MenuFrame extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();
	//StatusPopupMenu menu = new StatusPopupMenu();

	public MenuFrame() {
		init();
	}

	private void init() {
		this.getScene().getStylesheets().add(this.getClass().getResource("/classics/css/base.css").toString());
		this.getRootPane().getStylesheets().add(this.getClass().getResource("/classics/css/base.css").toString());
		
		
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		VBox topBox = new VBox();
		topBox.setPrefHeight(50);
		rootPane.setTop(topBox);
		BorderPane borderPane = new BorderPane();

		rootPane.setCenter(borderPane);

		HBox hBox = new HBox();

		borderPane.setTop(hBox);
		borderPane.setCenter(componentBox);

		componentBox.setOnContextMenuRequested(a -> {
			///menu.show(MenuFrame.this, a.getX(), a.getY());
		});
	}
}
