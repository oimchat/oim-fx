/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.list;

import java.util.HashMap;
import java.util.Map;

import com.oim.fx.common.component.ImageNode;
import com.only.fx.common.component.choose.ChoosePane;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 *
 * @author XiaHui
 */
public class HeadItem extends ChoosePane  {
	
	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private final BorderPane borderPane = new BorderPane();
	private final StackPane headStackPane = new StackPane();
	
	private final ImageNode headPane = new ImageNode();
	
	private final AnchorPane headShowPane = new AnchorPane();

	private final VBox textBaseVBox = new VBox();
	private final HBox textHBox = new HBox();

	private final StackPane infoStackPane = new StackPane();
	private final HBox timeHBox = new HBox();
	private final HBox infoHBox = new HBox();
	private final HBox businessHBox = new HBox();

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
	}

	private void initComponent() {
		
		this.getChildren().add(borderPane);
		this.getStyleClass().add("head-item");

		headPane.setImageSize(40);
		headPane.setImageRadius(40);

		borderPane.setLeft(headStackPane);
		borderPane.setCenter(textBaseVBox);
	

		headShowPane.setPrefWidth(60);
		headShowPane.setPrefHeight(60);
		headShowPane.getChildren().add(headPane);

		headPane.setLayoutX(10);
		headPane.setLayoutY(10);
		
		headStackPane.getChildren().add(headShowPane);
		headStackPane.getChildren().add(new StackPane());

		timeHBox.setAlignment(Pos.CENTER_RIGHT);
		timeHBox.getChildren().add(timeLabel);

		infoHBox.getChildren().add(remarkLabel);
		infoHBox.getChildren().add(nicknameLabel);
		infoHBox.getChildren().add(numberLabel);
		infoHBox.getChildren().add(statusLabel);
		
		infoStackPane.getChildren().add(infoHBox);
		infoStackPane.getChildren().add(timeHBox);

		textHBox.getChildren().add(businessHBox);
		textHBox.getChildren().add(textLabel);
		
		textBaseVBox.setAlignment(Pos.CENTER_LEFT);
		textBaseVBox.setPadding(new Insets(0, 0, 0, 0));
		textBaseVBox.getChildren().add(infoStackPane);
		textBaseVBox.getChildren().add(textHBox);

		timeLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");

		remarkLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:13px;");
		nicknameLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		numberLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		statusLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
		
		textLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:13px;");
	}

	private void iniEvent() {

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

		KeyFrame kfx1 = new KeyFrame(new Duration(160), kx1);
		KeyFrame kfy1 = new KeyFrame(new Duration(160), ky1);
		KeyFrame kfx2 = new KeyFrame(new Duration(320), kx2);
		KeyFrame kfy2 = new KeyFrame(new Duration(320), ky2);
		KeyFrame kfx3 = new KeyFrame(new Duration(480), kx3);
		KeyFrame kfy3 = new KeyFrame(new Duration(480), ky3);
		KeyFrame kfx4 = new KeyFrame(new Duration(640), kx4);
		KeyFrame kfy4 = new KeyFrame(new Duration(640), ky4);

		animation.getKeyFrames().add(kfx1);
		animation.getKeyFrames().add(kfy1);
		animation.getKeyFrames().add(kfx2);
		animation.getKeyFrames().add(kfy2);
		animation.getKeyFrames().add(kfx3);
		animation.getKeyFrames().add(kfy3);
		animation.getKeyFrames().add(kfx4);
		animation.getKeyFrames().add(kfy4);

	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setHeadImage(Image image) {
		headPane.setImage(image);
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
		businessHBox.getChildren().add(node);
	}

	public void removeBusinessIcon(Node node) {
		businessHBox.getChildren().remove(node);
	}

	public void setHeadSize(double value) {
		headPane.setImageSize(value);
		headPane.setImageRadius(value);
	}

	public AnchorPane getHeadShowPane() {
		return headShowPane;
	}

	public ImageNode getHeadPane() {
		return headPane;
	}
}
