package com.oim.ui.fx.succinct.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * 个人显示信息面板
 * 
 * @author: XiaHui
 * @date: 2017-12-09 22:52:42
 */
public class PersonalPane extends BorderPane {

	/** 头像显示组件 **/
	private final ImageView headImageView = new ImageView();
	/** 头像圆角修剪 **/
	private final Rectangle headImageClip = new Rectangle();
	/** 点击弹出菜单按钮 **/
	private final Button menuButton = new Button();
	/** 昵称/姓名显示组件 **/
	private final Label nameLabel = new Label();

	public PersonalPane() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {
		headImageView.setClip(headImageClip);
		StackPane headStackPane = new StackPane();
		headStackPane.setPadding(new Insets(16, 10, 16, 16));
		headStackPane.getChildren().add(headImageView);

		nameLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1);-fx-font-size: 18px;");
		HBox nameHBox = new HBox();
		nameHBox.setAlignment(Pos.CENTER_LEFT);
		nameHBox.getChildren().add(nameLabel);

		menuButton.getStyleClass().remove("button");
		menuButton.getStyleClass().add("personal-menu-button");
		menuButton.setGraphic(new ImageView());
		
		StackPane buttonStackPane = new StackPane();
		buttonStackPane.setPadding(new Insets(0, 20, 0, 0));
		buttonStackPane.getChildren().add(menuButton);

		this.setLeft(headStackPane);
		this.setCenter(nameHBox);
		this.setRight(buttonStackPane);
		this.setPrefWidth(180);
	}

	private void initEvent() {
		// TODO 自动生成的方法存根

	}

	private void initSet() {
		this.setHeadRadius(8);
		this.setHeadSize(40);
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

	public void setOnMenuAction(EventHandler<ActionEvent> value) {
		menuButton.setOnAction(value);
	}
}
