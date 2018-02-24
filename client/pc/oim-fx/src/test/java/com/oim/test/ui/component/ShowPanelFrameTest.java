/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class ShowPanelFrameTest extends Application {
	ShowPanelFrame showPanelFrame = new ShowPanelFrame();

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
