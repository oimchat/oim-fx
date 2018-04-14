/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.fx.v1;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class ChatPaneFrameTest extends Application {
	ChatPaneFrame showPanelFrame = new ChatPaneFrame();

	@Override
	public void start(Stage primaryStage) {
		showPanelFrame.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
