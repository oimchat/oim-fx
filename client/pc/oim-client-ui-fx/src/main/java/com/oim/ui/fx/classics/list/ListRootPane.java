/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics.list;

import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

/**
 *
 * @author XiaHui
 */
public class ListRootPane extends VBox {

	VBox box = new VBox();
	ScrollPane scrollPane = new ScrollPane();

	public ListRootPane() {
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
		if (!box.getChildren().contains(node)) {
			box.getChildren().add(index, node);
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
