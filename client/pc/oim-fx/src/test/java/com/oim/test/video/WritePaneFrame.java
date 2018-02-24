/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.video;

import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.MediaSimplPane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Only
 */
public class WritePaneFrame extends BaseStage {

	BorderPane rootPane = new BorderPane();

	VBox box = new VBox();

	public WritePaneFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("登录");
		this.setWidth(440);
		this.setHeight(360);
		this.setCenter(rootPane);
		initMediaPlayer();
		
	}

	private void initEvent() {
		
	}
	String MEDIA_URL = "rtmp://192.168.1.200:1935/oflaDemo/stream1517215039242";
	void initMediaPlayer() {
		MediaPlayer mediaPlayer = new MediaPlayer(new Media(MEDIA_URL));
		mediaPlayer.setAutoPlay(true);
		PlayerPane playerPane = new PlayerPane(mediaPlayer);
		
		MediaSimplPane m=new MediaSimplPane();
		m.setUrl(MEDIA_URL);
		m.play();
		box.getChildren().add(playerPane);
		m.setStyle("-fx-background-color:rgba(230, 230, 230, 1)");
		rootPane.setCenter(m);
	}
}
