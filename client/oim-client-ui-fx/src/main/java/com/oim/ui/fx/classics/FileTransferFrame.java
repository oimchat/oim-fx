package com.oim.ui.fx.classics;

import com.oim.fx.ui.list.ListRootPanel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午10:12:12
 */
public class FileTransferFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();
	VBox rootBox = new VBox();
	ListRootPanel listPane = new ListRootPanel();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button cancelButton = new Button();
	Label titleLabel = new Label();

	public FileTransferFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("");
		this.setTitlePaneStyle(2);
		this.setWidth(390);
		this.setHeight(520);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setResizable(false);

		cancelButton.setText("关闭");
		cancelButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setTop(topBox);
		rootPane.setCenter(listPane);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			FileTransferFrame.this.hide();
		});
	}

	public void setLabelTitle(String value) {
		titleLabel.setText(value);
		this.setTitle(value);
	}

	public void addNode(Node node) {
		listPane.addNode(node);
	}

	public void addNode(int index, Node node) {
		listPane.addNode(index, node);
	}

	public void removeNode(Node node) {
		listPane.removeNode(node);
	}

	public void removeNode(int index) {
		listPane.removeNode(index);
	}
}
