package com.oim.ui.fx.classics;

import com.only.fx.common.action.EventAction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017年8月23日 下午6:03:57
 */
public class RequestFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();


	VBox box = new VBox();
	Label label = new Label();

	HBox bottomBox = new HBox();
	Button refuseButton = new Button();
	Button acceptButton = new Button();
	EventAction<?> refuseAction;
	EventAction<?> acceptAction;

	public RequestFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(380);
		this.setHeight(180);
		this.setResizable(false);
		this.setTitlePaneStyle(3);
		this.setRadius(5);
		this.setCenter(rootPane);

		label.setWrapText(true);

		box.setStyle("-fx-background-color:#e8f0f3");
		box.getChildren().add(label);

		refuseButton.setText("拒绝");
		refuseButton.setPrefWidth(80);

		acceptButton.setText("接受");
		acceptButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(refuseButton);
		bottomBox.getChildren().add(acceptButton);

		rootPane.setCenter(box);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
	}

	public void setContent(String content) {
		label.setText(content);
	}

	public void setOnAcceptAction(EventHandler<ActionEvent> value) {
		acceptButton.setOnAction(value);
	}

	public void setOnRefuseAction(EventHandler<ActionEvent> value) {
		refuseButton.setOnAction(value);
		getOnlyTitlePane().addOnCloseAction(value);
	}
}
