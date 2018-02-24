package com.oim.ui.fx.classics;

import com.oim.fx.common.component.WaitingPane;
import com.oim.ui.fx.common.chat.ChatShowPane;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * 
 * @author: XiaHui
 * @date 2018-02-01 13:44:29
 */
public class ChatHistoryFrame extends CommonStage {

	Pagination page = new Pagination();// 分页组件
	StackPane centerPane = new StackPane();
	ChatShowPane showPane = new ChatShowPane();
	WaitingPane waitingPane = new WaitingPane();
	ScrollPane scrollPane = new ScrollPane();

	public ChatHistoryFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("聊天记录");
		// this.setResizable(false);
		this.setWidth(750);
		this.setHeight(480);
		// this.setTitlePaneStyle(2);
		this.setRadius(5);
		BorderPane rootPane = new BorderPane();
		this.setCenter(rootPane);


		centerPane.getChildren().add(showPane);
		centerPane.getChildren().add(waitingPane);

		// centerPane.setStyle("-fx-background-color:rgba(8, 103, 68, 0.5)");
		centerPane.setStyle("-fx-background-color:rgba(255, 255, 255)");

		rootPane.setCenter(centerPane);
		rootPane.setBottom(page);

		this.showWaiting(false, WaitingPane.show_waiting);
		this.setPage(0, 1);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub
	}

	public void showWaiting(boolean show, String key) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				waitingPane.setVisible(show);
				showPane.setVisible(!show);
				waitingPane.show(key);
			}
		});
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

	public void setBodyHtml(String html) {
		showPane.setBodyHtml(html);
	}

	public void replaceImage(String id, String src) {
		src = src.replace("\\", "/");
		showPane.replaceImage(id, src);
	}

	public void initHtml() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				showPane.initializeHtml();
			}
		});
	}

	public ChatShowPane getShowPane() {
		return showPane;
	}
}
