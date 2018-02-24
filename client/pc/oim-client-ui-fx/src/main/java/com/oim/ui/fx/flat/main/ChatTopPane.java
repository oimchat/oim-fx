package com.oim.ui.fx.flat.main;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-10-27 15:45:52
 */
public class ChatTopPane extends VBox {

	Label label = new Label();

	public ChatTopPane() {
		this.getChildren().add(label);
	}

	public void setText(String value) {
		label.setText(value);
	}

	public Label getLabel() {
		return label;
	}

}
