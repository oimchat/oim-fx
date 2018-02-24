/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.oim.fx.common.box.ImageBox;
import com.oim.ui.fx.classics.FileTransferFrame;
import com.oim.ui.fx.classics.file.FileDownItem;
import com.oim.ui.fx.classics.file.FileUpItem;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class FileTransferFrameTest extends Application {

    @Override
    public void start(Stage primaryStage) {
    	FileTransferFrame l=  new FileTransferFrame();
        l.show();
        
        String name="123.mp4";
        String size="134mb/450mb";
        String speed="124kb/s";
        Image image=ImageBox.getImageClassPath("/resources/common/images/file/default.png");
        FileUpItem fui=new FileUpItem();
        fui.setName(name);
        fui.setImage(image);
        fui.setSize(size);
        fui.setSpeed(speed);
        fui.setProgress(25);
        l.addNode(fui);
        
        FileDownItem fdi=new FileDownItem();
        fdi.setName(name);
        fdi.setImage(image);
        fdi.setSize(size);
        fdi.setSpeed(speed);
        fdi.setProgress(25);
        fdi.showSaveAs(false);
        l.addNode(fdi);
        
        fdi=new FileDownItem();
        fdi.setName(name);
        fdi.setImage(image);
        fdi.setSize(size);
        fdi.setSpeed(speed);
        fdi.setProgress(25);
        fdi.showSaveAs(true);
        l.addNode(fdi);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
