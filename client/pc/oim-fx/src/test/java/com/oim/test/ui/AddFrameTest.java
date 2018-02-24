/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.AddFrame;
import com.oim.ui.fx.classics.add.InfoPane;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class AddFrameTest extends Application {

	AddFrame auf=new AddFrame();
    @Override
    public void start(Stage primaryStage) {
    	auf.show();
    	
    	InfoPane ip=auf.getInfoPanel();
    	Image image = ImageBox.getImagePath("Resources/Images/Head/User/" + 1 + "_100.gif");
    	ip.setHeadImage(image);
		ip.setName("哈加额hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		ip.setNumber("10089");
		
		auf.addCategory("100", "我的好友");
		auf.addCategory("101", "444好友");
		auf.addCategory("102", "154");
		
		auf.selectCategory(0);
		auf.setDoneAction(d->{
			System.out.println(auf.getCategoryId());
		});
		auf.setNewCategoryAction(name->{
			System.out.println(name);
		});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
