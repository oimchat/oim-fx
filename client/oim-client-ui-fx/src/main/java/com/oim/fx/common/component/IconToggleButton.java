package com.oim.fx.common.component;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class IconToggleButton extends ToggleButton {

	private static final String DEFAULT_STYLE_CLASS = "icon-toggle-button";
	
	private ImageView imageView = new ImageView();
	private Image normalImage = null;
	private Image hoverImage = null;
	private Image selectedImage = null;

	private ImageView redImageView = new ImageView();
	private StackPane stackPane=new StackPane();
	
	private boolean mouseEntered = false;

	public IconToggleButton() {
		super();
		this.setGraphic(stackPane);
		this.getStyleClass().clear();
	}

	public IconToggleButton(String text) {
		super(text);
		this.setGraphic(stackPane);
		this.getStyleClass().clear();
	}

	/**
	 * Creates a toggle button with the specified text and icon for its label.
	 *
	 * @param text
	 *            A text string for its label.
	 * @param graphic
	 *            the icon for its label.
	 */
	public IconToggleButton(String text, Node graphic) {
		super(text);
		this.setGraphic(stackPane);
		stackPane.getChildren().add(0, graphic);
		this.getStyleClass().clear();
	}

	public IconToggleButton(Image normalImage, Image hoverImage, Image selectedImage) {
		this("", null, null, normalImage, hoverImage, selectedImage);
	}

	public IconToggleButton(String text, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, null, null, normalImage, hoverImage, selectedImage);
	}

	public IconToggleButton(String text, Font font, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, font, null, normalImage, hoverImage, selectedImage);
	}

	public IconToggleButton(String text, Font font, Paint paint, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text);
		this.normalImage = normalImage;
		this.hoverImage = hoverImage;
		this.selectedImage = selectedImage;
		if (null != font) {
			this.setFont(font);
		}
		if (null != paint) {
			this.setTextFill(paint);
		}
		initEvent();
		updateImage();
		VBox redVBox=new VBox();
		redVBox.setAlignment(Pos.TOP_RIGHT);
		redVBox.getChildren().add(redImageView);
		
		stackPane.getChildren().add(imageView);
		stackPane.getChildren().add(redVBox);
		
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
	}

	private void initEvent() {
		this.setOnMouseEntered((Event event) -> {
			mouseEntered = true;
			updateImage();
		});
		this.setOnMouseExited((Event event) -> {
			mouseEntered = false;
			updateImage();
		});
		this.selectedProperty().addListener(s -> {
			updateImage();
		});
	}

	private void updateImage() {
		if (!this.isSelected()) {
			if (mouseEntered) {
				imageView.setImage(hoverImage);
			} else {
				imageView.setImage(normalImage);
			}
		} else {
			imageView.setImage(selectedImage);
		}
	}

	/**
	 * @return the normalImage
	 */
	public Image getNormalImage() {
		return normalImage;
	}

	/**
	 * @param normalImage
	 *            the normalImage to set
	 */
	public void setNormalImage(Image normalImage) {
		this.normalImage = normalImage;
		updateImage();
	}

	/**
	 * @return the hoverImage
	 */
	public Image getHoverImage() {
		return hoverImage;
	}

	/**
	 * @param hoverImage
	 *            the hoverImage to set
	 */
	public void setHoverImage(Image hoverImage) {
		this.hoverImage = hoverImage;
		updateImage();
	}

	/**
	 * @return the selectedImage
	 */
	public Image getSelectedImage() {
		return selectedImage;
	}

	/**
	 * @param selectedImage
	 *            the selectedImage to set
	 */
	public void setSelectedImage(Image selectedImage) {
		this.selectedImage = selectedImage;
		updateImage();
	}
	
	public void setRedImage(Image image) {
		redImageView.setImage(image);
	}
}
