/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test.component;

import com.only.fx.OnlyFrame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class TooltipFrame extends OnlyFrame {

	VBox rootVBox = new VBox();

	HBox hBox1 = new HBox();
	HBox hBox2 = new HBox();
	HBox hBox3 = new HBox();
	HBox hBox4 = new HBox();

	public TooltipFrame() {
		init();
	}

	private void init() {

		this.setCenter(rootVBox);
		this.setTitle("登录");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		rootVBox.getChildren().add(hBox1);
		rootVBox.getChildren().add(hBox2);
		rootVBox.getChildren().add(hBox3);
		rootVBox.getChildren().add(hBox4);

		Tooltip tooltip1 = new Tooltip();
		tooltip1.setText("账号");
		tooltip1.setHideOnEscape(true);

		TextField textField1 = new TextField();
		
		textField1.setTooltip(tooltip1);
		
		Button button1 = new Button();

		button1.setText("点击");
		button1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tooltip1.show(TooltipFrame.this);
			}
		});
		hBox1.getChildren().add(textField1);
		hBox1.getChildren().add(button1);

	}
}
