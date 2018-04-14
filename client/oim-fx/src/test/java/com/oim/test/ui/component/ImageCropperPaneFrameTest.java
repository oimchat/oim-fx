package com.oim.test.ui.component;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author: XiaHui
 * @date: 2017年4月11日 上午10:41:04
 */
public class ImageCropperPaneFrameTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        new ImageCropperPaneFrame().show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}