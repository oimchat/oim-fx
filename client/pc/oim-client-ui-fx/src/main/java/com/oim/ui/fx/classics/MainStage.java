/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author XiaHui
 * @date 2017-12-07 15:18:45
 */
public class MainStage extends ClassicsStage {
	Stage stage = new Stage();

	public MainStage() {
		initComponent();
	}

	/**
	 * 初始化布局排版和各个组件
	 */
	private void initComponent() {
		stage.initStyle(StageStyle.UTILITY);// 用于隐藏任务栏图标
		// stage.setOpacity(0);
		stage.initStyle(StageStyle.UTILITY);
		stage.setOpacity(0);
		stage.setMaxWidth(1);
		stage.setMaxHeight(1);
		stage.show();
		this.initOwner(stage);
		this.setTitle("OIM");
		this.setTitlePaneStyle(2);
		this.setWidth(290);
		this.setHeight(630);
		this.setMinWidth(290);
		this.setMinHeight(530);
		this.setMaxWidth(610);
		this.setRadius(5);
		this.getOnlyTitlePane().addIconifiedAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainStage.this.hide();
			}
		});

	}
}
