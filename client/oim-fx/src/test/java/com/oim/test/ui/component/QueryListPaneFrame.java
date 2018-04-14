package com.oim.test.ui.component;

import java.util.List;

import com.oim.fx.common.component.BaseStage;
import com.oim.fx.common.component.WaitingPane;
import com.oim.ui.fx.classics.find.QueryPageListPane;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * 设备列表
 * 
 * @author: XiaHui
 * @date: 2017年4月11日 上午9:02:43
 */
public class QueryListPaneFrame extends BaseStage {

	QueryPageListPane queryListPane = new QueryPageListPane();
	
	public QueryListPaneFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("设备列表");
		this.setResizable(false);
		this.setWidth(960);
		this.setHeight(600);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		BorderPane rootPane = new BorderPane();
		this.setCenter(rootPane);

		Label titleLabel = new Label();
		titleLabel.setText("设备列表");
		titleLabel.setFont(Font.font("微软雅黑", 30));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		HBox hBox = new HBox();
		hBox.setMinHeight(35);
		hBox.setStyle("-fx-background-color:rgba(18, 98, 217, 1)");
		
		hBox.getChildren().add(titleLabel);
		
	
		
		rootPane.setTop(hBox);
		rootPane.setCenter(queryListPane);
		this.showWaiting(false, WaitingPane.show_waiting);
		this.setPage(0, 1);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub
	}

	public void showWaiting(boolean show,String key) {
		queryListPane.showWaiting(show, key);
	}

	public void setNodeList(List<? extends Node> nodeList) {
		queryListPane.setNodeList(nodeList);
	}

	public void setPage(int currentPage, int totalPage) {
		queryListPane.setPage(currentPage, totalPage);
	}
	
	public void setTotalPage(int totalPage) {
		queryListPane.setTotalPage(totalPage);
	}


	public void setPageFactory(Callback<Integer, Node> value) {
		queryListPane.setPageFactory(value);
	}
}
