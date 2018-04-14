/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics.component;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.ui.fx.classics.ClassicsStage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class IconButtonFrameTest extends Application {
	
	
	ClassicsStage stage=new ClassicsStage();
	BorderPane rootPane = new BorderPane();
	VBox componentBox = new VBox();
    @Override
    public void start(Stage primaryStage) {
    	stage.show();
    	
    	stage.setCenter(rootPane);
    	stage.setTitle("组件测试");
    	stage.setWidth(380);
    	stage.setHeight(600);
    	stage.setRadius(10);

		VBox topBox = new VBox();
		topBox.setPrefHeight(50);
		rootPane.setTop(topBox);

		rootPane.setCenter(componentBox);

		Image image = ImageBox.getImageClassPath("/images/head/101_100.gif");
		IconButton themeIconButton = new IconButton(image);
		themeIconButton.setPrefSize(130, 130);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				componentBox.getChildren().add(themeIconButton);
			}
		});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
