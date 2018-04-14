package com.oim.ui.fx.classics.file;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午10:18:49
 */
public class FileItem extends BorderPane {

	HBox hBox = new HBox();
	ImageView imageView = new ImageView();
	VBox vBox = new VBox();
	Tooltip tooltip = new Tooltip();
	Label nameLabel = new Label();
	Label sizeLabel = new Label();
	Label speedLabel = new Label();
	Label percentageLabel = new Label();
	ProgressBar progressBar = new ProgressBar();

	HBox infoBox = new HBox();

	Button button = new Button();

	StackPane buttonPane = new StackPane();

	public FileItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setLeft(imageView);
		this.setCenter(vBox);
		this.setRight(buttonPane);
		this.setStyle("-fx-background-color:#c9e1e9");

		sizeLabel.setMinWidth(90);
		sizeLabel.setTooltip(tooltip);

		infoBox.setSpacing(20);
		infoBox.setPadding(new Insets(0, 0, 0, 20));
		infoBox.getChildren().add(nameLabel);
		infoBox.getChildren().add(sizeLabel);

		progressBar.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		HBox stackPane = new HBox();
		stackPane.getChildren().add(progressBar);

		// progressBar.setPrefWidth(0);

		HBox speedBox = new HBox();

		speedBox.setSpacing(20);
		speedBox.setPadding(new Insets(0, 0, 0, 20));
		speedBox.getChildren().add(speedLabel);
		speedBox.getChildren().add(percentageLabel);

		vBox.getChildren().add(infoBox);
		vBox.getChildren().add(stackPane);
		vBox.getChildren().add(speedBox);

		HBox.setHgrow(stackPane, Priority.ALWAYS);
		// imageView.setFitHeight(32);
		// imageView.setFitWidth(32);

		button.setMaxSize(25, 25);
		button.getStyleClass().remove("button");
		button.getStyleClass().add("close-button-square-9");
		button.setGraphic(new ImageView());

		buttonPane.setPrefWidth(50);
		buttonPane.getChildren().add(button);
		vBox.widthProperty().addListener((Observable observable) -> {
			progressBar.setPrefWidth(vBox.getWidth());
		});
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public void setOnCloseAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setImage(Image value) {
		imageView.setImage(value);
	}

	public void setName(String value) {
		nameLabel.setText(value);
		tooltip.setText(value);
	}

	public void setSize(String value) {
		sizeLabel.setText(value);
	}

	public void setSpeed(String value) {
		speedLabel.setText(value);
	}

	public void setProgress(double value) {

		if (100 >= value && value > 1) {
			progressBar.setProgress((value / 100));
		} else if (1 >= value) {
			progressBar.setProgress(value);
		}

		if (100 >= value && value > 1) {
			setPercentage(value + "%");
		} else if (1 >= value) {
			setPercentage((value * 100) + "%");
		}
	}

	public void setPercentage(String value) {
		percentageLabel.setText(value);
	}
}
