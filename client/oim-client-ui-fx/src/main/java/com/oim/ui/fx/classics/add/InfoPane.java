/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.add;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * 用户或者群组信息面板
 * 
 * @author XiaHui
 */
public class InfoPane extends StackPane {

	private BorderPane baseBorderPane=new BorderPane();
	private Map<Object, Object> attributeMap = new HashMap<Object, Object>();

	private final ImageView headImageView = new ImageView();// 头像图片显示组件
	private final Rectangle headClip = new Rectangle();

	private final Label nameLabel = new Label();
	private final Label numberLabel = new Label();

	private final TextArea textArea = new TextArea();// 信息文本区

	public InfoPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {

		/*** 头像部分 start ****/
		headImageView.setFitHeight(60);
		headImageView.setFitWidth(60);

		headClip.setArcHeight(60);
		headClip.setArcWidth(60);

		headClip.setWidth(60);
		headClip.setHeight(60);

		AnchorPane headImagePane = new AnchorPane();
		headImagePane.setClip(headClip);
		headImagePane.getChildren().add(headImageView);
		headImagePane.setStyle("-fx-background-color:rgba(255, 255, 255, 0.9)");

		AnchorPane pane = new AnchorPane();
		pane.getStyleClass().add("head-common-image-pane");
		pane.getChildren().add(headImagePane);

		VBox headRootPane = new VBox();

		headRootPane.getChildren().add(pane);
		headRootPane.setPadding(new Insets(5, 5, 5, 5));
		
		HBox hBox = new HBox();
		hBox.getChildren().add(headRootPane);
		/***** 头像部分 end *****/

		nameLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:14px;");
		numberLabel.setStyle("-fx-text-fill:#000000;-fx-font-size:14px;");

		textArea.setEditable(false);
		textArea.setBorder(Border.EMPTY);
		textArea.getStyleClass().clear();
		textArea.setWrapText(true);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(5, 5, 5, 5));
		vBox.getChildren().add(nameLabel);
		vBox.getChildren().add(numberLabel);

		this.setPadding(new Insets(8, 15, 8, 15));
		
		baseBorderPane.setTop(hBox);
		baseBorderPane.setCenter(textArea);
		
		this.getChildren().add(baseBorderPane);
	}

	private void iniEvent() {

	}

	/**
	 * 存储对象
	 * 
	 * @author: XiaHui
	 * @param key
	 * @param value
	 * @createDate: 2017年5月25日 下午6:00:48
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:00:48
	 */
	public void addAttribute(Object key, Object value) {
		attributeMap.put(key, value);
	}

	/**
	 * 获取存储的对象
	 * 
	 * @author: XiaHui
	 * @param key
	 * @return
	 * @createDate: 2017年5月25日 下午6:01:12
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:01:12
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(Object key) {
		return (T) attributeMap.get(key);
	}

	/**
	 * 设置头像
	 * 
	 * @author: XiaHui
	 * @param image
	 * @createDate: 2017年5月25日 下午6:00:40
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:00:40
	 */
	public void setHeadImage(Image image) {
		if (null != image) {
			headImageView.setImage(image);
		}
	}

	/**
	 * 设置名称
	 * 
	 * @author: XiaHui
	 * @param name
	 * @createDate: 2017年5月25日 下午6:00:31
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午6:00:31
	 */
	public void setName(String name) {
		nameLabel.setText(name);
	}

	/**
	 * 设置账号信息
	 * 
	 * @author: XiaHui
	 * @param number
	 * @createDate: 2017年5月25日 下午5:59:49
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:59:49
	 */
	public void setNumber(String number) {
		numberLabel.setText(number);
	}

	/**
	 * 设置显示文本信息
	 * 
	 * @author: XiaHui
	 * @param showText
	 * @createDate: 2017年5月25日 下午5:59:40
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:59:40
	 */
	public void setInfoText(String showText) {
		textArea.setText(showText);
	}

	/**
	 * 设置头像大小
	 * 
	 * @author: XiaHui
	 * @param value
	 * @createDate: 2017年5月25日 下午5:59:25
	 * @update: XiaHui
	 * @updateDate: 2017年5月25日 下午5:59:25
	 */
	public void setHeadSize(double value) {
		headImageView.setFitHeight(value);
		headImageView.setFitWidth(value);

		headClip.setArcHeight(value);
		headClip.setArcWidth(value);

		headClip.setWidth(value);
		headClip.setHeight(value);
	}
}
