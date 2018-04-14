/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import com.oim.fx.common.component.BaseStage;

import javafx.scene.layout.VBox;

/**
 *
 * @author Only
 */
public class PagePanelFrame extends BaseStage {

	VBox box = new VBox();

	public PagePanelFrame() {
		init();
		initEvent();
	}

	private void init() {
		this.setBackground("Resources/Images/Wallpaper/18.jpg");
		this.setTitle("分页");
		this.setWidth(440);
		this.setHeight(360);
		this.setCenter(box);

		box.setStyle("-fx-background-color:rgba(255, 255, 255, 0.2)");

	}

	private void initEvent() {

	}

}
