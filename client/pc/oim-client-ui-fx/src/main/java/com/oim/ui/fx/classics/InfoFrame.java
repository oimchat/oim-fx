/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import com.oim.ui.fx.classics.add.InfoPane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Only
 */
public class InfoFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();
	InfoPane infoPanel = new InfoPane();

	HBox bottomBox = new HBox();
	Button button = new Button();

	public InfoFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(380);
		this.setHeight(520);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);

		infoPanel.setHeadSize(100);
		infoPanel.setMaxWidth(380);
		infoPanel.setStyle("-fx-background-color:#e8f0f3");

		button.setText("关闭");
		button.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.getChildren().add(button);

		rootPane.setCenter(infoPanel);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		button.setOnAction(a -> {
			InfoFrame.this.hide();
		});
	}

	public InfoPane getInfoPane() {
		return infoPanel;
	}

}
