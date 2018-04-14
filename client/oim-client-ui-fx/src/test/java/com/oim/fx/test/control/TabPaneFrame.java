/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.test.control;

import com.only.fx.OnlyFrame;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class TabPaneFrame extends OnlyFrame {

	BorderPane rootPane = new BorderPane();
	TabPane tp=new TabPane();

	public TabPaneFrame() {
		init();
		getRootPane().getStylesheets().add(this.getClass().getResource("/resources/common/css/base.css").toString());
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
		rootPane.setCenter(tp);
		
		Tab tab;
		
		tab=new Tab();
		tab.setText("001");
		tab.setContent(new BorderPane());
		tp.getTabs().add(tab);
		
		tab=new Tab();
		tab.setText("002");
		tab.setContent(new BorderPane());
		tp.getTabs().add(tab);
		
		tab=new Tab();
		tab.setText("003");
		tab.setContent(new BorderPane());
		tp.getTabs().add(tab);
	}
}
