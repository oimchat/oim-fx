package com.oim.ui.fx.classics.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午10:18:49
 */
public class FileDownItem extends FileItem {

	Button saveAsButton = new Button();
	VBox bottomBox = new VBox();
	VBox vBox = new VBox();
	HBox buttonBox = new HBox();

	public FileDownItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {

		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);

		saveAsButton.setText("另存为");
		saveAsButton.setPrefSize(80, 20);

		buttonBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonBox.setPadding(new Insets(0, 10, 5, 10));
		buttonBox.setSpacing(10);
		buttonBox.getChildren().add(saveAsButton);

		vBox.getChildren().add(buttonBox);

		bottomBox.getChildren().add(vBox);
		bottomBox.getChildren().add(separator);
		this.setBottom(bottomBox);
	}

	private void iniEvent() {
	}

	public void setOnSaveAction(EventHandler<ActionEvent> value) {
		saveAsButton.setOnAction(value);
	}

	public void showSaveAs(boolean show) {
		// saveAsButton.setVisible(show);
		if (show) {
			if (!vBox.getChildren().contains(buttonBox)) {
				vBox.getChildren().add(buttonBox);
			}
		} else {
			if (vBox.getChildren().contains(buttonBox)) {
				vBox.getChildren().remove(buttonBox);
			}
		}
	}
}
