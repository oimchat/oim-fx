/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.server.api.tools.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author Only
 */
public class CommonStage extends FreeStage {

	private final BorderPane rootPane = new BorderPane();

	private final HBox topBox = new HBox();
	private final Label titleLabel = new Label();

	public CommonStage() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setRadius(5);
		super.setCenter(rootPane);

		titleLabel.textProperty().bind(this.titleProperty());
		titleLabel.setFont(Font.font("微软雅黑", 14));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		// topBox.setStyle("-fx-background-color:#2cb1e0");
		topBox.setPrefHeight(35);
		topBox.setPadding(new Insets(5, 10, 5, 10));
		topBox.setSpacing(10);
		topBox.getChildren().add(titleLabel);

		rootPane.setTop(topBox);
	}

	private void iniEvent() {
	}

	public void setCenter(Node node) {
		rootPane.setCenter(node);
	}
}
