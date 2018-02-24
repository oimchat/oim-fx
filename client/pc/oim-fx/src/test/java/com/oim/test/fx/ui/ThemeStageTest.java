package com.oim.test.fx.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.ui.fx.classics.ThemeStage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class ThemeStageTest extends Application {

	ThemeStage themeStage = new ThemeStage();

	@Override
	public void start(Stage primaryStage) {
		themeStage.show();
		//themeStage.setBackgroundOpacity(0.8);
		themeStage.setColor(Color.rgb(255, 255, 255, 0.4));
		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations = 0;
				initData();
				return iterations;
			}
		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	private void initData() {
		
		double imageWidth = 130D;
		double imageHeight = 80D;
		
		List<IconButton> list = new ArrayList<IconButton>();
		for (int i = 0; i < 26; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			Image normalImage = ImageBox.getImagePath(path);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a->{
				themeStage.setBackground(path);
			});
			list.add(isb);
		}

		for (int i = 101; i < 111; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			Image normalImage = ImageBox.getImagePath(path);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a->{
				themeStage.setBackground(path);
			});
			list.add(isb);
		}

		for (int i = 201; i < 224; i++) {
			String path = "Resources/Images/Wallpaper/" + i + ".jpg";

			Image normalImage = ImageBox.getImagePath(path);
			IconButton isb = new IconButton();
			isb.setNormalImage(normalImage);
			isb.setImageSize(imageWidth, imageHeight);
			isb.setOnAction(a->{
				themeStage.setBackground(path);
			});
			list.add(isb);
		}
		
		String path = "Resources/Images/Wallpaper/default.jpg";
		

		Image normalImage = ImageBox.getImagePath(path);
		IconButton isb = new IconButton();
		isb.setNormalImage(normalImage);
		isb.setImageSize(imageWidth, imageHeight);
		isb.setOnAction(a->{
			themeStage.setBackground(path);
		});
		list.add(isb);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				themeStage.setImageStyleButtonList(list);
			}
		});
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
