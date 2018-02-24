/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.ui.fx.classics.WebFrame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class WebFrameTest extends Application {
	WebFrame wlf=new WebFrame();
    @Override
    public void start(Stage primaryStage) {
    	wlf.show();
    	
    	String url=this.getClass().getResource("/resources/chat/html/write_3.html").toString();
    	//String url=this.getClass().getResource("/html/chat.html").toString();
    	wlf.load(url);
    	
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
