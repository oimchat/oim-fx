/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.list;

import com.oim.fx.common.box.ImageBox;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ListNodePane extends StackPane {

	private final BorderPane baseBorderPane = new BorderPane();

	private final HBox topBox = new HBox();
	private final VBox box = new VBox();

	private final Label textLabel = new Label();
	private final Label numberLabel = new Label();

	private final Image closed = ImageBox.getImageClassPath("/classics/images/list/closed.png");
	private final Image open = ImageBox.getImageClassPath("/classics/images/list/open.png");

	private final ImageView imageView = new ImageView();
	private final BorderPane imagePane = new BorderPane();

	private boolean isShow = false;

	public ListNodePane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setBackground(Background.EMPTY);

		HBox hBox = new HBox();

		hBox.setPadding(new Insets(8, 0, 0, 0));
		hBox.setSpacing(5);
		hBox.getChildren().add(textLabel);
		hBox.getChildren().add(numberLabel);

		imageView.setImage(closed);

		imagePane.setPrefWidth(30);
		imagePane.setPrefHeight(30);
		imagePane.setCenter(imageView);

		topBox.getChildren().add(imagePane);
		topBox.getChildren().add(hBox);
		topBox.getStyleClass().add("list-top-title");

		textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");
		textLabel.setStyle(" -fx-text-fill:#000000;-fx-font-size:13px;");

		baseBorderPane.setTop(topBox);
		this.getChildren().add(baseBorderPane);
	}

	private void iniEvent() {
		topBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					if (isShow) {
						isShow = false;
						imageView.setImage(closed);
						baseBorderPane.setCenter(null);
						// ListNodePane.this.getChildren().remove(box);
					} else {
						isShow = true;
						imageView.setImage(open);
						baseBorderPane.setCenter(box);
						// ListNodePane.this.getChildren().add(box);
					}
				}
			}
		});
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void setNumberText(String text) {
		numberLabel.setText(text);
	}

	public void addItem(Node node) {
		if (!box.getChildren().contains(node)) {
			box.getChildren().add(node);
		}
	}

	public int itemSize() {
		return box.getChildren().size();
	}

	public void removeItem(Node node) {
		box.getChildren().remove(node);
	}

	public void clearItem() {
		box.getChildren().clear();
	}
}
