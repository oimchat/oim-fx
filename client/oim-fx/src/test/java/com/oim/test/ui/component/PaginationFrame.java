/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import com.oim.fx.common.component.BaseStage;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author Only
 */
public class PaginationFrame extends BaseStage {

	VBox box = new VBox();
	Pagination p = new Pagination();

	public PaginationFrame() {
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

		box.getChildren().add(p);

		p.setCurrentPageIndex(1);
		p.setPageCount(100);
	}

	private void initEvent() {
		p.setPageFactory(new Callback<Integer, Node>() {

			@Override
			public Node call(Integer index) {
				System.out.println(index);
				return new Label("第" + index + "页");
			}
		});
	}
}
