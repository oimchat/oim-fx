/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.io.File;
import java.net.MalformedURLException;

import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.MediaSimplPane;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Only
 */
public class MediaPanelFrame extends BaseStage {

	VBox box = new VBox();
	MediaSimplPane mp = new MediaSimplPane();
	
	public MediaPanelFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("分页");
		this.setWidth(440);
		this.setHeight(360);
		this.setCenter(box);

		// box.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");
		mp.setMaxSize(320, 240);
		mp.setPrefWidth(320);
		mp.setPrefHeight(240);

		box.getChildren().add(mp);

		File file = new File("Resources/Video/login.mp4");
		try {
			String pathString = file.toURI().toURL().toString();
			mp.setUrl(pathString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HBox hBox = new HBox();

		Button stop = new Button();
		stop.setText("停止");
		Button play = new Button();
		play.setText("播放");

		hBox.getChildren().add(stop);
		hBox.getChildren().add(play);
		
		stop.setOnAction(a -> {
			mp.stop();
		});
		play.setOnAction(a -> {
			mp.play();
		});
		
		box.getChildren().add(hBox);
	}

	private void initEvent() {

	}
}
