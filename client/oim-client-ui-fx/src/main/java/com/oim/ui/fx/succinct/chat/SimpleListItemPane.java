package com.oim.ui.fx.succinct.chat;

import com.only.fx.common.component.choose.ChoosePane;

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
public class SimpleListItemPane extends ChoosePane  {

	private final BorderPane borderPane=new BorderPane();
	/** 头像显示组件 **/
	private final ImageView headImageView = new ImageView();
	/** 头像圆角修剪 **/
	private final Rectangle headImageClip = new Rectangle();
	/** 昵称/姓名显示组件 **/
	private final Label nameLabel = new Label();

	public SimpleListItemPane() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {

		headImageView.setClip(headImageClip);

		StackPane headStackPane = new StackPane();
		headStackPane.setPadding(new Insets(10, 8, 10, 20));
		headStackPane.getChildren().add(headImageView);

		StackPane leftStackPane = new StackPane();
		leftStackPane.getChildren().add(headStackPane);

		nameLabel.setStyle("-fx-font-size: 14px;");

		HBox nameHBox = new HBox();
		nameHBox.setAlignment(Pos.CENTER_LEFT);
		nameHBox.getChildren().add(nameLabel);

		VBox centerVBox = new VBox();
		centerVBox.setAlignment(Pos.CENTER_LEFT);
		centerVBox.getChildren().add(nameHBox);


		borderPane.setLeft(leftStackPane);
		borderPane.setCenter(centerVBox);
		//this.getStyleClass().add("chat-item-pane");
		this.getChildren().add(borderPane);
	}

	private void initEvent() {
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent me) -> {

		});
		this.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (MouseEvent me) -> {

		});
		this.setOnMouseClicked(m -> {
			// setSelected(!isSelected());
			// setRed(!isRed());
		});
	}

	private void initSet() {
		this.setHeadRadius(3);
		this.setHeadSize(32);
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
}
