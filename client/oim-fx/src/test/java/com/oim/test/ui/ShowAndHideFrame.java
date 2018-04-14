/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.fx.common.component.BaseStage;
import com.oim.fx.ui.MainFrame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Only
 */
public class ShowAndHideFrame extends BaseStage {

    Button loginButton = new Button();
    MainFrame mainFrame=new MainFrame();

    public ShowAndHideFrame() {
        init();
    }

    private void init() {

        loginButton.setText("点击");
        loginButton.setLayoutX(220);
        loginButton.setLayoutY(160);
        loginButton.setPrefWidth(60);

        AnchorPane userPane = new AnchorPane();
        userPane.setPrefWidth(240);
        userPane.setPrefHeight(180);

        userPane.getChildren().addAll(loginButton);


        this.setTitle("登录");
        this.setWidth(320);
        this.setHeight(260);
        this.setCenter(userPane);
        this.setBackground("Resources\\Images\\Wallpaper\\1.jpg");
    	
    	
        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	if(mainFrame.isShowing()){
            		mainFrame.hide();
            	}else{
            		mainFrame.show();
            	}
            }
        });
    }
}
