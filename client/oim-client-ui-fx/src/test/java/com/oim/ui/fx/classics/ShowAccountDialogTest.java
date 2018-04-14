/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class ShowAccountDialogTest extends Application {
	ShowAccountDialog stage;

	@Override
	public void start(Stage primaryStage) {
		//primaryStage.show();
		stage = new ShowAccountDialog(primaryStage);
		stage.show();
		// stage.setBackground("/images/cropper/1.jpg");
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
