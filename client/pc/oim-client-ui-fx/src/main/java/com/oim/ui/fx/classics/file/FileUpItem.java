package com.oim.ui.fx.classics.file;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午10:18:49
 */
public class FileUpItem extends FileItem {
	VBox bottomBox = new VBox();
	VBox vBox = new VBox();

	public FileUpItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);

		// vBox.getChildren().add(buttonBox);

		bottomBox.getChildren().add(vBox);
		bottomBox.getChildren().add(separator);
		this.setBottom(bottomBox);
	}

	private void iniEvent() {

	}
}
