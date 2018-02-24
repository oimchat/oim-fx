package com.oim.test.ui.multiple;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.multiple.QuickResponseCodeLoginStage;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author XiaHui
 * @date 2017-12-16 10:55:02
 */
public class QuickResponseCodeLoginStageTest extends Application {

	QuickResponseCodeLoginStage stage = new QuickResponseCodeLoginStage();

	@Override
	public void start(Stage primaryStage) {
		stage.setTitle("扫描二维码登录");;
		stage.setRadius(3);
		stage.setResizable(false);
		stage.setTitlePaneStyle(2);
		stage.getScene().getStylesheets().add(this.getClass().getResource("/multiple/css/main.css").toString());
		Image image=ImageBox.getImagePath("E:/Temp/WeChat/QR.jpg");
		stage.setImage(image);
		stage.show();
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
