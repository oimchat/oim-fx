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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author XiaHui
 */
public class ImagePane extends StackPane {

	private final ImageView imageView = new ImageView();
	private final Rectangle clip = new Rectangle();

	private final StackPane imageCoverPane = new StackPane();

	private WritableImage grayImage = null;
	private Image normalImage = null;
	private boolean gray = false;

	public ImagePane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getStyleClass().add("head-common-image-pane");
		this.getChildren().add(imageView);
		this.getChildren().add(imageCoverPane);
		this.setClip(clip);

		setHeadRadius(8);
	}

	private void iniEvent() {
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
}
