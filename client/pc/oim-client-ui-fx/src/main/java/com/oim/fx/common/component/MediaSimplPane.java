package com.oim.fx.common.component;

import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * 
 * 简单视频播放面版
 * 
 * @author: XiaHui
 * @date: 2017年5月23日 下午2:21:40
 */
public class MediaSimplPane extends StackPane {

	private MediaPlayer mediaPlayer;
	private MediaView mediaView;

	public MediaSimplPane() {
		initPanel();
	}

	private void initPanel() {
		mediaView = new MediaView();
		getChildren().add(mediaView);
	}

	/**
	 * 设置播放视频文件地址<br>
	 * http地址直接使用<br>
	 * 本地视频需要用File("path").toURI().toURL().toString()
	 * 
	 * @author: XiaHui
	 * @param url
	 * @createDate: 2017年5月25日 下午6:03:14
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:03:14
	 */
	public void setUrl(String url) {
		mediaPlayer = new MediaPlayer(new Media(url));
		mediaPlayer.setAutoPlay(true);

		mediaPlayer.setCycleCount(-1);
		mediaView.setMediaPlayer(mediaPlayer);
	}

	/**
	 * 设置播放面板尺寸
	 * 
	 * @author: XiaHui
	 * @param prefWidth
	 * @param prefHeight
	 * @createDate: 2017年5月25日 下午6:05:00
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:05:00
	 */
	public void setMediaSize(double prefWidth, double prefHeight) {
		setPrefWidth(prefWidth);
		setPrefHeight(prefHeight);
		mediaView.relocate(0, 0);
		mediaView.setFitWidth(this.getWidth());
		mediaView.setFitHeight(this.getHeight());
	}

	@Override
	protected void layoutChildren() {
		mediaView.setFitWidth(this.getWidth());
		mediaView.setFitHeight(this.getHeight());
	}

	/**
	 * 停止播放
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年5月25日 下午6:05:31
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:05:31
	 */
	public void stop() {
		if (null != mediaPlayer) {
			mediaPlayer.stop();
		}
	}

	/**
	 * 开始播放
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年5月25日 下午6:05:46
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:05:46
	 */
	public void play() {
		if (null != mediaPlayer) {
			mediaPlayer.play();
		}
	}
}
