/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.find;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author XiaHui
 */
public class FindUserItem extends HBox {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private final StackPane headBaseRootPane = new StackPane();
	private final BorderPane headRootPane = new BorderPane();
	private final AnchorPane headImageShowPane = new AnchorPane();
	private final AnchorPane headImagePane = new AnchorPane();
	private final ImageView headImageView = new ImageView();
	private final Rectangle headClip = new Rectangle();
	private final AnchorPane pane = new AnchorPane();

	private final VBox textBaseRootPane = new VBox();
	private final HBox textRootPane = new HBox();
	private final HBox infoPane = new HBox();

	private final Label nicknameLabel = new Label();
	private final Label textLabel = new Label();

	private Button addButton = new Button();

	private WritableImage grayImage = null;
	private Image normalImage = null;
	private boolean gray = false;

	public FindUserItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {

		headImageView.setFitHeight(60);
		headImageView.setFitWidth(60);

		headClip.setArcHeight(60);
		headClip.setArcWidth(60);

		headClip.setWidth(60);
		headClip.setHeight(60);

		headImagePane.setClip(headClip);
		headImagePane.getChildren().add(headImageView);
		headImagePane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		pane.getStyleClass().add("head-common-image-pane");

		pane.getChildren().add(headImagePane);
		pane.setPrefWidth(60);
		pane.setPrefHeight(60);
		pane.setLayoutX(2);
		pane.setLayoutY(2);

		headImageShowPane.getChildren().add(pane);

		headRootPane.setTop(getGapNode(3));
		headRootPane.setRight(getGapNode(5));
		headRootPane.setLeft(getGapNode(5));
		headRootPane.setBottom(getGapNode(3));
		headRootPane.setCenter(headImageShowPane);

		headBaseRootPane.getChildren().add(headRootPane);

		headBaseRootPane.setPrefWidth(75);
		headBaseRootPane.setMinWidth(75);

		HBox iconBox = new HBox();
		iconBox.getStyleClass().add("find-add-icon");
		iconBox.setMinSize(14, 15);

		addButton.setGraphic(iconBox);
		addButton.getStyleClass().add("find-add-button");
		addButton.setText("好友");

		textBaseRootPane.getChildren().add(getGapNode(12));
		textBaseRootPane.getChildren().add(infoPane);
		textBaseRootPane.getChildren().add(textRootPane);
		textBaseRootPane.getChildren().add(addButton);
		// textBaseRootPane.getChildren().add(getGapNode(5));

		infoPane.getChildren().add(nicknameLabel);
		textRootPane.getChildren().add(textLabel);

		nicknameLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:14px;");
		textLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:12px;");
		textLabel.setWrapText(true);
		
		this.getChildren().add(headBaseRootPane);
		this.getChildren().add(textBaseRootPane);
	}

	private void iniEvent() {

	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	public void setHeadImage(Image image) {
		if (null != image) {
			this.normalImage = image;
			headImageView.setImage(image);
			setGrayImage(image);
		}
	}

	private void setGrayImage(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		grayImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		PixelWriter pixelWriter = grayImage.getPixelWriter();

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Color color = pixelReader.getColor(x, y);
				color = color.grayscale();
				pixelWriter.setColor(x, y, color);
			}
		}
	}

	public boolean isGray() {
		return gray;
	}

	public void setGray(boolean gray) {
		this.gray = gray;
		if (gray) {
			headImageView.setImage(grayImage);
		} else {
			headImageView.setImage(normalImage);
		}
	}

	public void setNickname(String nickname) {
		nicknameLabel.setText(nickname);
	}

	public void setShowText(String showText) {
		textLabel.setText(showText);
	}

	public void setAddAction(EventHandler<ActionEvent> value) {
		addButton.setOnAction(value);
	}

	public void setNicknameAction(EventHandler<? super MouseEvent> value) {
		nicknameLabel.setOnMouseClicked(value);
	}

	private Node getGapNode(double value) {
		AnchorPane tempPane = new AnchorPane();
		tempPane.setPrefWidth(value);
		tempPane.setPrefHeight(value);
		tempPane.setBackground(Background.EMPTY);
		return tempPane;
	}
}
