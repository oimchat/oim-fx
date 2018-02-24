package com.oim.test.ui.component;

import java.io.File;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 上午10:41:04
 */
public class ImageTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		// String url="http://img.blog.csdn.net/20130507163037359";
		
//		try {
			
//			System.out.println(file.getAbsolutePath());
//			System.out.println(file.toURI());
//			System.out.println(file.toURI().toString());
//			System.out.println(file.toURI().toURL());
//			String pathString = file.toURI().toURL().toString();
			File file=new File("Resources/Temp/1.jpg");
			String pathString=file.toURI().toString();
			System.out.println(pathString);
			Image image = new Image(pathString,false);
			System.out.println(image.getHeight());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}