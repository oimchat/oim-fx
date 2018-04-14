/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author XiaHui
 */
public class HeadImagePanel extends BorderPane {

	// private final DropShadow dropShadow = new DropShadow();
	private final StackPane rootPane = new StackPane();
	private final StackPane imagePane = new StackPane();
	private final ImageView imageView = new ImageView();
	private final Rectangle clip = new Rectangle();

	private final StackPane imageCoverPane = new StackPane();

	private WritableImage grayImage = null;
	private Image normalImage = null;
	private boolean gray = false;

	public HeadImagePanel() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getStyleClass().add("head-common-image-pane");
		this.setCenter(rootPane);
		// this.getChildren().add(headImageShowPane);

		rootPane.getChildren().add(imagePane);
		// rootPane.setPadding(new Insets(5, 5, 5, 5));

		// clip.setArcHeight(8);
		// clip.setArcWidth(8);
		setHeadRadius(8);

		imagePane.getChildren().add(imageView);
		imagePane.getChildren().add(imageCoverPane);

		// imagePane.setStyle("-fx-background-color: rgba(255, 255, 255,
		// 0.2);");
		imagePane.setClip(clip);
		// imageView.fitWidthProperty().addListener((Observable observable) -> {
		// clip.setWidth(imageView.getFitWidth());
		// });
		// imageView.fitHeightProperty().addListener((Observable observable) ->
		// {
		// clip.setHeight(imageView.getFitHeight());
		// });

	}

	private void iniEvent() {
		// this.setOnMouseEntered((Event event) -> {
		// HeadImagePanel.this.setEffect(dropShadow);
		// });
		// this.setOnMouseExited((Event event) -> {
		// HeadImagePanel.this.setEffect(null);
		// });
	}

	public void setImage(Image image) {
		this.normalImage = image;
		imageView.setImage(image);
		setGrayImage(image);
	}

	private void setGrayImage(Image image) {
		if (null != image && !image.isBackgroundLoading()) {
			int imageWidth = (int) image.getWidth();
			int imageHeight = (int) image.getHeight();
			if (imageWidth > 0 && imageHeight > 0) {
				PixelReader pixelReader = image.getPixelReader();
				grayImage = new WritableImage(imageWidth, imageHeight);
				PixelWriter pixelWriter = grayImage.getPixelWriter();
				if (null != pixelWriter && null != pixelReader) {
					for (int y = 0; y < imageHeight; y++) {
						for (int x = 0; x < imageWidth; x++) {
							Color color = pixelReader.getColor(x, y);
							color = color.grayscale();
							pixelWriter.setColor(x, y, color);
						}
					}
				}
			} else {
				grayImage = null;
			}
		} else {
			grayImage = null;
		}
	}

	public boolean isGray() {
		return gray;
	}

	public void setGray(boolean gray) {
		this.gray = gray;
		if (gray) {
			if (null != grayImage) {
				imageView.setImage(grayImage);
			} else {
				imageCoverPane.setStyle("-fx-background-color:rgba(250,250,250,0.6)");
			}
		} else {
			imageCoverPane.setStyle("");
			imageView.setImage(normalImage);
		}
	}

	/**
	 * 设置头像大小
	 * 
	 * @author: XiaHui
	 * @param value
	 * @createDate: 2017年5月25日 下午5:59:25
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:59:25
	 */
	public void setHeadSize(double value) {
		imageView.setFitHeight(value);
		imageView.setFitWidth(value);

		clip.setWidth(value);
		clip.setHeight(value);
	}

	public void setHeadRadius(double value) {
		clip.setArcHeight(value);
		clip.setArcWidth(value);
	}

	// private void initTest() {
	// Image image =
	// ImageBox.getImagePath("Resources/Images/Head/User/1_100.gif", 60, 60);
	// setImage(image);
	// }
}
