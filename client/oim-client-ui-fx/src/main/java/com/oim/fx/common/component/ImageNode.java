package com.oim.fx.common.component;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author: XiaHui
 * @date: 2018-01-16 20:38:46
 */
public class ImageNode extends Button {

	private static final String DEFAULT_STYLE_CLASS = "image-node";
	
	private final StackPane stackPane = new StackPane();
	private final ImageView imageView = new ImageView();
	private final Rectangle clip = new Rectangle();
	private final StackPane imageCoverPane = new StackPane();

	private Image grayImage = null;
	private Image normalImage = null;
	private boolean gray = false;

	public ImageNode() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setGraphic(stackPane);
		this.getStyleClass().remove("button");
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);

		stackPane.getChildren().add(imageView);
		stackPane.getChildren().add(imageCoverPane);
		stackPane.setClip(clip);
		/// this.getChildren().add(stackPane);
	}

	private void iniEvent() {
	}

	public void setImage(Image image) {
		this.normalImage = image;
		setGrayImage(image);
		updateImage();
	}

	private void setGrayImage(Image image) {
		grayImage = convertGrayImage(image);
	}

	public boolean isGray() {
		return gray;
	}

	public void setGray(boolean gray) {
		this.gray = gray;
		updateImage();
	}

	private void updateImage() {
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
	public void setImageSize(double value) {
		imageView.setFitHeight(value);
		imageView.setFitWidth(value);

		clip.setWidth(value);
		clip.setHeight(value);
	}

	public void setImageRadius(double value) {
		clip.setArcHeight(value);
		clip.setArcWidth(value);
	}

	private WritableImage convertGrayImage(Image image) {
		WritableImage grayImage = null;
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
			}
		}
		return grayImage;
	}
}
