package com.oim.ui.fx.classics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 
 * @author: XiaHui
 * @date 2018-02-01 12:17:31
 */
public class ForgetPasswordFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox bottomBox = new HBox();
	Button cancelButton = new Button();

	Label titleLabel = new Label();

	public ForgetPasswordFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("重设密码");
		this.setTitlePaneStyle(2);
		this.setWidth(420);
		this.setHeight(520);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setResizable(false);

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			ForgetPasswordFrame.this.hide();
		});
	}

	public void setCenterNode(Node node) {
		rootPane.setCenter(node);
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}
}
