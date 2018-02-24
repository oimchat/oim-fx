package com.oim.fx.ui.chat;

import java.util.HashMap;
import java.util.Map;

import com.oim.fx.ui.list.HeadImagePanel;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ChatItem extends StackPane {
	
	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();
	HBox infoBox = new HBox();
	HBox box = new HBox();
	private final ImageView imageView = new ImageView();
	private final Rectangle clip = new Rectangle();
	private final VBox imageBox = new VBox();
	private boolean mouseEntered = false;
	private boolean selected = false;
	HeadImagePanel headImagePanel = new HeadImagePanel();
	//Image image;
	//StackPane tilePane = new StackPane();
	Label label = new Label();
	Button closeButton = new Button();

	public ChatItem() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(infoBox);
		this.getChildren().add(box);

		clip.setArcHeight(38);
		clip.setArcWidth(38);

		//imageView.setFitHeight(38);
		//imageView.setFitWidth(38);
		
		imageBox.getChildren().add(imageView);
		imageBox.setClip(clip);
		imageBox.widthProperty().addListener((Observable observable) -> {
			clip.setWidth(imageBox.getWidth());
		});
		imageBox.heightProperty().addListener((Observable observable) -> {
			clip.setHeight(imageBox.getHeight());
		});

		//headImagePanel.setPrefSize(40, 40);
		//headImagePanel.setMaxWidth(40);
		//headImagePanel.setMinHeight(40);
		headImagePanel.setHeadRadius(38);
		headImagePanel.setHeadSize(38);
		//tilePane.setPrefColumns(1); // preferred columns
		
		VBox headVBox=new VBox();
//		tilePane.setAlignment(Pos.CENTER);
//		tilePane.setPadding(new Insets(2, 2, 2, 2));
//		tilePane.getChildren().add(headImagePanel);

		headVBox.setAlignment(Pos.CENTER);
		headVBox.getChildren().add(headImagePanel);
		headVBox.setPadding(new Insets(0, 0, 0, 5));
//		tilePane.getChildren().add(headImagePanel);
		
		
		HBox headHBox=new HBox();
		headHBox.setAlignment(Pos.CENTER);
		headHBox.getChildren().add(headVBox);
		
		infoBox.getChildren().add(headHBox);

		VBox textBox = new VBox();
		textBox.setAlignment(Pos.CENTER_LEFT);
		textBox.getChildren().add(label);

		infoBox.getChildren().add(textBox);
		this.setPrefHeight(44);

		closeButton.setFocusTraversable(false);
		closeButton.setPrefWidth(16);
		closeButton.setPrefHeight(16);
		closeButton.setMaxHeight(16);

		closeButton.getStyleClass().remove("button");
		closeButton.getStyleClass().add("chat-item-close-button");
		closeButton.setVisible(mouseEntered);

		StackPane pane = new StackPane();
		pane.getChildren().add(closeButton);
		// pane.setStyle("-fx-background-color:rgba(230, 230, 230, 1)");

		box.setAlignment(Pos.CENTER_RIGHT);
		box.getChildren().add(pane);
	}

	private void iniEvent() {
		this.setOnMouseEntered((Event event) -> {
			mouseEntered = true;
			updateBackground();
		});
		this.setOnMouseExited((Event event) -> {
			mouseEntered = false;
			updateBackground();
		});
	}

	

	public void updateBackground() {
		closeButton.setVisible(mouseEntered);
		if (selected) {
			this.setStyle("-fx-background-color:rgba(255, 255, 255, 0.8);-fx-background-radius: 3;");
		} else {
			if (mouseEntered) {
				this.setStyle("-fx-background-color:rgba(255, 255, 255, 0.3);-fx-background-radius: 3;");
			} else {
				this.setStyle("-fx-background-color:null;");
			}
		}
	}

	public void addCloseAction(EventHandler<ActionEvent> value) {
		closeButton.setOnAction(value);
	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setImage(Image image) {
		headImagePanel.setImage(image);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if(this.selected != selected){
			this.selected = selected;
			updateBackground();
		}
	}
	
	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}
}
