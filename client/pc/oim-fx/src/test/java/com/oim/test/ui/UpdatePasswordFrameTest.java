/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.ui.fx.classics.UpdatePasswordFrame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class UpdatePasswordFrameTest extends Application {
	UpdatePasswordFrame l=  new UpdatePasswordFrame();
    @Override
    public void start(Stage primaryStage) {
    	
        l.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
