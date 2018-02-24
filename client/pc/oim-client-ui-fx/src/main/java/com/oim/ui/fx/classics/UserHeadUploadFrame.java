/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.image.ImageCropperPane;
import com.only.fx.OnlyPopupOver;
import com.only.fx.common.action.EventAction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

/**
 *
 * @author Only
 */
public class UserHeadUploadFrame extends ClassicsStage {

	BorderPane rootPane = new BorderPane();
	BorderPane pane = new BorderPane();
	ImageCropperPane imagePane = new ImageCropperPane();
	Alert alert;
	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button doneButton = new Button();
	Button cancelButton = new Button();

	Label titleLabel = new Label();

	private OnlyPopupOver textOver = new OnlyPopupOver();// 昵称提示
	private Label textOverLabel = new Label();

	HBox buttonBox = new HBox();

	Button openButton = new Button();
	Button selectButton = new Button();

	private FileChooser imageFileChooser;

	public UserHeadUploadFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(390);
		this.setHeight(518);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("更换头像");

		imageFileChooser = new FileChooser();
		imageFileChooser.getExtensionFilters().add(new ExtensionFilter("图片文件", "*.png", "*.jpg", "*.bmp", "*.gif"));

		imagePane.setCoverSize(350, 350);

		openButton.setText("上传头像");
		selectButton.setText("选择系统头像");

		openButton.setPrefSize(130, 30);
		selectButton.setPrefSize(130, 30);

		buttonBox.setPadding(new Insets(12, 10, 12, 14));
		buttonBox.setSpacing(10);

		buttonBox.getChildren().add(openButton);
		buttonBox.getChildren().add(selectButton);

		pane.setTop(buttonBox);
		pane.setCenter(imagePane);

		titleLabel.setText("更换头像");
		titleLabel.setFont(Font.font("微软雅黑", 14));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		topBox.setStyle("-fx-background-color:#2cb1e0");
		topBox.setPrefHeight(30);
		topBox.setPadding(new Insets(5, 10, 5, 10));
		topBox.setSpacing(10);
		topBox.getChildren().add(titleLabel);

		alert = new Alert(AlertType.CONFIRMATION, "");
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(this);
		alert.getDialogPane().setContentText("确定选择");
		alert.getDialogPane().setHeaderText(null);

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		doneButton.setText("确定");
		doneButton.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(doneButton);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setTop(topBox);
		rootPane.setCenter(pane);
		rootPane.setBottom(bottomBox);

		Image coverImage = ImageBox.getImageClassPath("/classics/images/cropper/CircleMask.png");
		imagePane.setCoverImage(coverImage);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			UserHeadUploadFrame.this.hide();
		});
		openButton.setOnAction(a -> {
			openImage();
		});
	}

	public boolean verify() {
		boolean mark = imagePane.hasImage();
		if (!mark) {
			textOverLabel.setText("请输选择图片!");
			textOver.show(imagePane);
		}
		return mark;
	}

	public Image getImage() {
		return imagePane.getImage();
	}

	private void openImage() {
		File file = imageFileChooser.showOpenDialog(this);
		if (file != null) {
			if (file.exists()) {

				Image image = null;
				try {
					String pathString = file.toURI().toURL().toString();
					image = new Image(pathString, true);
					imagePane.setImage(image);
				} catch (MalformedURLException ex) {
					Logger.getLogger(ImageBox.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		doneButton.setOnAction(value);
	}

	public void setSelectAction(EventHandler<ActionEvent> value) {
		selectButton.setOnAction(value);
	}

	public void select(EventAction<Boolean> a) {
		alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
			a.execute(true);
		});
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}
}
