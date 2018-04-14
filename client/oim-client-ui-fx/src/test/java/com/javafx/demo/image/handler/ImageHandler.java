package com.javafx.demo.image.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageHandler extends Application {
	
	private ImageView imageView;
	private Image image;
	private WritableImage wImage;
	private FileChooser fileChooser;

	@Override
	public void start(final Stage primaryStage) {
		StackPane stackPane = new StackPane();
		Scene scene = new Scene(stackPane);

		VBox mVBox = new VBox(20);
		HBox mButtonsBox = new HBox(10);
		mButtonsBox.setAlignment(Pos.CENTER);

		Button bright = new Button("明亮");
		Button darker = new Button("深暗");
		Button gray = new Button("灰度处理");
		Button invert = new Button("颜色反转");
		Button saturate = new Button("增加饱和度");
		Button desaturate = new Button("减少饱和度");
		Button recover = new Button("还原图片");
		Button export = new Button("导出");

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("图片文件", "*.png", "*.jpg", "*.bmp", "*.gif"));
		bright.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(0);
			}
		});

		darker.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(1);
			}
		});

		gray.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(2);
			}
		});

		invert.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(3);
			}
		});

		saturate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(4);
			}
		});

		desaturate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pixWithImage(5);
			}
		});

		recover.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				imageView.setImage(image);
			}
		});

		export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showSaveDialog(primaryStage.getOwner());
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		mButtonsBox.getChildren().addAll(bright, darker, gray, invert, saturate, desaturate, recover, export);
		image = new Image("com/javafx/demo/image/handler/handle.jpg");
		imageView = new ImageView(image);
		imageView.setSmooth(true);
		imageView.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != imageView) {
					event.acceptTransferModes(TransferMode.ANY);
				}
			}
		});

		imageView.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();
				List<File> files = dragboard.getFiles();
				if (files.size() == 1) {
					File file = files.get(0);
					try {
						image = new Image(new FileInputStream(file));
						imageView.setImage(image);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});

		mVBox.getChildren().addAll(mButtonsBox, imageView);
		stackPane.getChildren().add(mVBox);

		primaryStage.setTitle("JavaFX示例--简易图片处理工具");
		primaryStage.setScene(scene);
		primaryStage.setWidth(600);
		primaryStage.setHeight(500);
		primaryStage.show();
	}

	private void pixWithImage(int type) {
		PixelReader pixelReader = imageView.getImage().getPixelReader();
		// Create WritableImage
		wImage = new WritableImage((int) image.getWidth(),(int) image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color color = pixelReader.getColor(x, y);
				switch (type) {
				case 0:
					color = color.brighter();
					break;
				case 1:
					color = color.darker();
					break;
				case 2:
					color = color.grayscale();
					break;
				case 3:
					color = color.invert();
					break;
				case 4:
					color = color.saturate();
					break;
				case 5:
					color = color.desaturate();
					break;
				default:
					break;
				}
				pixelWriter.setColor(x, y, color);
			}
		}
		imageView.setImage(wImage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}