package com.javafx.demo.image.handler;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author: XiaHui
 * @date: 2017年4月22日 上午10:23:42
 */
public class ImageViewApp extends Application {

	public Parent createContent() {
		Image topIamge = new Image("com/javafx/demo/image/handler/handle.jpg");;
		ImageView topImageView = new ImageView();
		topImageView.setImage(topIamge);
		topImageView.setFitHeight(50);
		topImageView.setFitWidth(100);
		
		topImageView.setRotate(20);
		
		VBox box = new VBox();
		box.getChildren().add(topImageView);
		return box;

	}

	@Override

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setWidth(600);
		primaryStage.setHeight(350);
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}