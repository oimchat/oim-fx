/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.test.ui.component;

import java.util.ArrayList;
import java.util.List;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconButton;
import com.oim.ui.fx.classics.ClassicsStage;
import com.oim.ui.fx.classics.chat.FacePopup;
import com.only.fx.OnlyPopupOver;
import com.onlyxiahui.oim.face.FaceRepository;
import com.onlyxiahui.oim.face.bean.FaceCategory;
import com.onlyxiahui.oim.face.bean.FaceInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class FaceFrame extends ClassicsStage {

	FaceRepository fr=new FaceRepository();
	VBox rootVBox = new VBox();

	VBox topBox = new VBox();
	VBox centerBox = new VBox();
	VBox bottomBox = new VBox();

	OnlyPopupOver po = new OnlyPopupOver();
	Button button = new Button("弹出");
	FacePopup facePopup = new FacePopup();

	public FaceFrame() {
		init();
		initEvent();
		initFace();
	}

	private void init() {
		this.setTitle("测试");
		this.setWidth(380);
		this.setHeight(300);
		this.setRadius(10);
		this.setCenter(rootVBox);

		topBox.setPrefHeight(28);

		rootVBox.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");
		rootVBox.getChildren().add(topBox);
		rootVBox.getChildren().add(centerBox);
		rootVBox.getChildren().add(bottomBox);

		centerBox.getChildren().add(button);
	}

	private void initEvent() {
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// po.show(button4,1d,2d);
				facePopup.show(button);

			}
		});
	}
	private void initFace() {
		List<FaceCategory> list = fr.getAllFaceCategoryList();
		for (FaceCategory fc : list) {
			if ("classical".equals(fc.getId())) {
				set(fc.getId(), fc.getName(), fc.getFaceInfoList());
			}
		}
	}

	private void set(String key, String name, List<FaceInfo> list) {
		List<IconButton> faceList = new ArrayList<IconButton>();

		if (null != list && !list.isEmpty()) {
			for (FaceInfo data : list) {
				// String faceKey = data.getKey();
				String normalPath = data.getShowPath();
				String hoverPath = data.getRealPath();
				String text = data.getText();
				double width = data.getWidth();
				double height = data.getHeight();

				double imageWidth = data.getImageWidth();
				double imageHeight = data.getImageHeight();

				Image normalImage = ImageBox.getImageClassPath(normalPath);
				Image hoverImage = ImageBox.getImageClassPath(hoverPath);
				Tooltip tooltip = new Tooltip(text);

				IconButton button = new IconButton(normalImage, hoverImage, null);
				button.setTooltip(tooltip);

				if (width > 0 && height > 0) {
					button.setPrefSize(width, height);
					button.setMinSize(width, height);
				}

				if (imageWidth > 0 && imageHeight > 0) {
					button.setImageSize(imageWidth, imageHeight);
				}
				//

				button.setOnAction(a -> {
					facePopup.hide();
				});
				faceList.add(button);
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				facePopup.set(key, name, faceList);
			}
		});
	}

}
