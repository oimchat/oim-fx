package com.oim.ui.fx.multiple;

import com.oim.ui.fx.multiple.main.MultipleListPane;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-14 09:20:40
 */
public class MultipleMainPane extends StackPane {

	private final BorderPane rootBorderPane = new BorderPane();
	private final MultipleListPane multipleListPane = new MultipleListPane();

	public MultipleMainPane() {
		initComponent();
		initEvent();
	}

	private void initComponent() {

		multipleListPane.setPrefWidth(228);
		multipleListPane.setPrefHeight(600);
		
		VBox lineVBox = new VBox();
		lineVBox.setMinWidth(1);
		lineVBox.setStyle("-fx-background-color:#000000;");

		BorderPane lineBorderPane = new BorderPane();
		lineBorderPane.setLeft(multipleListPane);
		lineBorderPane.setRight(lineVBox);

		rootBorderPane.setLeft(lineBorderPane);

		this.getChildren().add(rootBorderPane);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
	}

	public MultipleListPane getMultipleListPane() {
		return multipleListPane;
	}

	public void setCenter(Node value) {
		rootBorderPane.setCenter(value);
	}
}
