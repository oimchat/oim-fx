/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.oim.ui.fx.classics.component;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author XiaHui
 */
public class HeadImageFrameTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        new HeadImageFrame().show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
