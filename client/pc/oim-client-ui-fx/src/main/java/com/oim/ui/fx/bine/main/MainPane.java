package com.oim.ui.fx.bine.main;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class MainPane extends StackPane {

	BorderPane borderPane = new BorderPane();
	BorderPane leftBorderPane = new BorderPane();

	public MainPane() {
		initComponent();
	}

	private void initComponent() {
		this.getChildren().add(borderPane);
		borderPane.setLeft(leftBorderPane);
	}

	public void setLeftWidth(double value) {
		leftBorderPane.setPrefWidth(value);
		leftBorderPane.setMaxWidth(value);
		leftBorderPane.setMinWidth(value-1);
	}

	public void setLeftTop(Node node) {
		leftBorderPane.setTop(node);
	}

	public void setLeftCenter(Node node) {
		leftBorderPane.setCenter(node);
	}

	public void setLeftBottom(Node node) {
		leftBorderPane.setBottom(node);
	}

	public void setCenter(Node node) {
		borderPane.setCenter(node);
	}
}
