package com.only.fx.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.oim.fx.common.component.ScreenShotFrame;
import com.only.common.util.OnlyFileUtil;
import com.only.fx.common.action.EventAction;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScreenShotFrameTest extends Application {

	ScreenShotFrame frame = new ScreenShotFrame();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();

		

		EventAction<Image> action = new EventAction<Image>() {

			@Override
			public void execute(Image image) {
				save(image);
			}
		};
		frame.setOnImageAction(action);
	}

	public Parent createContent() {
		Button button = new Button("Screen Shot");
		button.setOnAction(a->{
			frame.setVisible(true);
		});
		
		VBox outerVBox = new VBox(button);
		outerVBox.setAlignment(Pos.CENTER);
		outerVBox.setSpacing(20);
		outerVBox.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);
		return outerVBox;
	}

	public void save(Image image) {
		try {

			String filePath = "Data/Temp/temp.png";
			OnlyFileUtil.checkOrCreateFolder(filePath);
			File file = new File(filePath);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

		} catch (IOException ex) {
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
