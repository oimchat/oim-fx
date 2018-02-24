package com.oim.server.api.tools.ui.main;

import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * @author: XiaHui
 * @date: 2018-02-23 09:24:05
 */
public class RequestPane extends StackPane {

	private SplitPane splitPane = new SplitPane();

	private BorderPane requestBorderPane = new BorderPane();
	private TextArea requestTextArea = new TextArea();
	private Label requestLabel = new Label();

	private BorderPane backBorderPane = new BorderPane();
	private TextArea backTextArea = new TextArea();
	private Label backLabel = new Label();

	public RequestPane() {
		initComponent();
	}

	private void initComponent() {

		requestLabel.setText("请求内容：");
		requestBorderPane.setTop(requestLabel);
		requestBorderPane.setCenter(requestTextArea);

		backLabel.setText("返回结果：");
		backBorderPane.setTop(backLabel);
		backBorderPane.setCenter(backTextArea);

		splitPane.getItems().add(requestBorderPane);
		splitPane.getItems().add(backBorderPane);
		splitPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		splitPane.setDividerPositions(0.5f, 0.5f);
		// splitPane.setOrientation(Orientation.VERTICAL);

		this.getChildren().add(splitPane);
	}

	public String getRequestText() {
		return requestTextArea.getText();
	}

	public void setBackText(String text) {
		backTextArea.setText(text);
	}
}
