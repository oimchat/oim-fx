/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.fx.v1;

import java.io.File;

import com.oim.ui.fx.classics.LoginStage;
import com.sun.javafx.application.PlatformImpl;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class LoginStageTest extends Application {
	
	LoginStage stage = new LoginStage();

	@Override
	public void start(Stage primaryStage) {
		stage.setTitle("登录");
		stage.setResizable(false);
		//stage.setWidth(445);
		//stage.setHeight(345);
		stage.setTitlePaneStyle(2);
		stage.setRadius(5);
		
		stage.show();
		File file = new File("Resources/login-head.mp4");
		stage.setVideo(file);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
		PlatformImpl.setTaskbarApplication(false);
	}
}
