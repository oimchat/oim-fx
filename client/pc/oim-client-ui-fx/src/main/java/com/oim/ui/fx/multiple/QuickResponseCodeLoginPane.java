package com.oim.ui.fx.multiple;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * @author XiaHui
 * @date 2017-12-16 10:56:10
 */
public class QuickResponseCodeLoginPane extends StackPane {

	private final ImageView imageView = new ImageView();

	public QuickResponseCodeLoginPane() {
		this.getChildren().add(imageView);
	}

	public void setImage(Image value) {
		imageView.setImage(value);
	}
}
