/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.common.component;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * 通用图标按钮
 * 
 * @author XiaHui
 */
public class IconPane extends BorderPane {

	private static final String DEFAULT_STYLE_CLASS = "icon-pane";
	
	private ImageView imageView = new ImageView();
	private Image normalImage = null;
	private Image hoverImage = null;
	private Image pressedImage = null;

	private StackPane baseStackPane = new StackPane();
	private boolean mouseEntered = false;
	private boolean mousePressed = false;
	private String text;
	private Label label = new Label();

	public IconPane() {
		initComponent();
		iniEvent();
	}

	public IconPane(Image normalImage) {
		this.normalImage = normalImage;
		initComponent();
		iniEvent();
	}

	public IconPane(String text, Image normalImage) {
		this.normalImage = normalImage;
		setText(text);
		initComponent();
		iniEvent();
	}

	public IconPane(Image normalImage, Image hoverImage, Image pressedImage) {
		this.normalImage = normalImage;
		this.hoverImage = hoverImage;
		this.pressedImage = pressedImage;
		initComponent();
		iniEvent();
	}

	public IconPane(String text, Image normalImage, Image hoverImage, Image pressedImage) {
		this.normalImage = normalImage;
		this.hoverImage = hoverImage;
		this.pressedImage = pressedImage;
		setText(text);
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		label.setGraphic(imageView);
		baseStackPane.getChildren().add(label);
		
		this.setCenter(baseStackPane);
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);
		updateImage();
	}

	private void iniEvent() {
		this.setOnMouseEntered((Event event) -> {
			mouseEntered = true;
			updateImage();
		});
		this.setOnMouseExited((Event event) -> {
			mouseEntered = false;
			updateImage();
		});
		this.setOnMousePressed((Event event) -> {
			mousePressed = true;
			updateImage();
		});
		this.setOnMouseReleased((Event event) -> {
			mousePressed = false;
			updateImage();
		});
	}

	

	private void updateImage() {

		if (!this.mousePressed) {
			if (mouseEntered) {
				if (null != hoverImage) {
					imageView.setImage(hoverImage);
				}
			} else if (null != normalImage) {
				imageView.setImage(normalImage);
			}
		} else if (null != pressedImage) {
			imageView.setImage(pressedImage);
		}
	}

	public Image getNormalImage() {
		return normalImage;
	}

	public void setNormalImage(Image normalImage) {
		this.normalImage = normalImage;
		updateImage();
	}

	public Image getHoverImage() {
		return hoverImage;
	}

	public void setHoverImage(Image hoverImage) {
		this.hoverImage = hoverImage;
		updateImage();
	}

	public Image getPressedImage() {
		return pressedImage;
	}

	public void setPressedImage(Image pressedImage) {
		this.pressedImage = pressedImage;
		updateImage();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		label.setText(text);
	}
}
