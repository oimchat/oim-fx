package com.oim.fx.common.component;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * @author: XiaHui
 * @date: 2018-01-24 09:57:06
 */
public class BaseStackPane extends StackPane {

	StackPane stackPane = new StackPane();

	public BaseStackPane() {
		super();
		initComponent();
	}

	public BaseStackPane(Node... children) {
		super();
		stackPane.getChildren().addAll(children);
		initComponent();
	}

	private void initComponent() {
		super.getChildren().add(stackPane);
	}

	@Override
	public ObservableList<Node> getChildren() {
		return stackPane.getChildren();
	}
}
