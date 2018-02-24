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
public class UserRegisterFrameTest extends Application {
	UserRegisterFrame stage=new  UserRegisterFrame() ;
    @Override
    public void start(Stage primaryStage) {
    	stage.show();
    	//stage.setBackground("/images/cropper/1.jpg");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
