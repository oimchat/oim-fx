/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.main;

import com.oim.fx.common.component.IconPane;
import com.oim.fx.ui.list.HeadImagePanel;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author XiaHui
 */
public class UserDataPane extends BorderPane {

	private final HeadImagePanel headImagePanel = new HeadImagePanel();
	private final StackPane headStackPane = new StackPane();

	private final VBox dataRootPane = new VBox();
	private final HBox textPane = new HBox();
	private final HBox infoPane = new HBox();

	private final ToolBar businessPane = new ToolBar();

	private final IconPane statusButton = new IconPane();
	private final Label nicknameLabel = new Label();
	private final Label numberLabel = new Label();
	private final Label statusLabel = new Label();

	private final Label textLabel = new Label();

	public UserDataPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {

		this.setLeft(headStackPane);
		this.setCenter(dataRootPane);

		headImagePanel.setPrefWidth(60);
		headImagePanel.setPrefHeight(60);

		headImagePanel.setHeadSize(60);
		headImagePanel.setHeadRadius(60);

		Button button = new Button();
		button.getStyleClass().remove("button");
		button.setGraphic(headImagePanel);

		headStackPane.setPadding(new Insets(0, 8, 10, 10));
		headStackPane.getChildren().add(button);

		businessPane.setBackground(Background.EMPTY);
		businessPane.setPadding(new Insets(0, 5, 5, 0));

		dataRootPane.setSpacing(2);
		dataRootPane.setAlignment(Pos.CENTER_LEFT);
		dataRootPane.getChildren().add(infoPane);
		dataRootPane.getChildren().add(textPane);
		dataRootPane.getChildren().add(businessPane);

		// VBox.setVgrow(infoPane, Priority.ALWAYS);
		// VBox.setVgrow(textPane, Priority.ALWAYS);
		// VBox.setVgrow(businessPane, Priority.ALWAYS);
		infoPane.setPadding(new Insets(0, 0, 0, 5));
		infoPane.getChildren().add(statusButton);
		infoPane.getChildren().add(nicknameLabel);
		infoPane.getChildren().add(statusLabel);
		infoPane.getChildren().add(numberLabel);

		textPane.setPadding(new Insets(0, 0, 0, 5));
		textPane.getChildren().add(textLabel);

		// nicknameLabel.setFont(new Font("微软雅黑",14));
		nicknameLabel.setFont(Font.font("微软雅黑", FontWeight.BOLD, 14));
		nicknameLabel.setStyle("-fx-text-fill: black;");
	}

	private void iniEvent() {
	}

	public void setHeadImage(Image headImage) {
		headImagePanel.setImage(headImage);
	}

	public void setStatusImage(Image statusImage) {
		statusButton.setNormalImage(statusImage);
	}

	public void setNickname(String nickname) {
		this.nicknameLabel.setText(nickname);
	}

	public void setText(String text) {
		this.textLabel.setText(text);
	}

	public void addBusinessIcon(Node node) {
		businessPane.getItems().add(node);
	}

	public void removeBusinessIcon(Node node) {
		businessPane.getItems().remove(node);
	}

	public void setHeadOnMouseClicked(EventHandler<? super MouseEvent> value) {
		headImagePanel.setOnMouseClicked(value);
	}

	public void setStatusOnMouseClicked(EventHandler<? super MouseEvent> value) {
		statusButton.setOnMouseClicked(value);
	}
}
