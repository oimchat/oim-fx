/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.InfoFrame;
import com.oim.ui.fx.classics.add.InfoPane;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class InfoFrameTest extends Application {

	InfoFrame auf=new InfoFrame();
    @Override
    public void start(Stage primaryStage) {
    	auf.show();
    	
    	InfoPane ip=auf.getInfoPane();
    	Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + 1 + "_100.gif");
    	ip.setHeadImage(image);
		ip.setName("哈加额hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		ip.setNumber("10089");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
