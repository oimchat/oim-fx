/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.oim.ui.fx.classics.UserHeadUploadFrame;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Only
 */
public class UserHeadUploadFrameTest extends Application {

	UserHeadUploadFrame f = new UserHeadUploadFrame();

	@Override
	public void start(Stage primaryStage) {
		f.show();

		f.setDoneAction(a -> {
			boolean v = f.verify();
			if (v) {
				Image image = f.getImage();
				save(image);
			}
		});
	}

	public void save(Image image) {
		try {
			File file = new File("Resources/Temp/1.png");
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			f.showPrompt("保存成功!");
		} catch (IOException ex) {
			f.showPrompt("保存失败:" + ex.getMessage());
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
