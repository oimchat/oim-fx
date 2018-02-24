/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

//import java.io.File;

import com.oim.fx.ui.LoginFrame;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class LoginFrameTest extends Application {
	LoginFrame l=  new LoginFrame();
    @Override
    public void start(Stage primaryStage) {
    	
        l.show();
    	//File file = new File("Resources/Video/Login/login.mp4");
    	//File file = new File("Resources/Video/Login/Swf/1/main.swf");
    	//l.setVideo(file);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
