/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui;

import com.only.fx.OnlyFrame;
import com.only.fx.OnlyPopupOver;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class WebViewFrame extends OnlyFrame {

	VBox rootVBox = new VBox();

	VBox topBox = new VBox();
	VBox centerBox = new VBox();

	OnlyPopupOver po = new OnlyPopupOver();
	Button button = new Button("弹出");

	public WebViewFrame() {
		init();
		initEvent();
	}

	private void init() {

		this.setTitle("测试");
		this.setWidth(380);
		this.setHeight(300);
		this.setRadius(10);

		this.setCenter(rootVBox);

		topBox.setPrefHeight(20);

		rootVBox.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");
		rootVBox.getChildren().add(topBox);
		rootVBox.getChildren().add(centerBox);

		button.setPrefHeight(70);
		button.setPrefWidth(200);
		button.setLayoutX(20);
		button.setLayoutY(140);

		centerBox.getChildren().add(button);

		// po.setDetached(false);
		// po.setDetachable(false);
		po.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_CENTER);
		po.setContentNode(createContent());
		po.setArrowSize(0);

		StackPane sp = new StackPane();

		// autoCheckBox.setFocusTraversable(true);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setMinWidth(150);
		scrollPane.setPrefWidth(250);
		scrollPane.setPrefHeight(360);

		scrollPane.setContent(sp);
		// scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.widthProperty().addListener((Observable observable) -> {
			// sp.setPrefWidth(scrollPane.getWidth()-20);
		});

	}

	private void initEvent() {
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// po.show(button4,1d,2d);
				po.show(WebViewFrame.this, WebViewFrame.this.getX(), WebViewFrame.this.getY());

			}
		});
	}

	public Parent createContent() {
		Image ICON_48 = new Image(WebViewFrame.class.getResourceAsStream("/resources/common/images/logo/logo_1.png"));
		TilePane tilePane = new TilePane();
		tilePane.setPrefColumns(2); // preferred columns
		tilePane.setAlignment(Pos.CENTER);
		Button[] buttons = new Button[6];

		for (int j = 0; j < buttons.length; j++) {
			buttons[j] = new Button("button" + (j + 1), new ImageView(ICON_48));
			tilePane.getChildren().add(buttons[j]);
		}

		return tilePane;
	}
}
