/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.test.component;

import java.io.File;

import com.only.fx.OnlyFrame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 *
 * @author XiaHui
 */
public class FileFrame extends OnlyFrame {
	
	BorderPane rootPane = new BorderPane();
	private FileChooser fileChooser;
	public FileFrame() {
		init();
		fileChooser = new FileChooser();
	}

	private void init() {
		this.setCenter(rootPane);
		this.setTitle("登录");
		this.setWidth(380);
		this.setHeight(600);
		this.setRadius(10);

		Button button1 = new Button();

		button1.setText("点击");
		button1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				fileChooser.setInitialFileName("cc.text");
				File file=fileChooser.showSaveDialog(FileFrame.this);
				if(file!=null){
					System.out.println("getName:"+file.getName());
					System.out.println("isFile:"+file.isFile());
					System.out.println("isDirectory:"+file.isDirectory());
					System.out.println("getAbsolutePath:"+file.getAbsolutePath());
				}
			}
		});
		rootPane.setCenter(button1);
	}
}
