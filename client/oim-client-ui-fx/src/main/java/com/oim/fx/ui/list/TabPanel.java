/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

//import java.util.HashMap;
//import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author XiaHui
 */
public class TabPanel extends BorderPane {

	private final HBox topBox = new HBox();
	private final VBox topRootBox = new VBox();
	private final StackPane box = new StackPane();
	// private final List<Tab> list = new CopyOnWriteArrayList<Tab>();
	private final Map<Tab, Node> map = new ConcurrentHashMap<Tab, Node>();
	private Tab selectedTab;

	public TabPanel() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {

		// topBox.setPrefHeight(35);
		topBox.setMinHeight(35);

		Separator separator = new Separator();
		// separator.setOrientation(Orientation.HORIZONTAL);
		separator.setPrefHeight(1);
		separator.setMaxHeight(1);
		separator.setBorder(Border.EMPTY);
		// separator.setAlignment(Pos.CENTER_LEFT);
		// separator.getStyleClass().remove("line");
		// separator.r
		separator.setStyle("-fx-background-color:rgba(0, 0, 0, 0.3);");
		// separator.setBackground(Background.EMPTY);
		HBox line = new HBox();
		line.setMinHeight(1);
		line.setMaxHeight(1);
		line.setStyle("-fx-background-color:rgba(0, 0, 0, 0.1);");
		topRootBox.getChildren().add(topBox);

		this.setCenter(box);
		this.setTop(topRootBox);

		// setStyle("-fx-background-color:rgba(240, 240, 240, 1)");
		// this.getChildren().add(topRootBox);
		// this.getChildren().add(box);
	}

	private void iniEvent() {
	}

	public void add(Tab tab, Node node) {
		topBox.getChildren().add(tab);
		HBox.setHgrow(tab, Priority.ALWAYS);
		map.put(tab, node);
		if (null == selectedTab) {
			tab.setSelected(true);
			selectedTab = tab;
			box.getChildren().add(node);
		}
		tab.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				selected(tab);
			}
		});
		tab.setOnMouseClicked((Event event) -> {
			tabOnMouseClicked(event);
		});

		// if (!list.contains(tab)) {
		// list.add(tab);
		// }
	}

	public Tab add(String text, Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		Tab tab = new Tab(text, normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	public Tab add(String text, Font font, Paint paint, Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		Tab tab = new Tab(text, font, paint, normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	public Tab add(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		Tab tab = new Tab(normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	// public void select(int index) {
	// if (index < list.size()) {
	// Tab tab =list.get(index);
	// selected(tab);
	// }
	// }

	private void tabOnMouseClicked(Event event) {
		Object o = event.getSource();
		if (o instanceof Tab) {
			Tab tabTemp = (Tab) o;
			selected(tabTemp);
		}
	}

	public void selected(Tab tab) {
		if (!tab.equals(selectedTab)) {
			// int oldIndex = 0;
			// int newIndex = topBox.getChildren().indexOf(o);

			if (null != selectedTab) {
				selectedTab.setSelected(false);
				// oldIndex = topBox.getChildren().indexOf(tab);
			}
			Node node = map.get(tab);
			if (!tab.isSelected()) {
				tab.setSelected(true);
				// node.setVisible(false);
				setSelectedNode(node);
				// if (oldIndex < newIndex) {
				// new FadeInRightBigTransition(node).play();
				// } else {
				// new FadeInLeftBigTransition(node).play();
				// }

				// if (oldIndex < newIndex) {
				// new FadeInRightTransition(node).play();
				// } else {
				// new FadeInLeftTransition(node).play();
				// }

				// timeline.setCycleCount(1);
				// timeline.setDelay(Duration.ONE);
				// KeyValue ky = new KeyValue(node.visibleProperty(), true);
				// KeyFrame kf = new KeyFrame(new Duration(230), ky);
				// timeline.getKeyFrames().add(kf);
				// timeline.play();
				// node.setVisible(true);
			}
			selectedTab = tab;
		}
	}

	private void setSelectedNode(Node node) {
		box.getChildren().clear();
		box.getChildren().add(node);
	}

	public void selected(int index) {
		Node node = topBox.getChildren().get(index);
		if (node instanceof Tab) {
			Tab tabTemp = (Tab) node;
			selected(tabTemp);
		}
	}

	// private void iniTest() {
	//
	// Image normalImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_normal.png");
	// Image hoverImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_hover.png");
	// Image selectedImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_contacts_selected.png");
	//
	// VBox rootVBox = new VBox();
	// this.add(normalImage, hoverImage, selectedImage, rootVBox);
	//
	// normalImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_normal.png");
	// hoverImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_hover.png");
	// selectedImage =
	// ImageBox.getImageClassPath("/resources/main/images/panel/icon_group_selected.png");
	//
	// VBox box2 = new VBox();
	// this.add(normalImage, hoverImage, selectedImage, box2);
	//
	// }
}
