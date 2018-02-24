package com.oim.ui.fx.classics.chat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChatTopPane extends StackPane {

	private final BorderPane baseBorderPane = new BorderPane();

	private final Label nameLabel = new Label();
	private final Label textLabel = new Label();

	private final HBox nameHBox = new HBox();
	private final HBox textHBox = new HBox();
	private final ToolBar toolBar = new ToolBar();
	private final HBox rightToolBox = new HBox();

	public ChatTopPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(baseBorderPane);

		nameLabel.setStyle("-fx-font-size:18px;");

		nameHBox.getChildren().add(nameLabel);
		textHBox.getChildren().add(textLabel);

		VBox infoVBox = new VBox();
		infoVBox.setPadding(new Insets(10, 0, 5, 28));
		infoVBox.getChildren().add(nameHBox);
		infoVBox.getChildren().add(textHBox);

		// this.setPadding(new Insets(0, 0, 0, 8));

		// textBox.setStyle("-fx-background-color:rgba(255, 255, 255, 0.92)");

		toolBar.setBackground(Background.EMPTY);
		//toolBar.setPadding(new Insets(0, 0, 0, 0));
		toolBar.setPadding(new Insets(0, 0, 0, 28));
		
		rightToolBox.setAlignment(Pos.CENTER_RIGHT);

		
		HBox toolHBox = new HBox();
		
		toolHBox.setAlignment(Pos.CENTER_LEFT);
		toolHBox.getChildren().add(toolBar);
		
		BorderPane toolBorderPane = new BorderPane();
		toolBorderPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");
		toolBorderPane.setCenter(toolHBox);
		toolBorderPane.setRight(rightToolBox);
		
		HBox topLine = new HBox();
		topLine.setMinHeight(1);
		topLine.setStyle("-fx-background-color:#d6d6d6;");

		VBox topVBox = new VBox();
		topVBox.setPadding(new Insets(0, 0, 0, 0));
		topVBox.getChildren().add(infoVBox);
		topVBox.getChildren().add(topLine);
		
		baseBorderPane.setCenter(topVBox);
		baseBorderPane.setBottom(toolBorderPane);
	}

	private void iniEvent() {

	}

	public void setName(String name) {
		nameLabel.setText(name);
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void addTool(Node value) {
		toolBar.getItems().add(value);
	}

	public void removeTool(Node value) {
		toolBar.getItems().remove(value);
	}

	public void addRightTool(Node value) {
		rightToolBox.getChildren().add(value);
	}

	public void removeRightTool(Node value) {
		rightToolBox.getChildren().add(value);
	}
}
