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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author XiaHui
 */
public class FindGroupItem extends BorderPane {

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

	private final Label nameLabel = new Label();
	private final Label infoTextLabel = new Label();

	private final TextArea textArea = new TextArea();

	private Button addButton = new Button();

	public FindGroupItem() {
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

		textBaseRootPane.getChildren().add(getGapNode(12));
		textBaseRootPane.getChildren().add(infoPane);
		textBaseRootPane.getChildren().add(textRootPane);
		// textBaseRootPane.getChildren().add(addButton);
		// textBaseRootPane.getChildren().add(getGapNode(5));

		infoPane.getChildren().add(nameLabel);
		textRootPane.getChildren().add(infoTextLabel);

		nameLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:15px;");
		infoTextLabel.setStyle("-fx-text-fill:#888888;-fx-font-size:14px;");

		HBox hBox = new HBox();

		hBox.getChildren().add(headBaseRootPane);
		hBox.getChildren().add(textBaseRootPane);

		textArea.setEditable(false);
		textArea.setBorder(Border.EMPTY);
		textArea.getStyleClass().clear();

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(5, 5, 5, 5));
		vBox.getChildren().add(hBox);
		vBox.getChildren().add(textArea);

		HBox iconBox = new HBox();
		iconBox.getStyleClass().add("find-add-icon");
		iconBox.setMinSize(14, 15);

		addButton.setGraphic(iconBox);
		addButton.getStyleClass().add("find-add-button");
		addButton.setText("加群");

		HBox bottomBox = new HBox();
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setStyle("-fx-background-color:#e6e6e6");
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.getChildren().add(addButton);

		this.setStyle("-fx-border-color:#e6e6e6;-fx-border-width: 1px;");
		this.setCenter(vBox);
		this.setBottom(bottomBox);
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
			headImageView.setImage(image);
		}
	}

	public void setName(String name) {
		nameLabel.setText(name);
	}

	public void setShowText(String showText) {
		infoTextLabel.setText(showText);
	}

	public void setInfoText(String showText) {
		textArea.setText(showText);
	}

	public void setAddAction(EventHandler<ActionEvent> value) {
		addButton.setOnAction(value);
	}

	public void setNameAction(EventHandler<? super MouseEvent> value) {
		nameLabel.setOnMouseClicked(value);
	}

	private Node getGapNode(double value) {
		AnchorPane tempPane = new AnchorPane();
		tempPane.setPrefWidth(value);
		tempPane.setPrefHeight(value);
		tempPane.setBackground(Background.EMPTY);
		return tempPane;
	}
}
