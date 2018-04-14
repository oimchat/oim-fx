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
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Only
 */
public class UpdatePasswordFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	PasswordField oldPasswordField = new PasswordField();
	PasswordField newPasswordField = new PasswordField();
	PasswordField confirmPasswordField = new PasswordField();

	public UpdatePasswordFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("修改密码");
		this.setWidth(330);
		this.setHeight(220);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setResizable(false);
		this.setCenter(rootPane);

		oldPasswordField.setPromptText("请输入原密码");
		newPasswordField.setPromptText("请输入新密码");
		confirmPasswordField.setPromptText("请再次输入新密码");

		oldPasswordField.setLayoutX(75);
		oldPasswordField.setLayoutY(20);
		oldPasswordField.setPrefSize(155, 25);

		newPasswordField.setLayoutX(75);
		newPasswordField.setLayoutY(55);
		newPasswordField.setPrefSize(155, 25);

		confirmPasswordField.setLayoutX(75);
		confirmPasswordField.setLayoutY(90);
		confirmPasswordField.setPrefSize(155, 25);

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(oldPasswordField);
		infoPane.getChildren().add(newPasswordField);
		infoPane.getChildren().add(confirmPasswordField);

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
			UpdatePasswordFrame.this.hide();
		});
	}

	public void setOnDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public String getOldPassword() {
		return oldPasswordField.getText();
	}

	public String getNewPassword() {
		return newPasswordField.getText();
	}

	public String getConfirmPassword() {
		return confirmPasswordField.getText();
	}

	public void clearData() {
		oldPasswordField.setText("");
		newPasswordField.setText("");
		confirmPasswordField.setText("");
	}
}
