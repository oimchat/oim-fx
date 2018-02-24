/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author XiaHui
 */
public class HeadItem extends HBox {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private final HeadImageItemPane headPane = new HeadImageItemPane();

	private final AnchorPane headShowPane = new AnchorPane();

	private final VBox textBaseRootPane = new VBox();
	private final HBox textRootPane = new HBox();
	
	private final StackPane infoRootPane = new StackPane();
	private final HBox timePane = new HBox();
	private final HBox infoPane = new HBox();
	private final HBox businessPane = new HBox();

	
	private final Label timeLabel = new Label();
	
	private final Label remarkLabel = new Label();
	private final Label nicknameLabel = new Label();
	private final Label numberLabel = new Label();
	private final Label statusLabel = new Label();
	private final Label textLabel = new Label();

	private String remark;
	private String nickname;
	private String status;
	private String showText;
	private final Timeline animation = new Timeline();
	boolean pulse = false;

	public HeadItem() {
		initComponent();
		iniEvent();
		// iniTest();
	}

	private void initComponent() {
		this.getStyleClass().add("list-head-item");

		headPane.setHeadSize(40);
		headPane.setHeadRadius(40);

		this.getChildren().add(headShowPane);
		this.getChildren().add(textBaseRootPane);

		headShowPane.setPrefWidth(60);
		headShowPane.setPrefHeight(52);
		headShowPane.getChildren().add(headPane);
		
		headPane.setLayoutX(5);
		headPane.setLayoutY(3);
		
		timePane.setAlignment(Pos.CENTER_RIGHT);
		
		timePane.getChildren().add(timeLabel);

		textBaseRootPane.getChildren().add(getGapNode(12));
		textBaseRootPane.getChildren().add(infoRootPane);
		textBaseRootPane.getChildren().add(textRootPane);
		// textBaseRootPane.getChildren().add(getGapNode(5));
		
		infoRootPane.getChildren().add(infoPane);
		infoRootPane.getChildren().add(timePane);
		
		infoPane.getChildren().add(remarkLabel);
		infoPane.getChildren().add(nicknameLabel);
		infoPane.getChildren().add(numberLabel);
		infoPane.getChildren().add(statusLabel);

		textRootPane.getChildren().add(businessPane);
		textRootPane.getChildren().add(textLabel);
		
		timeLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");

		remarkLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:13px;");
		nicknameLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		numberLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		statusLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		textLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");

	}

	private void iniEvent() {
		this.setOnMouseClicked((Event event) -> {
			if (!HeadItem.this.isFocused()) {
				HeadItem.this.requestFocus();
			}
		});
		// animation.setAutoReverse(true);
		animation.setCycleCount(Animation.INDEFINITE);

		KeyValue kx1 = new KeyValue(headPane.layoutXProperty(), headPane.getLayoutX() + 1);
		KeyValue ky1 = new KeyValue(headPane.layoutYProperty(), headPane.getLayoutY() - 1);

		KeyValue kx2 = new KeyValue(headPane.layoutXProperty(), headPane.getLayoutX() + 1);
		KeyValue ky2 = new KeyValue(headPane.layoutYProperty(), headPane.getLayoutY() + 1);

		KeyValue kx3 = new KeyValue(headPane.layoutXProperty(), headPane.getLayoutX() - 1);
		KeyValue ky3 = new KeyValue(headPane.layoutYProperty(), headPane.getLayoutY() - 1);

		KeyValue kx4 = new KeyValue(headPane.layoutXProperty(), headPane.getLayoutX() - 1);
		KeyValue ky4 = new KeyValue(headPane.layoutYProperty(), headPane.getLayoutY() + 1);

		// KeyValue kx5 = new KeyValue(pane.layoutXProperty(),
		// pane.getLayoutX());
		// KeyValue ky5 = new KeyValue(pane.layoutYProperty(),
		// pane.getLayoutY());

		KeyFrame kfx1 = new KeyFrame(new Duration(160), kx1);
		KeyFrame kfy1 = new KeyFrame(new Duration(160), ky1);
		KeyFrame kfx2 = new KeyFrame(new Duration(320), kx2);
		KeyFrame kfy2 = new KeyFrame(new Duration(320), ky2);
		KeyFrame kfx3 = new KeyFrame(new Duration(480), kx3);
		KeyFrame kfy3 = new KeyFrame(new Duration(480), ky3);
		KeyFrame kfx4 = new KeyFrame(new Duration(640), kx4);
		KeyFrame kfy4 = new KeyFrame(new Duration(640), ky4);
		// KeyFrame kfx5 = new KeyFrame(new Duration(250), kx5);
		// KeyFrame kfy5 = new KeyFrame(new Duration(250), ky5);

		// KeyFrame k2 = new KeyFrame(new Duration(460), kx2);
		// KeyFrame k3 = new KeyFrame(new Duration(360), kx3);
		// KeyFrame k4 = new KeyFrame(new Duration(460), kx4);
		// KeyFrame k5 = new KeyFrame(new Duration(460), kx4);
		//
		// KeyFrame k1 = new KeyFrame(new Duration(360), kx1);
		// KeyFrame k2 = new KeyFrame(new Duration(460), kx2);
		// KeyFrame k3 = new KeyFrame(new Duration(360), kx3);
		// KeyFrame k4 = new KeyFrame(new Duration(460), kx4);
		// KeyFrame k5 = new KeyFrame(new Duration(460), kx4);
		animation.getKeyFrames().add(kfx1);
		animation.getKeyFrames().add(kfy1);
		animation.getKeyFrames().add(kfx2);
		animation.getKeyFrames().add(kfy2);
		animation.getKeyFrames().add(kfx3);
		animation.getKeyFrames().add(kfy3);
		animation.getKeyFrames().add(kfx4);
		animation.getKeyFrames().add(kfy4);
		// animation.getKeyFrames().add(kfx5);
		// animation.getKeyFrames().add(kfy5);

		// animation.getKeyFrames().add(k3);
		// animation.getKeyFrames().add(k4);
		// animation.getKeyFrames().add(k5);
		//
		// KeyFrame k1 = new KeyFrame(new Duration(360), kx1, ky1 );
		// KeyFrame k2 = new KeyFrame(new Duration(560), kx2, ky2 );
		//// KeyFrame k2 = new KeyFrame(new Duration(460), kx2);
		//// KeyFrame k3 = new KeyFrame(new Duration(360), kx3);
		//// KeyFrame k4 = new KeyFrame(new Duration(460), kx4);
		//// KeyFrame k5 = new KeyFrame(new Duration(460), kx4);
		////
		//// KeyFrame k1 = new KeyFrame(new Duration(360), kx1);
		//// KeyFrame k2 = new KeyFrame(new Duration(460), kx2);
		//// KeyFrame k3 = new KeyFrame(new Duration(360), kx3);
		//// KeyFrame k4 = new KeyFrame(new Duration(460), kx4);
		//// KeyFrame k5 = new KeyFrame(new Duration(460), kx4);
		//
		// animation.getKeyFrames().add(k1);
		// animation.getKeyFrames().add(k2);
		//// animation.getKeyFrames().add(k3);
		//// animation.getKeyFrames().add(k4);
		//// animation.getKeyFrames().add(k5);
		// animation.play();
		// Runnable r = (new Runnable() {
		// @Override
		// public void run() {
		// while (true) {
		//
		// try {
		// pane.setLayoutX(pane.getLayoutX() - 1);
		// pane.setLayoutY(pane.getLayoutY() + 1);
		// Thread.sleep(160);
		// pane.setLayoutX(pane.getLayoutX() + 1);
		// pane.setLayoutY(pane.getLayoutY() - 1);
		// Thread.sleep(260);
		// pane.setLayoutX(pane.getLayoutX() + 1);
		// pane.setLayoutY(pane.getLayoutY() + 1);
		// Thread.sleep(160);
		// pane.setLayoutX(pane.getLayoutX() - 1);
		// pane.setLayoutY(pane.getLayoutY() - 1);
		// Thread.sleep(260);
		// } catch (InterruptedException ex) {
		// Logger.getLogger(HeadItem.class.getName()).log(Level.SEVERE, null,
		// ex);
		// }
		// }
		// }
		// });
		// new Thread(r).start();

		// headImagePane.setOnMouseClicked((MouseEvent me) -> {
		// if (me.getClickCount() == 2) {
		// if (animation.getStatus() == Animation.Status.RUNNING) {
		// animation.stop();
		// } else {
		// animation.play();
		// }
		// }
		// });
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setHeadImage(Image image) {
		headPane.setHeadImage(image);
	}

	public boolean isGray() {
		return headPane.isGray();
	}

	public void setGray(boolean gray) {
		headPane.setGray(gray);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
		remarkLabel.setText(remark);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		nicknameLabel.setText(nickname);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		statusLabel.setText(status);
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
		textLabel.setText(showText);
	}
	
	public void setTime(String time) {
		this.timeLabel.setText(time);
	}

	public void setPulse(boolean pulse) {
		this.pulse = pulse;
		if (!pulse) {
			if (animation.getStatus() == Animation.Status.RUNNING) {
				animation.stop();
			}
		} else {
			animation.play();
		}
	}

	public boolean isPulse() {
		return pulse;
	}

	public void addBusinessIcon(Node node) {
		businessPane.getChildren().add(node);
	}

	public void removeBusinessIcon(Node node) {
		businessPane.getChildren().remove(node);
	}
	
	public void setHeadSize(double value) {
		headPane.setHeadSize(value);
		headPane.setHeadRadius(value);
	}

	
	public AnchorPane getHeadShowPane() {
		return headShowPane;
	}

	public HeadImageItemPane getHeadPane() {
		return headPane;
	}

	private Node getGapNode(double value) {
		AnchorPane tempPane = new AnchorPane();
		tempPane.setPrefWidth(value);
		tempPane.setPrefHeight(value);
		tempPane.setBackground(Background.EMPTY);
		return tempPane;
	}
	
	public void setRedImage(Image image) {
		headPane.setRedImage(image);
	}
}
