package com.oim.fx.ui.chat.pane;

import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MessageList extends StackPane {
	VBox box = new VBox();
	ScrollPane scrollPane = new ScrollPane();
	double v = 1.0D;

	public MessageList() {
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
			box.setPrefWidth(scrollPane.getWidth() - 10);
		});

		box.setPadding(new Insets(0, 15, 0, 15));
	}

	private void iniEvent() {
		// scrollPane.vvalueProperty().addListener(new ChangeListener<Number>()
		// {
		//
		// @Override
		// public void changed(ObservableValue<? extends Number> observable,
		// Number oldValue, Number newValue) {
		// scrollPane.setVvalue(oldValue.doubleValue());
		// }
		// });

		box.heightProperty().addListener((Observable observable) -> {
			updateV();
		});
	}

	public void addNode(Node node) {
		markV();
		if (!box.getChildren().contains(node)) {
			box.getChildren().add(node);
		}
		checkSize();
	}

	public void addNode(int index, Node node) {
		markV();
		if (box.getChildren().contains(node)) {
			box.getChildren().remove(node);
		}
		if (!box.getChildren().contains(node)) {
			box.getChildren().add(index, node);
		}
		checkSize();
	}

	void markV() {
		v = scrollPane.getVvalue();
	}

	void updateV() {
		scrollPane.setVvalue(v);
	}

	void checkSize() {
		int size = box.getChildren().size();
		if (size > 500) {
			box.getChildren().remove(0);
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
