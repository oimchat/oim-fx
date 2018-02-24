/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.control;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModalDialogDemo extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ModalDialogDemo.class, args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        ModalDialog md = new ModalDialog(primaryStage);
        
        Group root = new Group();
        Scene scene = new Scene(root, 500, 450, Color.LIGHTBLUE);
        Button btn = new Button();
        btn.setLayoutX(250);
        btn.setLayoutY(240);
        btn.setText("Show modal dialog");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            	md.show();
            }
        });
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
