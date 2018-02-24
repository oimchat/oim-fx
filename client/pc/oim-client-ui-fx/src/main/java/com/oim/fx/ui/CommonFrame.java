/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author Only
 */
public class CommonFrame extends BaseFrame {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button doneButton = new Button();
	Button cancelButton = new Button();
	Label titleLabel = new Label();

	public CommonFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setRadius(5);
		this.setCenter(rootPane);
		//this.setTitle("");

		titleLabel.setText("");
		titleLabel.setFont(Font.font("微软雅黑", 14));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		topBox.setStyle("-fx-background-color:#2cb1e0");
		topBox.setPrefHeight(30);
		topBox.setPadding(new Insets(5, 10, 5, 10));
		topBox.setSpacing(10);
		topBox.getChildren().add(titleLabel);

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		doneButton.setText("确定");
		doneButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(doneButton);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setTop(topBox);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			CommonFrame.this.hide();
		});
	}

	public void setCenterNode(Node node) {
		rootPane.setCenter(node);
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		doneButton.setOnAction(value);
	}

	public void setDoneButtonText(String value) {
		doneButton.setText(value);
	}

	public void setCancelButtonText(String value) {
		cancelButton.setText(value);
	}

	public void showDoneButton(boolean value) {
		doneButton.setVisible(value);
	}

	public void showCancelButton(boolean value) {
		cancelButton.setVisible(value);
	}
}
