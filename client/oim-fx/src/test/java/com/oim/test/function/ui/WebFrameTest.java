/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.function.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class WebFrameTest extends Application {
	WebFrame wf = new WebFrame();

	@Override
	public void start(Stage primaryStage) {
		wf.show();
		wf.load("http://rj.baidu.com/soft/detail/13478.html?ald&qq-pf-to=pcqq.c2c");
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
