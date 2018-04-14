package com.oim.ui.fx.classics.find;

import com.oim.fx.common.component.SearchBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年3月27日 下午4:30:11
 */
public class FindGroupPane extends VBox {

	AnchorPane rootPane = new AnchorPane();
	SearchBox searchBox = new SearchBox();
	Button searchButton = new Button();

	QueryPageListPane queryListPane = new QueryPageListPane();

	public FindGroupPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(rootPane);
		this.getChildren().add(queryListPane);
		VBox.setVgrow(queryListPane, Priority.ALWAYS);
		
		rootPane.setMinHeight(60);
		rootPane.setPrefHeight(60);
		rootPane.setStyle("-fx-background-color:rgba(240, 240, 240, 0.9)");
		
		searchBox.setPromptText("请输入 群号码/名称/关键字");
		

		searchBox.setLayoutX(50);
		searchBox.setLayoutY(15);
		searchBox.setPrefSize(600, 25);

		searchButton.setText("查找");
		searchButton.setLayoutX(660);
		searchButton.setLayoutY(15);
		searchButton.setPrefSize(100, 26);

		rootPane.getChildren().add(searchBox);
		rootPane.getChildren().add(searchButton);
		
		
		queryListPane.setVgap(10);
		queryListPane.setHgap(10);
	}

	private void iniEvent() {

	}

	public void initData(){
		searchBox.setText("");
	}
	
	public void setSearchAction(EventHandler<ActionEvent> value) {
		searchButton.setOnAction(value);
	}

	public String getText() {
		return searchBox.getText();
	}

	public QueryPageListPane getQueryListPane(){
		return queryListPane;
	}
}
