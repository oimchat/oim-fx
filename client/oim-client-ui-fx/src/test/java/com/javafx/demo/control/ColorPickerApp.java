/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javafx.demo.control;

/**
 *
 * @author XiaHui
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * A sample that demonstrates the ColorPicker.
 */
public class ColorPickerApp extends Application {
    
    public Parent createContent() {
        final ColorPicker colorPicker = new ColorPicker(Color.GREEN);
        final Label coloredText = new Label("Colors");
        Font font = new Font(53);
        coloredText.setFont(font);
        final Button coloredButton = new Button("Colored Control");
        Color c = colorPicker.getValue();
        coloredText.setTextFill(c);
        coloredButton.setStyle(createRGBString(c));
        
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Color newColor = colorPicker.getValue();
                coloredText.setTextFill(newColor);
                coloredButton.setStyle(createRGBString(newColor));
            }
        });
        
        VBox outerVBox = new VBox(coloredText, coloredButton, colorPicker);
        outerVBox.setAlignment(Pos.CENTER);
        outerVBox.setSpacing(20);
        outerVBox.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
        
        return outerVBox;
    }
    
    private String createRGBString(Color c) {
        return "-fx-base: rgb(" + (c.getRed() * 255) + "," + (c.getGreen() * 255) + "," + (c.getBlue() * 255) + ");";
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
