/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.server.api.tools.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class MainStageTest extends Application {

	MainStage stage = new MainStage();

	@Override
	public void start(Stage primaryStage) {
		stage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
