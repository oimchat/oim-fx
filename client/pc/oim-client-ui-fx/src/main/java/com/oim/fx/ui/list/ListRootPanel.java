/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui.list;

import java.lang.reflect.Field;
import java.util.Set;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ListRootPanel extends VBox {

	VBox box = new VBox();
	ScrollPane scrollPane = new ScrollPane();

	public ListRootPanel() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(scrollPane);

		scrollPane.setBackground(Background.EMPTY);
		// scrollPane.setVvalue(20);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setContent(box);
		scrollPane.widthProperty().addListener((Observable observable) -> {
			box.setPrefWidth(scrollPane.getWidth());
		});

		try {
			Skin<?> skin = scrollPane.getSkin();

			if (skin instanceof ScrollPaneSkin) {
				//ScrollPaneSkin scrollPaneSkin = (ScrollPaneSkin) scrollPane.getSkin();
				Field field = skin.getClass().getDeclaredField("vsb");
				if (null != field) {
					field.setAccessible(true);
					ScrollBar scrollBar = (ScrollBar) field.get(skin);
					field.setAccessible(false);
					scrollBar.setUnitIncrement(20.0);
				}
				
				field = skin.getClass().getDeclaredField("hsb");
				if (null != field) {
					field.setAccessible(true);
					ScrollBar scrollBar = (ScrollBar) field.get(skin);
					field.setAccessible(false);
					scrollBar.setUnitIncrement(20.0);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		Set<Node> nodes = scrollPane.lookupAll(".scroll-bar");
		if (null != nodes) {
			for (Node node : nodes) {
				if (node instanceof ScrollBar) {
					//System.out.println("222");
					ScrollBar sb = (ScrollBar) node;
					sb.setUnitIncrement(20.0);
					// if (sb.getOrientation() == Orientation.HORIZONTAL) {
					// System.out.println("horizontal scrollbar visible = " +
					// sb.isVisible());
					// System.out.println("width = " + sb.getWidth());
					// System.out.println("height = " + sb.getHeight());
					// }
				}
			}
		}
	}

	private void iniEvent() {
	}

	public void addNode(Node node) {
		if (!box.getChildren().contains(node)) {
			box.getChildren().add(node);
		}
	}

	public void addNode(int index, Node node) {
		if (box.getChildren().contains(node)) {
			box.getChildren().remove(node);
		}
		if(!box.getChildren().contains(node)){
			box.getChildren().add(index,node);
		}
	}

	public void removeNode(Node node) {
		box.getChildren().remove(node);
	}

	public void removeNode(int index) {
		box.getChildren().remove(index);
	}

	public void clearNode() {
		box.getChildren().clear();
	}

	public int nodeSize() {
		return box.getChildren().size();
	}

}
