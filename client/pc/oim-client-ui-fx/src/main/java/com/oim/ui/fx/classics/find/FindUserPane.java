package com.oim.ui.fx.classics.find;

import com.oim.fx.common.component.SearchBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * @author: XiaHui
 * @date: 2017年3月27日 下午4:30:11
 */
public class FindUserPane extends VBox {

	AnchorPane rootPane = new AnchorPane();
	SearchBox searchBox = new SearchBox();
	TextField homeAddressText = new TextField();
	TextField locationAddressText = new TextField();
	ComboBox<String> genderBox = new ComboBox<String>();
	ComboBox<String> ageBox = new ComboBox<String>();
	Button searchButton = new Button();

	QueryPageListPane queryListPane = new QueryPageListPane();

	public FindUserPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(rootPane);
		this.getChildren().add(queryListPane);
		VBox.setVgrow(queryListPane, Priority.ALWAYS);
		
		rootPane.setMinHeight(100);
		rootPane.setPrefHeight(120);
		rootPane.setStyle("-fx-background-color:rgba(240, 240, 240, 0.9)");
		
		searchBox.setPromptText("请输入 号码/昵称/关键字/手机号码/邮箱");
		locationAddressText.setPromptText("所在地");
		homeAddressText.setPromptText("故乡");
		genderBox.setPromptText("性别");
		ageBox.setPromptText("年龄");

		searchBox.setLayoutX(50);
		searchBox.setLayoutY(15);
		searchBox.setPrefSize(600, 25);

		locationAddressText.setLayoutX(50);
		locationAddressText.setLayoutY(50);
		locationAddressText.setPrefSize(200, 25);

		homeAddressText.setLayoutX(260);
		homeAddressText.setLayoutY(50);
		homeAddressText.setPrefSize(200, 25);

		genderBox.setLayoutX(470);
		genderBox.setLayoutY(50);
		genderBox.setPrefSize(70, 25);

		ageBox.setLayoutX(550);
		ageBox.setLayoutY(50);
		ageBox.setPrefSize(100, 25);

		genderBox.setEditable(false);
		ageBox.setEditable(false);

		genderBox.getItems().addAll(
				"不限",
				"男",
				"女");
		ageBox.getItems().addAll(
				"不限",
				"18岁以下",
				"18-22岁",
				"23-26岁",
				"27-35岁",
				"35岁以上");

		searchButton.setFont(Font.font("微软雅黑", 18));
		searchButton.setText("查找");
		searchButton.setLayoutX(660);
		searchButton.setLayoutY(28);
		searchButton.setPrefSize(120, 40);

		rootPane.getChildren().add(searchBox);
		rootPane.getChildren().add(locationAddressText);
		rootPane.getChildren().add(homeAddressText);
		rootPane.getChildren().add(genderBox);
		rootPane.getChildren().add(ageBox);
		rootPane.getChildren().add(searchButton);
	}

	private void iniEvent() {

	}

	public void setSearchAction(EventHandler<ActionEvent> value) {
		searchButton.setOnAction(value);
	}
	
	public void initData(){
		searchBox.setText("");
		homeAddressText.setText("");
		locationAddressText.setText("");
		genderBox.setValue("不限");
		ageBox.setValue("不限");
		queryListPane.getChildren().clear();
	}

	public String getText() {
		return searchBox.getText();
	}

	public String getHomeAddress() {
		return homeAddressText.getText();
	}

	public String getLocationAddress() {
		return locationAddressText.getText();
	}

	public String getGender() {
		return genderBox.getValue();
	}

	public String getAge() {
		return ageBox.getValue();
	}
	
	public QueryPageListPane getQueryListPane(){
		return queryListPane;
	}
}
