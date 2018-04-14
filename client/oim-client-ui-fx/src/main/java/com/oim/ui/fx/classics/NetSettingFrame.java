/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Only
 */
public class NetSettingFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	TextField addressField = new TextField();
	Label titleLabel = new Label();

	public NetSettingFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(420);
		this.setHeight(120);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("服务器地址设置");

		Label nameLabel = new Label("服务器地址：");

		addressField.setPromptText("服务器地址");

		nameLabel.setPrefSize(75, 25);
		nameLabel.setLayoutX(10);
		nameLabel.setLayoutY(5);
		addressField.setPrefSize(300, 25);
		addressField.setLayoutX(nameLabel.getLayoutX() + nameLabel.getPrefWidth() + 10);
		addressField.setLayoutY(nameLabel.getLayoutY());

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(nameLabel);
		infoPane.getChildren().add(addressField);

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		button.setText("确定");
		button.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(button);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setTop(topBox);
		rootPane.setCenter(infoPane);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			NetSettingFrame.this.hide();
		});
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setAddress(String value) {
		addressField.setText(value);
	}

	public String getAddress() {
		return addressField.getText();
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}
}
