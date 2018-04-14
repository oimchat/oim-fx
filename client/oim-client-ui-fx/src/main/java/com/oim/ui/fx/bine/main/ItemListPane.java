package com.oim.ui.fx.bine.main;

import com.oim.fx.ui.list.ListRootPanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ItemListPane extends StackPane {

	final BorderPane borderPane = new BorderPane();

	final StackPane stackPane = new StackPane();

	final HBox findBox = new HBox();
	final TextField textField = new TextField();

	final ListRootPanel listRootPane = new ListRootPanel();
	final ListRootPanel findListPane = new ListRootPanel();

	ImageView imageView = new ImageView();

	public ItemListPane() {
		initComponent();
		initEvent();
	}

	private void initComponent() {
		this.getChildren().add(borderPane);

		textField.getStyleClass().clear();
		textField.setBackground(Background.EMPTY);
//		textField.setStyle("-fx-border-color: #666666;\r\n" +
//				"    -fx-border-width: 1px;\r\n" +
//				"    -fx-border-style: solid;");

		
		VBox ivBox = new VBox();
		ivBox.setAlignment(Pos.CENTER);
		ivBox.setPadding(new Insets(5, 10, 5, 10));

		ivBox.getChildren().add(imageView);

		HBox ihBox = new HBox();
		ihBox.setAlignment(Pos.CENTER);
		ihBox.getChildren().add(ivBox);
		
		
		StringBuilder sb=new StringBuilder();
		sb.append("-fx-border-color: #dfdfdf;");
		sb.append("-fx-border-width: 1px;");
		sb.append("-fx-border-style: solid;");
		
		findBox.setStyle(sb.toString());

		findBox.getChildren().add(textField);
		findBox.getChildren().add(ihBox);
		
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setPadding(new Insets(15, 10, 15, 10));

		vBox.getChildren().add(findBox);

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(vBox);

		borderPane.setTop(hBox);

		borderPane.setCenter(stackPane);

		stackPane.getChildren().add(listRootPane);
		stackPane.getChildren().add(findListPane);

		listRootPane.setVisible(true);
		findListPane.setVisible(false);
	}

	private void initEvent() {
		textField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				String text = textField.getText();
				if (null != text && !text.isEmpty()) {
					listRootPane.setVisible(false);
					findListPane.setVisible(true);
				} else {
					listRootPane.setVisible(true);
					findListPane.setVisible(false);
				}
			}
		});
	}

	public BorderPane getBorderPane() {
		return borderPane;
	}

	public TextField getTextField() {
		return textField;
	}

	public void setFindImage(Image image) {
		imageView.setImage(image);
	}

	public ListRootPanel getFindListPane() {
		return findListPane;
	}

	public ListRootPanel getListRootPane() {
		return listRootPane;
	}
}
