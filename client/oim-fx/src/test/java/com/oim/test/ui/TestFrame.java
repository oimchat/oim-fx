/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oim.fx.common.component.BaseStage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Only
 */
public class TestFrame extends BaseStage {

    Button loginButton = new Button();
    Button settingButton = new Button();
    TextField accountField = new TextField();
    PasswordField passwordField = new PasswordField();
    private final GaussianBlur gaussianBlur = new GaussianBlur();

    public TestFrame() {
        init();
    }

    private void init() {
        try {
            Image iamge = new Image(new File("Resources\\Images\\Wallpaper\\1.jpg").toURI().toURL().toString(), true);
            ImageView imageView = new ImageView();

            imageView.setImage(iamge);
            imageView.setEffect(gaussianBlur);

            loginButton.setText("登录");
            settingButton.setText("设置");

            accountField.setPromptText("账号");
            passwordField.setPromptText("密码");

            accountField.setLayoutX(60);
            passwordField.setLayoutX(60);

            accountField.setLayoutY(60);
            passwordField.setLayoutY(100);

            accountField.setPrefWidth(200);
            passwordField.setPrefWidth(200);

            loginButton.setLayoutX(220);
            settingButton.setLayoutX(30);

            settingButton.setLayoutY(160);
            loginButton.setLayoutY(160);

            settingButton.setPrefWidth(60);
            loginButton.setPrefWidth(60);

            AnchorPane userPane = new AnchorPane();
            userPane.setPrefWidth(240);
            userPane.setPrefHeight(180);

            userPane.getChildren().addAll(accountField, passwordField);
            userPane.getChildren().addAll(loginButton, settingButton);

//            DropShadow dropShadow = new DropShadow();
//            AnchorPane rootPane = new AnchorPane();
//            rootPane.setId("bg");
//            rootPane.setEffect(dropShadow);
            // rootPane.setPrefWidth(320);
            // rootPane.setPrefHeight(220);
            BorderPane borderPane = new BorderPane();

            //borderPane.setBackground(Background.EMPTY);

//            
//            OnlyScene scene = new OnlyScene(borderPane);
//            scene.getStylesheets().add(this.getClass().getResource("/resources/css/base.css").toString());
            this.setTitle("登录");
            // this.setResizable(false);
            this.setWidth(320);
            this.setHeight(260);
            // this.setScene(scene);
            this.setCenter(userPane);
            this.show();
            this.setBackground("Resources\\Images\\Wallpaper\\1.jpg");

            StackPane userPane1 = new StackPane();
            userPane1.setPrefWidth(5);
            userPane1.setPrefHeight(5);
            userPane1.setBackground(Background.EMPTY);

            StackPane userPane2 = new StackPane();
            userPane2.setPrefWidth(5);
            userPane2.setPrefHeight(5);
            userPane2.setBackground(Background.EMPTY);

            AnchorPane userPane3 = new AnchorPane();
            userPane3.setPrefWidth(5);
            userPane3.setPrefHeight(5);
            userPane3.setBackground(Background.EMPTY);

            AnchorPane userPane4 = new AnchorPane();
            userPane4.setPrefWidth(5);
            userPane4.setPrefHeight(5);
            userPane4.setBackground(Background.EMPTY);

//            Move move = new Move(this, scene);
//            WindowButtons windowButtons = move.getWindowButtons();
            // rootPane.getChildren().add(userPane);
            // rootPane.getChildren().add(windowButtons);
            // borderPane.setCenter(rootPane);
            borderPane.setTop(userPane1);
            borderPane.setRight(userPane2);
            borderPane.setLeft(userPane3);
            borderPane.setBottom(userPane4);

            this.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.out.println(".handle()");
                }
            });
        } catch (MalformedURLException ex) {
            Logger.getLogger(TestFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

    }
}
