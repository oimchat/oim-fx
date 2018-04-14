package com.oim.ui.fx.classics.chat;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.ImageNode;
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

/**
 * @author XiaHui
 * @date 2017-12-11 20:45:45
 */
public class LastItem extends ChoosePane {

	//private static final String DEFAULT_STYLE_CLASS = "chat-item";
	
	private final BorderPane borderPane = new BorderPane();

	/** 头像显示组件 **/
	private final ImageNode imagePane = new ImageNode();
	/** 昵称/姓名显示组件 **/
	private final Label nameLabel = new Label();
	private final Label textLabel = new Label();

	private final Label timeLabel = new Label();

	private final ImageView redImageView = new ImageView();
	private final Label redLabel = new Label();

	private BooleanProperty red = new SimpleBooleanProperty(false);

	public LastItem() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {

		StackPane headStackPane = new StackPane();
		headStackPane.setPadding(new Insets(10, 8, 12, 15));
		headStackPane.getChildren().add(imagePane);

		redLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);");

		StackPane redStackPane = new StackPane();
		redStackPane.getChildren().add(redImageView);
		redStackPane.getChildren().add(redLabel);

		HBox redHBox = new HBox();
		redHBox.setAlignment(Pos.TOP_RIGHT);
		redHBox.getChildren().add(redStackPane);
		redHBox.setPadding(new Insets(5, 5, 0, 0));
		
		VBox redVBox = new VBox();
		redVBox.setAlignment(Pos.TOP_RIGHT);
		redVBox.getChildren().add(redHBox);

		StackPane leftStackPane = new StackPane();

		leftStackPane.getChildren().add(headStackPane);
		leftStackPane.getChildren().add(redVBox);

		//nameLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);-fx-font-size: 14px;");

		HBox nameHBox = new HBox();
		nameHBox.getChildren().add(nameLabel);

		timeLabel.setStyle("-fx-text-fill:#666a77;");

		BorderPane nameBorderPane = new BorderPane();

		nameBorderPane.setCenter(nameHBox);
		nameBorderPane.setRight(timeLabel);

		//textLabel.setStyle("-fx-text-fill:rgba(152, 152, 152, 1);");

		HBox textHBox = new HBox();
		textHBox.setAlignment(Pos.CENTER_LEFT);
		textHBox.getChildren().add(textLabel);

		VBox centerVBox = new VBox();
		centerVBox.setSpacing(5);
		centerVBox.setPadding(new Insets(10, 10, 0, 0));
		centerVBox.getChildren().add(nameBorderPane);
		centerVBox.getChildren().add(textHBox);

		borderPane.setLeft(leftStackPane);
		borderPane.setCenter(centerVBox);
		this.getChildren().add(borderPane);
	}

	private void initEvent() {
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent me) -> {
		});

		this.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (MouseEvent me) -> {
		});

		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
		});

		this.setOnMouseClicked(m -> {
		});

		red.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				updateRed();
			}
		});
	}

	private void initSet() {
		this.setHeadRadius(40);
		this.setHeadSize(40);
	}

	private void updateRed() {
		Image redImage = ImageBox.getImageClassPath("/classics/images/chat/item/red-16x16.png");
		boolean red = isRed();
		if (red) {
			this.redImageView.setImage(redImage);
		} else {
			this.redImageView.setImage(null);
		}
	}

	public void setHeadSize(double value) {
		imagePane.setImageSize(value);
	}

	public void setHeadRadius(double value) {
		imagePane.setImageRadius(value);
	}

	public void setHeadImage(Image value) {
		imagePane.setImage(value);
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
	
	public boolean isGray() {
		return imagePane.isGray();
	}

	public void setGray(boolean gray) {
		imagePane.setGray(gray);
	}
}
