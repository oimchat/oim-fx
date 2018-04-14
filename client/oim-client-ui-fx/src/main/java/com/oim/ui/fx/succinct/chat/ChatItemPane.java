package com.oim.ui.fx.succinct.chat;

import com.oim.fx.common.box.ImageBox;
import com.only.fx.common.component.choose.ChoosePane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * @author XiaHui
 * @date 2017-12-11 20:45:45
 */
public class ChatItemPane extends ChoosePane {

	private final BorderPane borderPane = new BorderPane();

	/** 头像显示组件 **/
	private final ImageView headImageView = new ImageView();
	/** 头像圆角修剪 **/
	private final Rectangle headImageClip = new Rectangle();
	/** 昵称/姓名显示组件 **/
	private final Label nameLabel = new Label();
	private final Label textLabel = new Label();

	private final Label timeLabel = new Label();

	private final ImageView redImageView = new ImageView();
	private final Label redLabel = new Label();

	// private boolean selected = false;
	// private BooleanProperty selected = new SimpleBooleanProperty(false);

	private BooleanProperty red = new SimpleBooleanProperty(false);

	public ChatItemPane() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {

		headImageView.setClip(headImageClip);

		StackPane headStackPane = new StackPane();
		headStackPane.setPadding(new Insets(12, 8, 12, 18));
		headStackPane.getChildren().add(headImageView);

		redLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);");

		StackPane redStackPane = new StackPane();
		redStackPane.getChildren().add(redImageView);
		redStackPane.getChildren().add(redLabel);

		HBox redHBox = new HBox();
		redHBox.setAlignment(Pos.TOP_RIGHT);
		redHBox.getChildren().add(redStackPane);

		VBox redVBox = new VBox();
		redVBox.setAlignment(Pos.TOP_RIGHT);
		redVBox.getChildren().add(redHBox);

		StackPane leftStackPane = new StackPane();

		leftStackPane.getChildren().add(headStackPane);
		leftStackPane.getChildren().add(redVBox);

		nameLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);-fx-font-size: 14px;");

		HBox nameHBox = new HBox();
		nameHBox.getChildren().add(nameLabel);

		timeLabel.setStyle("-fx-text-fill:#666a77;");

		BorderPane nameBorderPane = new BorderPane();

		nameBorderPane.setCenter(nameHBox);
		nameBorderPane.setRight(timeLabel);

		textLabel.setStyle("-fx-text-fill:rgba(152, 152, 152, 1);");

		HBox textHBox = new HBox();
		textHBox.setAlignment(Pos.CENTER_LEFT);
		textHBox.getChildren().add(textLabel);

		VBox centerVBox = new VBox();
		centerVBox.setSpacing(5);
		centerVBox.setPadding(new Insets(10, 0, 0, 0));
		centerVBox.getChildren().add(nameBorderPane);
		centerVBox.getChildren().add(textHBox);

		HBox lineHBox = new HBox();
		lineHBox.setPrefHeight(0.5);
		lineHBox.setStyle("-fx-background-color:#292c33;");

		borderPane.setLeft(leftStackPane);
		borderPane.setCenter(centerVBox);
		borderPane.setBottom(lineHBox);
		// borderPane.getStyleClass().add("selected-button");
		// this.getStyleClass().add("chat-item-pane");
		this.getChildren().add(borderPane);
	}

	private void initEvent() {
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent me) -> {

		});
		this.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (MouseEvent me) -> {

		});

		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
			// setSelected(!isSelected());
		});
		this.setOnMouseClicked(m -> {

			// setRed(!isRed());
		});

		red.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				updateRed();
			}
		});
	}

	private void initSet() {
		this.setHeadRadius(5);
		this.setHeadSize(40);
	}

	private void updateRed() {
		Image redImage = ImageBox.getImageClassPath("/multiple/images/chat/item/red.png");
		boolean red = isRed();
		if (red) {
			this.redImageView.setImage(redImage);
		} else {
			this.redImageView.setImage(null);
		}
	}

	public void setHeadSize(double value) {
		headImageView.setFitHeight(value);
		headImageView.setFitWidth(value);

		headImageClip.setWidth(value);
		headImageClip.setHeight(value);
	}

	public void setHeadRadius(double value) {
		headImageClip.setArcHeight(value);
		headImageClip.setArcWidth(value);
	}

	public void setHeadImage(Image value) {
		headImageView.setImage(value);
	}

	public void setName(String value) {
		nameLabel.setText(value);
	}

	public void setText(String value) {
		textLabel.setText(value);
	}

	public void setTime(String value) {
		timeLabel.setText(value);
	}

	public void setRed(boolean red) {
		this.red.set(red);
	}

	public boolean isRed() {
		return this.red.get();
	}

	public void setRedText(String value) {
		redLabel.setText(value);
	}
}
