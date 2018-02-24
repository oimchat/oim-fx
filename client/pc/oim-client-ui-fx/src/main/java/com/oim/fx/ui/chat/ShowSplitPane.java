package com.oim.fx.ui.chat;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ShowSplitPane extends VBox {

	private int type = 0;

	BorderPane topPane = new BorderPane();
	BorderPane bottomPane = new BorderPane();
	private double minTop = 55;
	private double minBottom = 105;
	// private double maxHeight;
	//
	// private double stageX;
	// private double stageY;
	// private double stageWidth;
	// private double stageHeight;
	private double rootHeight;

	public ShowSplitPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getChildren().add(topPane);
		this.getChildren().add(bottomPane);
		VBox.setVgrow(topPane, Priority.ALWAYS);
		bottomPane.setPrefHeight(140);
		bottomPane.setMinHeight(minBottom);

		//topPane.setStyle("-fx-background-color: #008000;");
		//bottomPane.setStyle("-fx-background-color: #1931e8;");
	}

	private void iniEvent() {
		bottomPane.setOnMouseExited((MouseEvent me) -> {
			Cursor cursor = Cursor.DEFAULT;
			if (this.getCursor() != cursor) {
				this.setCursor(cursor);
			}
		});

		bottomPane.setOnMouseMoved((MouseEvent me) -> {
			Cursor cursor = getCursor(me, bottomPane);
			if (this.getCursor() != cursor) {
				this.setCursor(cursor);
			}
		});
		this.setOnMousePressed((MouseEvent me) -> {
			// stageX = bottomPane.getLayoutX();
			// stageY = bottomPane.getLayoutY();
			rootHeight = ShowSplitPane.this.getHeight();
		});
		this.setOnMouseDragged((MouseEvent me) -> {
			if (type == 1) {
			} else if (type == 2) {
			} else if (type == 3) {
			} else if (type == 4) {
			} else if (type == 5) {
			} else if (type == 6) {
			} else if (type == 7) {

				double value = rootHeight - me.getY();
				updateHeight(bottomPane, value);

				me.consume();
			} else if (type == 8) {
				// double value = me.getY();
				// double max = this.getMaxHeight();
				// double min = this.getMinHeight();
				// // if ((min <= value && value <= max)) {
				// this.setPrefHeight(value);
				// // }
				// me.consume();
			}
		});
	}

	// private void updateWidth(Pane stage, double value) {
	// double max = stage.getMaxWidth();
	// double min = stage.getMinWidth();
	//
	// if ((min <= value && value <= max)) {
	// stage.setPrefWidth(value);
	// }
	// }

	private void updateHeight(Pane stage, double value) {
		// double max = stage.getMaxHeight();
		// double min = stage.getMinHeight();
		if ((minBottom <= value && value <= (rootHeight - minTop))) {
			stage.setPrefHeight(value);
		}
	}

	// private void updateX(Pane stage, double x, double value) {
	// double max = stage.getMaxWidth();
	// double min = stage.getMinWidth();
	//
	// if ((min <= value && value <= max)) {
	// stage.setX(x);
	// }
	// }
	//
	// private void updateY(Pane stage, double y, double value) {
	// double max = stage.getMaxHeight();
	// double min = stage.getMinHeight();
	//
	// if ((min <= value && value <= max)) {
	// stage.setY(y);
	// }
	// }
	private Cursor getCursor(MouseEvent me, Pane pane) {
		Cursor cursor = Cursor.DEFAULT;

		double grp = 4;
		double width = pane.getWidth();
		double height = pane.getHeight();

		double x = me.getX();
		double y = me.getY();

		if (x < grp && y < grp) {
			// cursor = Cursor.SE_RESIZE;
			type = 1;
		} else if (x > (width - grp) && y < grp) {
			// cursor = Cursor.SW_RESIZE;
			type = 2;
		} else if (x < grp && y > (height - grp)) {
			// cursor = Cursor.SW_RESIZE;
			type = 3;
		} else if (x > (width - grp) && y > (height - grp)) {
			// cursor = Cursor.SE_RESIZE;
			type = 4;
		} else if (x < grp) {
			// cursor = Cursor.H_RESIZE;
			type = 5;
		} else if (x > (width - grp)) {
			// cursor = Cursor.H_RESIZE;
			type = 6;
		} else if (y < grp) {
			cursor = Cursor.V_RESIZE;
			type = 7;
		} else if (y > (height - grp)) {
			// cursor = Cursor.V_RESIZE;
			type = 8;
		} else {
			type = 0;
		}
		return cursor;
	}

	public void setTop(Node node) {
		topPane.setCenter(node);
//		topPane.getChildren().clear();
//		topPane.getChildren().add(node);
	}

	public void setBottom(Node node) {
		bottomPane.setCenter(node);
//		bottomPane.getChildren().clear();
//		bottomPane.getChildren().add(node);
	}
}
