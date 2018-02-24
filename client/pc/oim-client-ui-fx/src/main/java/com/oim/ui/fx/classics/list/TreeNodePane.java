package com.oim.ui.fx.classics.list;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * @author XiaHui
 * @date 2017年8月8日 下午8:03:15
 */
public class TreeNodePane extends HBox {

	Label textLabel = new Label();
	Label numberLabel = new Label();

	BorderPane textPane = new BorderPane();

	public TreeNodePane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setBackground(Background.EMPTY);

		HBox hBox = new HBox();

		this.getChildren().add(textPane);
		this.getStyleClass().add("list-top-title");

		textPane.setCenter(hBox);
		hBox.getChildren().add(textLabel);
		hBox.getChildren().add(new Label(" "));
		hBox.getChildren().add(numberLabel);

		textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");
		textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");

	}

	private void iniEvent() {
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void setNumberText(String text) {
		numberLabel.setText(text);
	}
}
