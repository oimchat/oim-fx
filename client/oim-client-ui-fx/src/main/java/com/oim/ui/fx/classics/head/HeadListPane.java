package com.oim.ui.fx.classics.head;

import java.util.List;

import com.oim.fx.common.component.WaitingPane;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 * 
 * @author: XiaHui
 * @date 2018-02-01 13:20:45
 */
public class HeadListPane extends BorderPane {

	StackPane centerPane = new StackPane();
	FlowPane flowPane = new FlowPane();
	WaitingPane waitingPanel = new WaitingPane();
	ScrollPane scrollPane = new ScrollPane();

	public HeadListPane() {
		initComponent();
		initEvent();
	}

	private void initComponent() {

		flowPane.setPadding(new Insets(15, 10, 0, 10));
		flowPane.setVgap(10);
		flowPane.setHgap(10);
		flowPane.setPrefWrapLength(900); // 预设流面板的宽度，使得能够显示两列

		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setContent(flowPane);

		centerPane.getChildren().add(scrollPane);
		centerPane.getChildren().add(waitingPanel);

		centerPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.7)");

		this.setCenter(centerPane);
		this.showWaiting(false, WaitingPane.show_waiting);
	}

	private void initEvent() {

	}

	public void showWaiting(boolean show, String key) {
		waitingPanel.setVisible(show);
		flowPane.setVisible(!show);
		waitingPanel.show(key);
	}

	public void setNodeList(List<? extends Node> nodeList) {
		flowPane.getChildren().clear();
		flowPane.getChildren().addAll(nodeList);
	}

	public void setPrefWrapLength(double length) {
		flowPane.setPrefWrapLength(length);
	}

	public void setVgap(double value) {
		flowPane.setVgap(value);
	}

	public void setHgap(double value) {
		flowPane.setHgap(value);
	}
}
