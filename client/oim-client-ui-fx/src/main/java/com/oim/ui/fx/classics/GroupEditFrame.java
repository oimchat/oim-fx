/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import com.oim.fx.ui.list.HeadImagePanel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Only
 */
public class GroupEditFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HeadImagePanel imagePanel=new HeadImagePanel();
	
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	TextField nameField = new TextField();
	TextArea introduceTextArea = new TextArea();
	TextArea publicNoticeTextArea = new TextArea();
	Label titleLabel = new Label();
	
	public GroupEditFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("修改资料");
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setWidth(390);
		this.setHeight(520);
		this.setRadius(5);
		this.setCenter(rootPane);
		

		Label nameLabel = new Label("名 \t 称");
		Label introduceLabel = new Label("介 \t 绍");
		Label publicNoticeLabel = new Label("公 \t 告");
		
		nameField.setPromptText("名称");
		introduceTextArea.setPromptText("介 绍");
		publicNoticeTextArea.setPromptText("公 告");

		imagePanel.setHeadSize(70);
		imagePanel.setPrefSize(75, 75);
		imagePanel.setLayoutX(70);
		imagePanel.setLayoutY(20);
		
		nameLabel.setPrefSize(50, 25);
		nameLabel.setLayoutX(10);
		nameLabel.setLayoutY(120);
		nameField.setPrefSize(290, 25);
		nameField.setLayoutX(nameLabel.getLayoutX() + nameLabel.getPrefWidth() + 10);
		nameField.setLayoutY(nameLabel.getLayoutY());

		introduceLabel.setPrefSize(50, 25);
		introduceLabel.setLayoutX(10);
		introduceLabel.setLayoutY(nameField.getLayoutY() + nameField.getPrefHeight() + 15);
		introduceTextArea.setPrefSize(290, 120);
		introduceTextArea.setLayoutX(nameField.getLayoutX());
		introduceTextArea.setLayoutY(introduceLabel.getLayoutY());
		
		publicNoticeLabel.setPrefSize(50, 25);
		publicNoticeLabel.setLayoutX(10);
		publicNoticeLabel.setLayoutY(introduceTextArea.getLayoutY() + introduceTextArea.getPrefHeight() + 15);
		publicNoticeTextArea.setPrefSize(290, 120);
		publicNoticeTextArea.setLayoutX(nameField.getLayoutX());
		publicNoticeTextArea.setLayoutY(publicNoticeLabel.getLayoutY());

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(nameLabel);
		infoPane.getChildren().add(introduceLabel);
		infoPane.getChildren().add(publicNoticeLabel);

		infoPane.getChildren().add(nameField);
		infoPane.getChildren().add(introduceTextArea);
		infoPane.getChildren().add(publicNoticeTextArea);

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

		
		//VBox vBox=new VBox();
		
		infoPane.getChildren().add(imagePanel);
		//vBox.getChildren().add(infoPane);
		
		rootPane.setCenter(infoPane);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			GroupEditFrame.this.hide();
		});
	}
	
	public void setDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}
	
	public void clearData() {
		nameField.setText("");
		introduceTextArea.setText("");
	}
	
	public void setHeadImage(Image image){
		imagePanel.setImage(image);
	}
	
	public void setName(String value) {
		nameField.setText(value);
	}

	public void setIntroduce(String value) {
		introduceTextArea.setText(value);
	}
	
	public void setPublicNotice(String publicNotice) {
		publicNoticeTextArea.setText(publicNotice);
	}

	/**************************/
	public String getName() {
		return nameField.getText();
	}

	public String getIntroduce() {
		return introduceTextArea.getText();
	}

	public String getPublicNotice() {
		return publicNoticeTextArea.getText();
	}
	
	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
    }
}
