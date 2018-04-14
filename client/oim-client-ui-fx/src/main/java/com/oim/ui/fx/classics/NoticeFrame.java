package com.oim.ui.fx.classics;

import java.util.List;

import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * @author XiaHui
 * @date 2017年8月18日 下午4:19:16
 */
public class NoticeFrame extends CommonStage{

	VBox box = new VBox();
	StackPane centerPane = new StackPane();
	ScrollPane scrollPane = new ScrollPane();
	Pagination page = new Pagination();

	public NoticeFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("消息通知");
		this.setResizable(false);
		this.setWidth(550);
		this.setHeight(480);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		BorderPane rootPane = new BorderPane();
		this.setCenter(rootPane);

		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setContent(box);
		scrollPane.widthProperty().addListener((Observable observable) -> {
			box.setPrefWidth(scrollPane.getWidth());
		});

		box.setMaxWidth(550);
		box.setSpacing(10);
		box.setPadding(new Insets(5, 20, 5, 10));

		centerPane.getChildren().add(scrollPane);

		rootPane.setCenter(centerPane);
		rootPane.setBottom(page);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub
	}

	public void addItem(Node node) {
		box.getChildren().add(node);
	}
	
	public void addItem(int index,Node node) {
		box.getChildren().add(index,node);
	}

	public void setItemList(List<? extends Node> nodeList) {
		box.getChildren().clear();
		box.getChildren().addAll(nodeList);
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
	}
}
