/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test.component;

import com.only.fx.OnlyFrame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class TextFrameTest extends Application {

	OnlyFrame frame = new OnlyFrame();

	VBox rootVBox = new VBox();

	VBox topBox = new VBox();
	VBox centerBox = new VBox();
	VBox bBox = new VBox();

	Button button = new Button("弹出");
	TextFlow textFlow=new TextFlow();
	
	@Override
	public void start(Stage primaryStage) {
		frame.setCenter(rootVBox);
		frame.setTitle("测试");
		frame.setWidth(380);
		frame.setHeight(600);
		frame.setRadius(10);

		topBox.setPrefHeight(20);

		rootVBox.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");
		rootVBox.getChildren().add(topBox);
		rootVBox.getChildren().add(centerBox);
		rootVBox.getChildren().add(bBox);

		button.setPrefHeight(70);
		button.setPrefWidth(200);
		button.setLayoutX(20);
		button.setLayoutY(140);

		centerBox.getChildren().add(button);
		
		bBox.getChildren().add(textFlow);
		
		textFlow.setPrefHeight(80);
		textFlow.setPrefWidth(200);
		frame.show();
		initEvent() ;
	}
	private void initEvent() {
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Text text=new Text("HH");
				textFlow.getChildren().add(text);
			}
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
