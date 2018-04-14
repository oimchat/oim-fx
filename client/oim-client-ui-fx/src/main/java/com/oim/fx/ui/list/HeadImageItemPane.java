/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author XiaHui
 */
public class HeadImageItemPane extends StackPane {

	private final StackPane basePane = new StackPane();
	private final Rectangle baseClip = new Rectangle();

	private final StackPane showPane = new StackPane();

	private final StackPane imagePane = new StackPane();
	private final ImageView imageView = new ImageView();
	private final Rectangle imageClip = new Rectangle();
	private final StackPane imageCoverPane = new StackPane();

	private ImageView redImageView = new ImageView();
	
	private WritableImage grayImage = null;
	private Image normalImage = null;
	private boolean gray = false;

	public HeadImageItemPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(basePane);

		VBox b=new VBox();
		b.setAlignment(Pos.BOTTOM_RIGHT);
		b.getChildren().add(redImageView);
		this.getChildren().add(b);
		
		
		imagePane.setStyle("-fx-background-color:rgba(255,255,255,0.1)");
		imagePane.setClip(imageClip);
		imagePane.getChildren().add(imageView);
		imagePane.getChildren().add(imageCoverPane);
		
		

		showPane.setPadding(new Insets(2, 2, 2, 2));
		showPane.getStyleClass().add("head-image-show");
		showPane.getChildren().add(imagePane);

		basePane.setClip(baseClip);
		basePane.setPadding(new Insets(1, 1, 1, 1));
		basePane.getStyleClass().add("head-image-pane");
		basePane.getChildren().add(showPane);
		

	}

	private void iniEvent() {
		// StringBuilder style=new StringBuilder();
		// style.append("-fx-border-color: #4e8fe2;");
		// style.append("-fx-border-radius:5");
		// style.append("-fx-border-width:1;");
		// basePane.setOnMouseExited(m -> {
		// basePane.setStyle("-fx-border-color:null;");
		// });
		// basePane.setOnMouseEntered(m -> {
		// basePane.setStyle(style.toString());
		// });

		// imagePane.heightProperty().addListener(l -> {
		// if (newImage && !useImageSize) {
		// newImage = false;
		//
		// double width = imagePane.getWidth();
		// double height = imagePane.getHeight();
		//
		// if (width > 0 && height > 0) {
		// imageWidth = (int) width;
		// imageHeight = (int) height;
		// // setGrayImage(normalImage);
		// }
		// }
		// });
	}

	public void setHeadSize(double value) {

		baseClip.setWidth(value + 6);
		baseClip.setHeight(value + 6);

		imageView.setFitHeight(value);
		imageView.setFitWidth(value);

		imageClip.setWidth(value);
		imageClip.setHeight(value);
	}

	public void setHeadRadius(double value) {
		baseClip.setArcHeight(value + 6);
		baseClip.setArcWidth(value + 6);

		imageClip.setArcHeight(value);
		imageClip.setArcWidth(value);
	}

	public void setHeadImage(Image image) {
		this.normalImage = image;
		imageView.setImage(image);
		setGrayImage(image);
	}

	private void setGrayImage(Image image) {
		if (null != image&&!image.isBackgroundLoading()) {
			int imageWidth = (int) image.getWidth();
			int imageHeight = (int) image.getHeight();
			if (imageWidth > 0 && imageHeight > 0) {
				PixelReader pixelReader = image.getPixelReader();
				grayImage = new WritableImage(imageWidth, imageHeight);
				PixelWriter pixelWriter = grayImage.getPixelWriter();
				for (int y = 0; y < imageHeight; y++) {
					for (int x = 0; x < imageWidth; x++) {
						Color color = pixelReader.getColor(x, y);
						color = color.grayscale();
						pixelWriter.setColor(x, y, color);
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
	
	public void setRedImage(Image image) {
		redImageView.setImage(image);
	}
}
