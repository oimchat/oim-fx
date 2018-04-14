/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author XiaHui
 */
public class Tab extends StackPane {

	BorderPane borderPane = new BorderPane();

	ImageView imageView = new ImageView();
	Button imageButton = new Button();

	private Image normalImage = null;
	private Image hoverImage = null;
	private Image selectedImage = null;
	private boolean selected = false;

	private boolean mouseEntered = false;

	private HBox hBox = new HBox();
	
	public Tab() {
		initComponent();
		iniEvent();
	}

	public Tab(Image normalImage, Image hoverImage, Image selectedImage) {
		this("", null, null, normalImage, hoverImage, selectedImage);
	}

	public Tab(String text, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, null, null, normalImage, hoverImage, selectedImage);
	}

	public Tab(String text, Font font, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, font, null, normalImage, hoverImage, selectedImage);
	}

	public Tab(String text, Font font, Paint paint, Image normalImage, Image hoverImage, Image selectedImage) {
		this.normalImage = normalImage;
		this.hoverImage = hoverImage;
		this.selectedImage = selectedImage;

		if (null != text && !"".equals(text)) {
			imageButton.setText(text);
		}
		if (null != font) {
			imageButton.setFont(font);
		}
		if (null != paint) {
			imageButton.setTextFill(paint);
		}
		initComponent();
		iniEvent();
		updateImage();
	}

	private void initComponent() {

		StackPane stackPane = new StackPane();
		this.getChildren().add(borderPane);
		borderPane.setCenter(stackPane);
		// borderPane.setBottom(hBox);
		// // vbox.getChildren().add(stackPane);
		//
		// // this.getChildren().add(imageButton);
		// hBox.setMinHeight(2);

		stackPane.getChildren().add(imageButton);
		imageButton.getStyleClass().clear();

		imageButton.setBackground(Background.EMPTY);
		imageButton.setBorder(Border.EMPTY);
		imageButton.setFocusTraversable(false);
		imageButton.setGraphic(imageView);

		hBox.setStyle("-fx-background-color:rgba(194, 194, 173, 1)");
		// imageButton.setOnAction(new EventHandler() {
		// @Override
		// public void handle(Event event) {
		//
		// }
		// });
		// setStyle("-fx-background-color:rgba(0, 0, 0, 1);");
		setSide(Side.BOTTOM);
	}

	public void setOnAction(EventHandler<ActionEvent> value) {
		imageButton.setOnAction(value);
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
	}

	public void setSide(Side side) {
		if (Side.TOP == side) {
			borderPane.setTop(hBox);
			hBox.setMinHeight(2);
		} else if (Side.RIGHT == side) {
			borderPane.setRight(hBox);
			hBox.setMinWidth(2);
		} else if (Side.BOTTOM == side) {
			borderPane.setBottom(hBox);
			hBox.setMinHeight(2);
		} else if (Side.LEFT == side) {
			borderPane.setLeft(hBox);
			hBox.setMinWidth(2);
		} else {
			borderPane.setBottom(hBox);
			hBox.setMinHeight(2);
		}
	}
	// private void mouseEntered() {
	// if (!this.isSelected()) {
	// imageView.setImage(hoverImage);
	// }
	// }
	//
	// private void mouseExited() {
	// if (!this.isSelected()) {
	// imageView.setImage(normalImage);
	// }
	// }

	private void updateImage() {

		if (!this.isSelected()) {
			if (mouseEntered) {
				imageView.setImage(hoverImage);
			} else {
				imageView.setImage(normalImage);
			}
			hBox.setStyle("-fx-background-color:rgba(194, 194, 173, 1)");
		} else {
			imageView.setImage(selectedImage);
			hBox.setStyle("-fx-background-color:rgba(62, 136, 245, 1)");
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

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		updateImage();
	}
}
