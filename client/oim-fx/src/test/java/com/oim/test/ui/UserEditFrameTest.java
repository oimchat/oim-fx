/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.UserEditFrame;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class UserEditFrameTest extends Application {

    @Override
    public void start(Stage primaryStage) {
    	UserEditFrame l=  new UserEditFrame();
        l.show();
        Image image = ImageBox.getImagePath("Resources/Images/Head/User/85_100.gif");
        l.setHeadImage(image);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
