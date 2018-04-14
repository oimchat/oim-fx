/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.chat;

import java.util.HashMap;
import java.util.Map;

import com.oim.fx.ui.list.HeadImagePanel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author XiaHui
 */
public class SimpleHead extends StackPane {

	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();
	private HBox infoBox = new HBox();
	private final ImageView imageView = new ImageView();
	private final Rectangle clip = new Rectangle();
	private final VBox imageBox = new VBox();
	private boolean mouseEntered = false;
	private boolean selected = false;
	private HeadImagePanel headImagePanel = new HeadImagePanel();
	private TilePane tilePane = new TilePane();
	private Label label = new Label();
	private final Timeline animation = new Timeline();
	private String text = "";
	boolean pulse = false;
	boolean mousehighlight = true;

	public SimpleHead() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(infoBox);

		imageBox.getChildren().add(imageView);
		imageBox.setClip(clip);
		imageBox.widthProperty().addListener((Observable observable) -> {
			clip.setWidth(imageBox.getWidth());
		});
		imageBox.heightProperty().addListener((Observable observable) -> {
			clip.setHeight(imageBox.getHeight());
		});

		tilePane.setPrefColumns(1); // preferred columns
		tilePane.setAlignment(Pos.CENTER);
		tilePane.getChildren().add(headImagePanel);

		infoBox.getChildren().add(tilePane);

		VBox textBox = new VBox();
		textBox.setAlignment(Pos.CENTER_LEFT);
		textBox.getChildren().add(label);

		infoBox.getChildren().add(textBox);

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

		animation.setCycleCount(Animation.INDEFINITE);
		KeyValue kx1 = new KeyValue(label.textProperty(), "");
		KeyValue ky1 = new KeyValue(label.textProperty(), text);

		KeyFrame kfx1 = new KeyFrame(new Duration(280), kx1);
		KeyFrame kfy1 = new KeyFrame(new Duration(880), ky1);

		animation.getKeyFrames().add(kfx1);
		animation.getKeyFrames().add(kfy1);
	}

	public void updateBackground() {
		if (!mousehighlight) {
			return;
		}
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

	public void setHeadSize(double value) {
		headImagePanel.setHeadSize(value);
	}

	public void setHeadRadius(double value) {
		headImagePanel.setHeadRadius(value);
	}

	public void setText(String text) {
		this.text = text;
		label.setText(text);
	}

	public void setImage(Image image) {
		headImagePanel.setImage(image);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			updateBackground();
		}
	}

	public void setPulse(boolean pulse) {
		this.pulse = pulse;
		if (!pulse) {
			if (animation.getStatus() == Animation.Status.RUNNING) {
				animation.stop();
			}
			label.setText(text);
		} else {
			animation.play();
		}
	}

	public boolean isPulse() {
		return pulse;
	}

	public boolean isMousehighlight() {
		return mousehighlight;
	}

	public void setMousehighlight(boolean mousehighlight) {
		this.mousehighlight = mousehighlight;
	}

	public boolean isGray() {
		return headImagePanel.isGray();
	}

	public void setGray(boolean gray) {
		headImagePanel.setGray(gray);
	}

	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}
}
