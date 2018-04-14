package com.oim.ui.fx.flat.main;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.IconToggleButton;
import com.oim.fx.ui.list.HeadImagePanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * @author XiaHui
 * @date 2017-10-27 10:46:16
 */
public class MainFlatPane extends BorderPane {

	BorderPane borderPane = new BorderPane();

	HBox topBox = new HBox();

	HeadImagePanel head = new HeadImagePanel();

	HBox topLabelBox = new HBox();
	Label topLabel = new Label();

	BorderPane tabPane = new BorderPane();
	VBox tabBox = new VBox();

	VBox box = new VBox();

	ToggleGroup group = new ToggleGroup();

	public MainFlatPane() {
		initComponent();
	}

	/**
	 * 初始化ui界面各个组件
	 * 
	 * @author: XiaHui
	 * @createDate: 2017-10-25 17:54:45
	 * @update: XiaHui
	 * @updateDate: 2017-10-25 17:54:45
	 */
	private void initComponent() {

		this.setCenter(borderPane);
		
		
		// 顶部面板设置
		topBox.setPrefHeight(53);
		topBox.setStyle("-fx-background-color:rgba(33, 41, 56, 1)");
		// 头像的大小和圆角
		head.setHeadSize(35);
		head.setHeadRadius(0);
		// 以下让头像自适应位置
		VBox headBox = new VBox();
		headBox.setAlignment(Pos.TOP_CENTER);
		headBox.setPadding(new Insets(10, 0, 1, 15));
		headBox.getChildren().add(head);

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(headBox);

		topLabel.setFont(Font.font("微软雅黑", 16));
		topLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");
		
		topLabelBox.setPadding(new Insets(0, 0, 0, 30));
		topLabelBox.setAlignment(Pos.CENTER);
		topLabelBox.getChildren().add(topLabel);
		
		topBox.getChildren().add(hBox);
		topBox.getChildren().add(topLabelBox);
		
		tabBox.setAlignment(Pos.TOP_CENTER);
		tabBox.setPadding(new Insets(35, 0, 0, 0));
		tabBox.setSpacing(30);
		
		tabPane.setCenter(tabBox);

		tabPane.setMinWidth(55);
		tabPane.setStyle("-fx-background-color:rgba(50, 58, 73, 1)");

		borderPane.setTop(topBox);
		borderPane.setLeft(tabPane);
	}

	void initEvent() {
		group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
			if (newValue == null) {
				group.selectToggle(oldValue);
			}
		});
	}

	ToggleButton tempTb;
	ChangeListener<Toggle> listener = new ChangeListener<Toggle>() {

		@Override
		public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
			if (newValue == null) {
				group.selectToggle(oldValue);
			}
		}
	};

	public void addTab(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		IconToggleButton tb = new IconToggleButton(normalImage, hoverImage, selectedImage);
		addTab(tb, node);
	}

	public void addTab(IconToggleButton tb, Node node) {
		tb.setUserData(node);
		tb.setToggleGroup(group);
		tb.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (oldValue != newValue && newValue) {
				setSelectedNode(node);
				redTab(tb, !newValue);
			}
		});
		tabBox.getChildren().add(tb);
		if (tempTb == null) {
			group.selectToggle(tb);
			tempTb = tb;
		}
		group.selectedToggleProperty().removeListener(listener);
		group.selectedToggleProperty().addListener(listener);
	}

	public void setHeadImage(Image image) {
		head.setImage(image);
	}

	public void setSelectedNode(Node node) {
		borderPane.setCenter(node);
	}

	public void selectedTab(int index) {
		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof ToggleButton) {
				group.selectToggle((ToggleButton) node);
			}
		}
	}

	public void redTab(int index, boolean show) {

		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof IconToggleButton) {
				IconToggleButton tb = (IconToggleButton) node;
				redTab(tb, show);
			}
		}
	}

	public boolean isSelectedTab(int index) {
		boolean show = false;
		int size = tabBox.getChildren().size();
		if (index < size) {
			Node node = tabBox.getChildren().get(index);
			if (node instanceof IconToggleButton) {
				IconToggleButton tb = (IconToggleButton) node;
				show = tb.isSelected();
			}
		}
		return show;
	}

	public void redTab(IconToggleButton tb, boolean show) {
		if (show) {
			Image image = ImageBox.getImageClassPath("/resources/images/common/red/red_12.png");
			tb.setRedImage(image);
		} else {
			tb.setRedImage(null);
		}
	}
	
	
	public void setTopText(String text) {
		this.topLabel.setText(text);
	}

	public void setLeftBottom(Node node) {
		tabPane.setBottom(node);
	}
}
