/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.util.ArrayList;
import java.util.List;

import com.oim.common.util.KeyUtil;
import com.oim.fx.ui.list.HeadItem;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Only
 */
public class QueryListPaneFrameTest extends Application {
	QueryListPaneFrame deviceListFrame = new QueryListPaneFrame();

	int size = 1;

	@Override
	public void start(Stage primaryStage) {
		deviceListFrame.show();
		initc();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private void initc() {
		deviceListFrame.setPage(0, 10);
		deviceListFrame.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				System.out.println(index);
				getList(index + 1);
				return new Label("第" + (index + 1) + "页");
			}
		});
	}

	public void getList(int index) {
		List<HeadItem> list = new ArrayList<HeadItem>();
		for (int i = ((index - 1) * size); i < index * size; i++) {
			HeadItem item = new HeadItem();
			item.setNickname("账号" + i);
			item.setShowText(KeyUtil.getKey());
			list.add(item);
		}
		deviceListFrame.setNodeList(list);
	}
}
