/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.common.component.image;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.ClassicsStage;
import com.only.fx.common.component.OnlyContextMenu;
import com.only.fx.common.component.OnlyMenuItem;

import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ContextMenuStage extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	Button button=new Button("菜单");
	
	OnlyContextMenu menu=new OnlyContextMenu();
	public ContextMenuStage() {
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
		
		HBox hBox=new HBox();
		hBox.getChildren().add(button);
		
		rootPane.setTop(topBox);
		rootPane.setBottom(hBox);
		
		button.setOnAction(a->{
			menu.show(button, Side.TOP, button.getLayoutX(), button.getLayoutY());
		});
	}
	
	private void initData(){
		 SeparatorMenuItem separator2 = new SeparatorMenuItem();
		
		Image onlineImage = ImageBox.getImageClassPath("/common/images/status/flag/big/imonline.png");
		ImageView onlineImageView = new ImageView();
		onlineImageView.setImage(onlineImage);
		HBox hBox=new HBox();
		hBox.setPadding(new Insets(0, 0, 0, 10));
		hBox.getChildren().add(onlineImageView);
		
		OnlyMenuItem item=new OnlyMenuItem("测试1");
		
		menu.getItems().add(item);
		item=new OnlyMenuItem("测试1");
		menu.getItems().add(item);
		//item.setGraphic(hBox);
		
		item=new OnlyMenuItem("测试3");
		menu.getItems().add(item);
		
		menu.getItems().add(separator2);
		
		item=new OnlyMenuItem("测试1");
		menu.getItems().add(item);
		
		//System.out.println(item);
	}
}
