/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.fx.common.component;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
public class FlatTab extends StackPane {

	private BorderPane borderPane = new BorderPane();
	private StackPane stackPane = new StackPane();

	private ImageView imageView = new ImageView();
	private Button imageButton = new Button();

	private Image normalImage = null;
	private Image hoverImage = null;
	private Image selectedImage = null;
	private boolean selected = false;

	private boolean mouseEntered = false;
	private boolean bottomChange = true;
	private double bottomLineSize = 2.0;
	private Side side = Side.BOTTOM;
	private HBox hBox = new HBox();

	public FlatTab() {
		initComponent();
		iniEvent();
	}

	public FlatTab(Image normalImage, Image hoverImage, Image selectedImage) {
		this("", null, null, normalImage, hoverImage, selectedImage);
	}

	public FlatTab(String text, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, null, null, normalImage, hoverImage, selectedImage);
	}

	public FlatTab(String text, Font font, Image normalImage, Image hoverImage, Image selectedImage) {
		this(text, font, null, normalImage, hoverImage, selectedImage);
	}

	public FlatTab(String text, Font font, Paint paint, Image normalImage, Image hoverImage, Image selectedImage) {
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
		updateBottomLine();
	}

	private void initComponent() {
		imageButton.getStyleClass().clear();
		imageButton.setBackground(Background.EMPTY);
		imageButton.setBorder(Border.EMPTY);
		imageButton.setFocusTraversable(false);
		imageButton.setGraphic(imageView);

		stackPane.getChildren().add(imageButton);
		borderPane.setCenter(stackPane);
		
		this.setIconPadding(new Insets(5,0,5,0));
		this.getChildren().add(borderPane);
		
		// hBox.setStyle("-fx-background-color:rgba(194, 194, 173, 1)");
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
		this.side = side;
		updateBottomLine();
	}

	private void updateBottomLine() {
		if (Side.TOP == side) {
			borderPane.setTop(hBox);
			hBox.setMinHeight(bottomLineSize);
		} else if (Side.RIGHT == side) {
			borderPane.setRight(hBox);
			hBox.setMinWidth(bottomLineSize);
		} else if (Side.BOTTOM == side) {
			borderPane.setBottom(hBox);
			hBox.setMinHeight(bottomLineSize);
		} else if (Side.LEFT == side) {
			borderPane.setLeft(hBox);
			hBox.setMinWidth(bottomLineSize);
		} else {
			borderPane.setBottom(hBox);
			hBox.setMinHeight(bottomLineSize);
		}
	}

	private void updateImage() {
		if (!this.isSelected()) {
			if (mouseEntered) {
				imageView.setImage(hoverImage);
			} else {
				imageView.setImage(normalImage);
			}
			if (bottomChange) {
				hBox.setStyle("-fx-background-color:rgba(194, 194, 173, 1)");
			} else {
				hBox.setStyle("-fx-background-color:null");
			}
		} else {
			imageView.setImage(selectedImage);
			if (bottomChange) {
				hBox.setStyle("-fx-background-color:rgba(62, 136, 245, 1)");
			} else {
				hBox.setStyle("-fx-background-color:null");
			}
		}
	}

	public void setIconPadding(Insets value) {
		stackPane.setPadding(value);
	}

	public boolean isBottomChange() {
		return bottomChange;
	}

	public void setBottomChange(boolean bottomChange) {
		this.bottomChange = bottomChange;
		updateImage();
	}

	public double getBottomLineSize() {
		return bottomLineSize;
	}

	public void setBottomLineSize(double bottomLineSize) {
		this.bottomLineSize = bottomLineSize;
		updateBottomLine();
	}

	public Side getSide() {
		return side;
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
