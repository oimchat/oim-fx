/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A sample that demonstrates how to draw and paint shapes, apply visual
 * effects, blend colors in overlapping objects, and animate objects.
 */
public class ColorfulCirclesApp extends Application {

    private static final double WIDTH = 430, HEIGHT = 180;
    private Timeline animation;

    public ScrollPane createContent() {
        Pane layer1 = new Pane();
        for (int i = 0; i < 15; i++) {
            Circle circle = new Circle(200, Color.web("white", 0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.2f));
            circle.setStrokeWidth(4f);
            layer1.getChildren().add(circle);
        }
        // create second list of circles
        Pane layer2 = new Pane();
        for (int i = 0; i < 20; i++) {
            Circle circle = new Circle(70, Color.web("white", 0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.1f));
            circle.setStrokeWidth(2f);
            layer2.getChildren().add(circle);
        }
        // create third list of circles
        Pane layer3 = new Pane();
        for (int i = 0; i < 10; i++) {
            Circle circle = new Circle(150, Color.web("white", 0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white", 0.16f));
            circle.setStrokeWidth(4f);
            layer3.getChildren().add(circle);
        }
        // Set a blur effect on each layer
        layer1.setEffect(new BoxBlur(30, 30, 3));
        layer2.setEffect(new BoxBlur(2, 2, 2));
        layer3.setEffect(new BoxBlur(10, 10, 3));
        // create a rectangle size of window with colored gradient
        Rectangle colors = new Rectangle(WIDTH, HEIGHT,
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                        new Stop(0.84f, Color.web("#004882")),
                        new Stop(0.64f, Color.web("#004882")),
                        new Stop(0.78f, Color.web("#004882")),
                        new Stop(0.43f, Color.web("#004882")),
                        new Stop(0.57f, Color.web("#004882")),
                        new Stop(0.71f, Color.web("#004882")),
                        new Stop(0.85f, Color.web("#004882")),
                        new Stop(0.72f, Color.web("#004882"))));
        colors.setBlendMode(BlendMode.OVERLAY);
        // create main content
        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.setPrefSize(WIDTH, HEIGHT);

        Pane pane = new Pane();
        sp.setContent(pane);
        pane.getChildren().addAll(new Rectangle(WIDTH, HEIGHT, Color.BLACK),
                layer1,
                layer2,
                layer3,
                colors);
        Rectangle clip = new Rectangle(WIDTH, HEIGHT);
        clip.setSmooth(false);
        pane.setClip(clip);
        // create list of all circles
        List<Node> allCircles = new ArrayList<>();
        allCircles.addAll(layer1.getChildren());
        allCircles.addAll(layer2.getChildren());
        allCircles.addAll(layer3.getChildren());
        // Create a animation to randomly move every circle in allCircles
        animation = new Timeline();
        for (Node circle : allCircles) {
            animation.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, // set start position at 0s
                            new KeyValue(circle.translateXProperty(), random() * WIDTH),
                            new KeyValue(circle.translateYProperty(), random() * HEIGHT)),
                    new KeyFrame(new Duration(40000), // set end position at 40s
                            new KeyValue(circle.translateXProperty(), random() * WIDTH),
                            new KeyValue(circle.translateYProperty(), random() * HEIGHT)));
        }
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        return sp;
    }

    public void play() {
        animation.play();
    }

    @Override
    public void stop() {
        animation.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        play();
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
