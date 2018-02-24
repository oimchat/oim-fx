package com.oim.ui.fx.succinct.list;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-12 10:30:38
 */
public class ListNodePane extends BorderPane {

	private Label textLabel = new Label();
	private VBox centerVBox = new VBox();

	public ListNodePane() {
		initComponent();
		initEvent();
		initSet();
	}

	private void initComponent() {

		textLabel.setStyle("-fx-text-fill:#787b87;-fx-font-size: 14px;");
		
		HBox textHBox = new HBox();
		textHBox.setStyle("-fx-background-color:#292d32;");
		textHBox.setPadding(new Insets(4, 0, 4, 20));
		textHBox.getChildren().add(textLabel);
		
		this.setTop(textHBox);
		this.setCenter(centerVBox);

	}

	private void initEvent() {
		// TODO Auto-generated method stub

	}

	private void initSet() {
		// TODO Auto-generated method stub

	}

	public void setText(String value) {
		textLabel.setText(value);
	}

	public void addItem(Node node) {
		if (!centerVBox.getChildren().contains(node)) {
			centerVBox.getChildren().add(node);
		}
	}

	public void removeItem(Node node) {
		centerVBox.getChildren().remove(node);
	}

	public void clearItem() {
		centerVBox.getChildren().clear();
	}

	public int itemSize() {
		return centerVBox.getChildren().size();
	}

}
