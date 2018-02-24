/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.only;

import java.text.NumberFormat;

import com.only.fx.OnlyFrame;
import com.only.fx.OnlyPopupOver;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.FormatStringConverter;

/**
 *
 * @author XiaHui
 */
public class ComponentFrame extends OnlyFrame {

	VBox rootVBox = new VBox();

	HBox buttonVBox = new HBox();
	HBox checkBoxVBox = new HBox();
	HBox scrollPaneVBox = new HBox();
	HBox textFieldVBox = new HBox();
	BorderPane rootPane = new BorderPane();
	
	public ComponentFrame() {
		init();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("组件测试");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);
		
		
		rootPane.setPadding(new Insets(30, 0, 0, 0));
		rootPane.getChildren().add(rootVBox);
		
		
		OnlyPopupOver po = new OnlyPopupOver();

		po.setDetached(false);
		po.setDetachable(false);

		rootVBox.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");
		rootVBox.getChildren().add(buttonVBox);
		rootVBox.getChildren().add(checkBoxVBox);
		rootVBox.getChildren().add(scrollPaneVBox);
		rootVBox.getChildren().add(textFieldVBox);

		StackPane buttonPane = new StackPane();
		buttonPane.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");

		Button button = new Button("4444急急急啊");
		button.setPrefHeight(70);
		button.setPrefWidth(200);
		button.setLayoutX(20);
		button.setLayoutY(140);

		Button button2 = new Button("确定");

		AnchorPane button3Pane = new AnchorPane();

		Button button3 = new Button("确定");
		button3.setPrefHeight(25);
		button3.setPrefWidth(50);
		button3.setLayoutX(20);
		button3.setLayoutY(20);

		button3Pane.getChildren().add(button3);

		Button button4 = new Button("确定");
		button4.setPrefHeight(25);
		button4.setPrefWidth(80);
		button4.setLayoutX(20);
		button4.setLayoutY(50);
		button4.getStyleClass().add("button-border");
		Label label = new Label("444");
		label.setPrefSize(200, 25);
		label.setPadding(new Insets(0));

		HBox hb = new HBox();
		// hb.setStyle("-fx-background-color:rgba(0, 0, 0, 1)");
		hb.setPrefSize(100, 100);
		// hb.setMaxHeight(50);
		po.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_CENTER);
		po.setContentNode(hb);
		po.setArrowSize(0);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// po.show(button4,1d,2d);
				po.show(ComponentFrame.this, ComponentFrame.this.getX(), ComponentFrame.this.getY());

			}
		});

		button3Pane.getChildren().add(button4);

		buttonPane.getChildren().add(button);
		buttonVBox.getChildren().add(buttonPane);
		buttonVBox.getChildren().add(button2);
		buttonVBox.getChildren().add(button3Pane);

		CheckBox autoCheckBox = new CheckBox();
		checkBoxVBox.getChildren().add(autoCheckBox);

		StackPane sp = new StackPane();

		// autoCheckBox.setFocusTraversable(true);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setMinWidth(150);
		scrollPane.setPrefWidth(250);
		scrollPane.setPrefHeight(360);

		scrollPane.setContent(sp);
		// scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.widthProperty().addListener((Observable observable) -> {
			// sp.setPrefWidth(scrollPane.getWidth()-20);
		});

		scrollPaneVBox.getChildren().add(scrollPane);

		DoubleProperty price = new SimpleDoubleProperty(1200.555);
		TextField text = new TextField();
		NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();
		// String symbol = currencyInstance.getCurrency().getSymbol();
		TextFormatter<Number> formatter = new TextFormatter<>(new FormatStringConverter<>(currencyInstance));
		formatter.valueProperty().bindBidirectional(price);
		text.setTextFormatter(formatter);
		text.setMaxSize(140, TextField.USE_COMPUTED_SIZE);

		textFieldVBox.getChildren().add(text);
	}
}
