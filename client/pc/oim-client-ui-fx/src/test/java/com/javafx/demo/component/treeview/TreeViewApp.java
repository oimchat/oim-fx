package com.javafx.demo.component.treeview;

import java.util.Arrays;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author: XiaHui
 * @date: 2017年4月10日 上午11:02:59
 */
public class TreeViewApp extends Application {

	public Parent createContent() {

		final TreeItem<String> treeRoot = new TreeItem<String>("Root node");

		treeRoot.getChildren().addAll(Arrays.asList(
				new TreeItem<String>("Child Node 1"),
				new TreeItem<String>("Child Node 2"),
				new TreeItem<String>("Child Node 3")));

		treeRoot.getChildren().get(2).getChildren().addAll(Arrays.asList(

				new TreeItem<String>("Child Node 4"),
				new TreeItem<String>("Child Node 5"),
				new TreeItem<String>("Child Node 6"),
				new TreeItem<String>("Child Node 7"),
				new TreeItem<String>("Child Node 8")));

		Button button = new Button();
		button.setText("root");
		
		button.setOnMouseEntered(e->{
			System.out.println(3333);
		});

		TreeItem<Button> root = new TreeItem<Button>(button);
		
		Button nodeButton = new Button();
		nodeButton.setText("node");
		
		TreeItem<Button> node = new TreeItem<Button>(nodeButton);
		root.getChildren().add(node);
		
		TreeView<Button> treeView = new TreeView<Button>();
		treeView.setShowRoot(true);
		treeView.setRoot(root);
		// treeRoot.setExpanded(true);
		treeView.setOnMouseEntered(e->{
			System.out.println(1111);
		});
		return treeView;
	}

	@Override

	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
		EventType<MouseEvent> type = MouseEvent.MOUSE_CLICKED;
		primaryStage.addEventHandler(type,e->{
			System.out.println(e);
		});
	}

	/**
	 * 
	 * Java main for when running without JavaFX launcher
	 * 
	 */

	public static void main(String[] args) {
		launch(args);
	}
}