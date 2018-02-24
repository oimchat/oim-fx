package com.oim.ui.fx.classics.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.fx.common.component.IconButton;

import javafx.beans.Observable;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class FacePane extends StackPane {

	TabPane tabPane = new TabPane();

	Map<String, Tab> tabMap = new HashMap<String, Tab>();
	Map<String, FaceScrollPane> gridViewMap = new HashMap<String, FaceScrollPane>();

	public FacePane() {
		initComponent();
		initData();
		iniEvent();
	}

	private void initComponent() {
		this.setPrefSize(460, 300);
		this.getChildren().add(tabPane);
		tabPane.setSide(Side.BOTTOM);
	}

	private void initData() {

	}

	private void iniEvent() {

	}

	public void set(String key, String name, List<IconButton> list) {
		FaceScrollPane faceGrid = gridViewMap.get(key);
		if (null == faceGrid) {
			faceGrid = new FaceScrollPane();
			gridViewMap.put(key, faceGrid);
		} else {
			faceGrid.pane.getChildren().clear();
		}

		for (IconButton b : list) {
			faceGrid.pane.getChildren().add(b);
		}

		Tab tab = tabMap.get(key);
		if (null == tab) {
			tab = new Tab();
			tab.setClosable(false);
			tabMap.put(key, tab);
		}

		tab.setText(name);
		tab.setContent(faceGrid);
		if (!tabPane.getTabs().contains(tab)) {
			tabPane.getTabs().add(tab);
		}
	}

	class FaceScrollPane extends ScrollPane {
		FlowPane pane = new FlowPane();

		public FaceScrollPane() {
			this.setBackground(Background.EMPTY);
			this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			this.setContent(pane);
			this.widthProperty().addListener((Observable observable) -> {
				pane.setMinWidth(FaceScrollPane.this.getWidth());
			});
		}
	}
}
