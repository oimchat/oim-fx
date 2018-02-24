/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.BaseStage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ImageViewFrame extends BaseStage {

    public ImageViewFrame() {
        init();
    }

    private void init() {

        VBox rootVBox = new VBox();

        this.setCenter(rootVBox);
        this.setBackground("Resources/Images/Wallpaper/1.jpg");
        this.setTitle("登录");
        this.setWidth(380);
        this.setHeight(600);
        this.setRadius(10);

        StackPane buttonPane = new StackPane();
        buttonPane.setStyle("-fx-background-color:rgba(189, 158, 152, 1)");

        Image image = ImageBox.getImagePath("Resources/Images/Head/User/1.png", 40, 40);
        ImageView headImageView = new ImageView();
        headImageView.setImage(image);
        
        buttonPane.getChildren().add(headImageView);
        
        rootVBox.getChildren().add(buttonPane);
    }
}
