package com.oim.fx.ui.chat.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class MessageItemRight extends StackPane {

	BorderPane rootPane = new BorderPane();
	StackPane timePane = new StackPane();
	VBox heahBox = new VBox();
	VBox contentBox = new VBox();

	Label timeLabel = new Label();
	Label nameLabel = new Label();
	ImageView headImageView = new ImageView();

	BorderPane centerPane = new BorderPane();
	
	HBox centerBox = new HBox();
	VBox leftBox = new VBox();

	public MessageItemRight() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(rootPane);
		rootPane.setTop(timePane);
		rootPane.setLeft(leftBox);
		rootPane.setCenter(centerPane);
		rootPane.setRight(heahBox);
		
		leftBox.setPrefWidth(40);
		
		heahBox.setPrefHeight(20);
		timeLabel.setStyle("-fx-text-fill:#fff;"
				+ "-fx-background-color:#dadada;\r\n" + 
				"   -fx-background-radius:2;-fx-padding: 0 6px;");
		nameLabel.setStyle("-fx-text-fill:rgba(120, 120, 120, 1)");
		double value=38;
		Rectangle clip = new Rectangle();
		clip.setWidth(value);
		clip.setHeight(value);

		clip.setArcHeight(value);
		clip.setArcWidth(value);
		
		StackPane heahPane = new StackPane();
		heahPane.setClip(clip);
		heahPane.getChildren().add(headImageView);
		
		timePane.getChildren().add(timeLabel);
		heahBox.getChildren().add(heahPane);

		headImageView.setFitWidth(40);
		headImageView.setFitHeight(40);

		Polygon polygon = new Polygon(new double[] {

				0, 0,

				10, 10,

				0, 20,

		});

		polygon.setFill(Color.web("rgba(44, 217, 44, 1)"));
		//polygon.setStroke(Color.BLUE);
		//polygon.setStyle("-fx-background-color:rgba(245, 245, 245, 1);");
		VBox arrowBox = new VBox();
		arrowBox.setPadding(new Insets(3, 0, 0, 0));
		arrowBox.setAlignment(Pos.TOP_RIGHT);
		arrowBox.getChildren().add(polygon);
		
		contentBox.setStyle("-fx-background-color:rgba(44, 217, 44, 1);-fx-background-radius:3;");

		centerBox.setAlignment(Pos.CENTER_RIGHT);
		
		centerBox.getChildren().add(contentBox);
		centerBox.getChildren().add(arrowBox);
		
		HBox nameBox=new HBox();
		nameBox.setAlignment(Pos.TOP_RIGHT);
		nameBox.setPadding(new Insets(0, 10, 0, 10));
		nameBox.getChildren().add(nameLabel);
		
		centerPane.setTop(nameBox);
		centerPane.setCenter(centerBox);
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public void setTimeText(String timeText) {
		timeLabel.setText(timeText);
	}
	
	public void setName(String name) {
		nameLabel.setText(name);
	}

	public void setHeadImage(Image image) {
		headImageView.setImage(image);
	}

	public void addContentNode(Node node) {
		contentBox.getChildren().add(node);
	}
}
