package com.oim.ui.fx.classics.find;

import java.util.List;

import com.oim.fx.common.component.WaitingPane;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * @author: XiaHui
 * @date: 2017年3月27日 下午4:30:11
 */
public class QueryPageListPane extends VBox {

	BorderPane listRootPane = new BorderPane();
	Pagination page = new Pagination();// 分页组件
	StackPane centerPane = new StackPane();
	FlowPane flowPane = new FlowPane();
	WaitingPane waitingPanel = new WaitingPane();
	ScrollPane scrollPane = new ScrollPane();

	public QueryPageListPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(listRootPane);
		//this.setAlignment(Pos.BOTTOM_CENTER);

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

		listRootPane.setCenter(centerPane);
		listRootPane.setBottom(page);
		listRootPane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");
		VBox.setVgrow(listRootPane, Priority.ALWAYS);
		this.showWaiting(false, WaitingPane.show_waiting);
		this.setPage(0, 1);
	}

	private void iniEvent() {

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

	public void setPage(int currentPage, int totalPage) {
		page.setCurrentPageIndex(currentPage);
		page.setPageCount(totalPage);
	}

	public void setTotalPage(int totalPage) {
		page.setPageCount(totalPage);
	}

	public void setPageFactory(Callback<Integer, Node> value) {
		page.setPageFactory(value);
		// page.setPageFactory(new Callback<Integer, Node>() {
		//
		// @Override
		// public Node call(Integer index) {
		// System.out.println(index);
		// return new Label("第" + index + "页");
		// }
		// });
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
