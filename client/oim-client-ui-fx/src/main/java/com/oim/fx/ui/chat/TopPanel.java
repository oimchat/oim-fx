package com.oim.fx.ui.chat;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TopPanel extends HBox {

	private VBox baseBox = new VBox();

	private Label nameLabel = new Label();
	private Label textLabel = new Label();

	private HBox nameBox = new HBox();
	private HBox textBox = new HBox();
	ToolBar toolBar = new ToolBar();

	public TopPanel() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		HBox gapBox = new HBox();
		gapBox.setPrefWidth(8);
		this.getChildren().add(gapBox);
		this.getChildren().add(baseBox);

		gapBox = new HBox();
		gapBox.setPrefHeight(8);
		baseBox.getChildren().add(gapBox);
		baseBox.getChildren().add(nameBox);
		baseBox.getChildren().add(textBox);
		baseBox.getChildren().add(toolBar);

		nameBox.getChildren().add(nameLabel);
		textBox.getChildren().add(textLabel);

		nameLabel.setStyle("-fx-font-size:18px;");
		// textBox.setStyle("-fx-background-color:rgba(255, 255, 255, 0.92)");
		
		toolBar.setBackground(Background.EMPTY);
		toolBar.setPadding(new Insets(0, 0, 0, 0));
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
}
