/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.control;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ModalDialog {
	Stage stage = new Stage();//Initialize the Stage with type of modal
    public ModalDialog(Stage owner) {
    	Button button = new Button();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);//Set the owner of the Stage 
        stage.setTitle("Top Stage With Modality");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250, Color.LIGHTGREEN);

        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                stage.hide();
            }
        });

        button.setLayoutX(100);
        button.setLayoutY(80);
        button.setText("OK");
        root.getChildren().add(button);
        stage.setScene(scene);
    }
    
    public void show(){
    	stage.show();
    }

}
