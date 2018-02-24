package com.only.fx.common.component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * @author XiaHui
 * @date 2017-12-15 17:21:40
 */
public class FlatTabPane extends StackPane {

	private final Map<FlatTab, Node> map = new ConcurrentHashMap<FlatTab, Node>();
	private final BorderPane borderPane = new BorderPane();
	private final HBox hBox = new HBox();
	private final VBox vBox = new VBox();
	private Side side = Side.TOP;
	private FlatTab selectedTab;

	public FlatTabPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(borderPane);
		updateTab();
	}

	private void iniEvent() {
		// TODO Auto-generated method stub

	}

	public void setSide(Side side) {
		this.side = side;
		updateTab();
	}

	private void updateTab() {
		borderPane.setTop(null);
		borderPane.setRight(null);
		borderPane.setBottom(null);
		borderPane.setLeft(null);
		if (Side.TOP == side) {
			borderPane.setTop(hBox);

			ObservableList<Node> list = vBox.getChildren();
			hBox.getChildren().clear();
			for (Node node : list) {
				add(node);
			}
			list.clear();

		} else if (Side.RIGHT == side) {
			borderPane.setRight(vBox);
			ObservableList<Node> list = hBox.getChildren();
			vBox.getChildren().clear();
			for (Node node : list) {
				add(node);
			}
			list.clear();

		} else if (Side.BOTTOM == side) {
			borderPane.setBottom(hBox);
			ObservableList<Node> list = vBox.getChildren();
			hBox.getChildren().clear();
			for (Node node : list) {
				add(node);
			}
			list.clear();

		} else if (Side.LEFT == side) {
			borderPane.setLeft(vBox);
			ObservableList<Node> list = hBox.getChildren();
			vBox.getChildren().clear();
			for (Node node : list) {
				add(node);
			}
			list.clear();

		} else {
			borderPane.setBottom(hBox);
			ObservableList<Node> list = vBox.getChildren();
			hBox.getChildren().clear();
			for (Node node : list) {
				add(node);
			}
			list.clear();
		}
	}

	public void add(FlatTab tab, Node node) {
		add(tab);
		map.put(tab, node);
		if (null == selectedTab) {
			tab.setSelected(true);
			selectedTab = tab;
			setSelectedNode(node);
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
	}

	public FlatTab add(String text, Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		FlatTab tab = new FlatTab(text, normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	public FlatTab add(String text, Font font, Paint paint, Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		FlatTab tab = new FlatTab(text, font, paint, normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	public FlatTab add(Image normalImage, Image hoverImage, Image selectedImage, Node node) {
		FlatTab tab = new FlatTab(normalImage, hoverImage, selectedImage);
		add(tab, node);
		return tab;
	}

	private void tabOnMouseClicked(Event event) {
		Object o = event.getSource();
		if (o instanceof FlatTab) {
			FlatTab tabTemp = (FlatTab) o;
			selected(tabTemp);
		}
	}

	public void selected(FlatTab tab) {
		if (!tab.equals(selectedTab)) {
			if (null != selectedTab) {
				selectedTab.setSelected(false);
			}
			Node node = map.get(tab);
			if (!tab.isSelected()) {
				tab.setSelected(true);
				setSelectedNode(node);
			}
			selectedTab = tab;
		}
	}

	private void add(Node tab) {
		if (Side.TOP == side) {
			hBox.getChildren().add(tab);
			HBox.setHgrow(tab, Priority.ALWAYS);
		} else if (Side.RIGHT == side) {
			vBox.getChildren().add(tab);
			VBox.setVgrow(tab, Priority.ALWAYS);
		} else if (Side.BOTTOM == side) {
			hBox.getChildren().add(tab);
			HBox.setHgrow(tab, Priority.ALWAYS);
		} else if (Side.LEFT == side) {
			vBox.getChildren().add(tab);
			VBox.setVgrow(tab, Priority.ALWAYS);
		} else {
			hBox.getChildren().add(tab);
			HBox.setHgrow(tab, Priority.ALWAYS);
		}
	}

	private void setSelectedNode(Node value) {
		borderPane.setCenter(value);
	}

	public void selected(int index) {
		Node node = null;
		if (Side.TOP == side) {
			node = hBox.getChildren().get(index);
		} else if (Side.RIGHT == side) {
			node = vBox.getChildren().get(index);
		} else if (Side.BOTTOM == side) {
			node = hBox.getChildren().get(index);
		} else if (Side.LEFT == side) {
			node = vBox.getChildren().get(index);
		} else {
			node = hBox.getChildren().get(index);
		}
		if (node instanceof FlatTab) {
			FlatTab tabTemp = (FlatTab) node;
			selected(tabTemp);
		}
	}

	public void setTabSize(double value) {
		if (Side.TOP == side) {
			hBox.setPrefHeight(value);
		} else if (Side.RIGHT == side) {
			vBox.setPrefWidth(value);
		} else if (Side.BOTTOM == side) {
			hBox.setPrefHeight(value);
		} else if (Side.LEFT == side) {
			vBox.setPrefWidth(value);
		} else {
			hBox.setPrefHeight(value);
		}
	}

	public void setTabPadding(Insets value) {
		if (Side.TOP == side) {
			hBox.setPadding(value);
		} else if (Side.RIGHT == side) {
			vBox.setPadding(value);
		} else if (Side.BOTTOM == side) {
			hBox.setPadding(value);
		} else if (Side.LEFT == side) {
			vBox.setPadding(value);
		} else {
			hBox.setPadding(value);
		}
	}
}
