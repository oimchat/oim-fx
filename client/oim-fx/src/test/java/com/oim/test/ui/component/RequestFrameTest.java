package com.oim.test.ui.component;

import com.oim.ui.fx.classics.RequestFrame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author XiaHui
 * @date 2017年8月23日 下午6:17:47
 */
public class RequestFrameTest extends Application {
	
	RequestFrame frame = new RequestFrame();

	@Override
	public void start(Stage primaryStage) {
		frame.show();
		frame.setTitle("请求远程控制");
		frame.setContent("XXXX请求控制您的电脑？");
		frame.setOnAcceptAction(a->{
			System.out.println("setAcceptAction");
		});
		frame.setOnRefuseAction(a->{
			System.out.println("setRefuseAction");
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
