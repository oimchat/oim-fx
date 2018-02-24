/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.test.control;

import com.oim.common.component.ItemRemoveListCell;
import com.only.fx.OnlyFrame;
import com.only.fx.common.action.ExecuteAction;
import com.only.fx.common.action.ValueAction;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author XiaHui
 */
public class ComponentFrame extends OnlyFrame {

	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();

	public ComponentFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		VBox topBox = new VBox();
		topBox.setPrefHeight(50);
		rootPane.setTop(topBox);
		rootPane.setCenter(componentBox);

		ComboBox<String> comboBox = new ComboBox<String>();

		comboBox.getItems().add("10000");
		ExecuteAction a = new ExecuteAction() {

			@Override
			public <T, E> E execute(T value) {
				System.out.println(value);
				return null;
			}
		};
		StringConverter<String> stringConverter = new StringConverter<String>() {
			@Override
			public String toString(String t) {
				return t;
			}

			@Override
			public String fromString(String text) {
				return text;
			}
		};
		//ItemListCell<String> cell = new ItemListCell<String>(a,stringConverter);
		//comboBox.setButtonCell(cell);
		ValueAction<String> removeValueAction=new ValueAction<String>() {

			@Override
			public void value(String value) {
				System.out.println(value);
			}
		};
		comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			
			@Override
			public ListCell<String> call(ListView<String> param) {
				return new ItemRemoveListCell<String>(removeValueAction);
			}
		});
		componentBox.getChildren().add(comboBox);
	}
}
