package com.oim.fx.ui;

import com.oim.fx.ui.chat.ChatItem;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChatListFrame extends BaseFrame {

	private final StackPane stackPane = new StackPane();
	private final HBox hBox = new HBox();
	// VBox vBox = new VBox();

	VBox itemBox = new VBox();
	ScrollPane itemScrollPane = new ScrollPane();

	HBox itemPane = new HBox();
	StackPane chatPane = new StackPane();

	private double paneMinWidth = 585;
	private double itemWidth = 150;
	private double stageWidth;
	private int type = 0;

	public ChatListFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("OIM");
		this.setWidth(740);
		this.setHeight(510);
		this.setMinWidth(itemWidth + paneMinWidth);
		this.setMinHeight(510);
		this.setCenter(stackPane);
		this.getScene().getStylesheets().add(this.getClass().getResource("/resources/chat/css/chat.css").toString());

		stackPane.getChildren().add(hBox);

		// hBox.getChildren().add(gapPane);
		hBox.getChildren().add(itemPane);
		hBox.getChildren().add(chatPane);
		HBox.setHgrow(chatPane, Priority.ALWAYS);

		// itemBox.setStyle("-fx-background-color: #AA0000;");
		// chatPane.setStyle("-fx-background-color: #ffffff;");
		// itemPane.setStyle("-fx-background-color: rgba(70,70,70,0.8);");

		itemPane.setPrefWidth(itemWidth);
		itemPane.setMinWidth(65);
		itemPane.setMaxWidth(250);

		itemScrollPane.setFitToWidth(true);
		itemScrollPane.setContent(itemBox);
		itemScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		// itemScrollPane.widthProperty().addListener((Observable observable) ->
		// {
		// itemBox.setPrefWidth(itemScrollPane.);
		// });
		// scrollPane.setOnScroll(new ZoomHandler(innerGroup));

		VBox scrollBox = new VBox();

		StackPane gapNode = new StackPane();
		gapNode.setMinHeight(2);

		scrollBox.getChildren().add(gapNode);
		scrollBox.getChildren().add(itemScrollPane);

		StackPane gapPane = new StackPane();
		gapPane.setMinWidth(2);

		itemPane.setStyle("-fx-background-color: #49a0e5;");
		itemPane.getChildren().add(gapPane);
		itemPane.getChildren().add(scrollBox);
		gapNode = new StackPane();
		gapNode.setMinWidth(3);
		itemPane.getChildren().add(gapNode);

		HBox.setHgrow(scrollBox, Priority.ALWAYS);
	}

	private void iniEvent() {
		itemPane.setOnMouseDragExited((MouseEvent me) -> {
			Cursor cursor = getCursor(me, itemPane);
			if (itemPane.getCursor() != cursor) {
				itemPane.setCursor(cursor);
			}
		});

		itemPane.setOnMouseMoved((MouseEvent me) -> {
			Cursor cursor = getCursor(me, itemPane);
			if (itemPane.getCursor() != cursor) {
				itemPane.setCursor(cursor);
			}
		});
		itemPane.setOnMousePressed((MouseEvent me) -> {
			stageWidth = ChatListFrame.this.getWidth();
			itemWidth = itemPane.getWidth();
		});
		itemPane.setOnMouseDragged((MouseEvent me) -> {
			if (type == 1) {
			} else if (type == 2) {
			} else if (type == 3) {
			} else if (type == 4) {
			} else if (type == 5) {
			} else if (type == 6) {
				double value = me.getX();
				double max = itemPane.getMaxWidth();
				double min = itemPane.getMinWidth();
				if ((min <= value && value <= max)) {
					itemPane.setPrefWidth(value);
					ChatListFrame.this.setWidth(stageWidth + (value - itemWidth));
					ChatListFrame.this.setMinWidth(paneMinWidth + value);
				}
				me.consume();
			} else if (type == 7) {
			} else if (type == 8) {
			}
		});
	}

	public void addItem(ChatItem chatItem) {
		if (!itemBox.getChildren().contains(chatItem)) {
			itemBox.getChildren().add(chatItem);
		}
	}

	public void removeItem(ChatItem chatItem) {
		itemBox.getChildren().remove(chatItem);
	}

	public boolean isItemListEmpty() {
		return itemBox.getChildren().isEmpty();
	}


	public void setChatPanel(Node node) {
		chatPane.getChildren().clear();
		chatPane.getChildren().add(node);
	}

	private Cursor getCursor(MouseEvent me, Pane pane) {
		Cursor cursor = Cursor.DEFAULT;

		double grp = 3;
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
			cursor = Cursor.H_RESIZE;
			type = 6;
		} else if (y < grp) {
			// cursor = Cursor.V_RESIZE;
			type = 7;
		} else if (y > (height - grp)) {
			// cursor = Cursor.V_RESIZE;
			type = 8;
		} else {
			type = 0;
		}
		return cursor;
	}

}
