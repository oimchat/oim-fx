/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import com.oim.fx.common.component.list.KeyText;
import com.oim.ui.fx.classics.add.InfoPane;
import com.only.fx.common.action.EventAction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author Only
 */
public class AddFrame extends CommonStage {

	TextInputDialog textInput = new TextInputDialog("");
	BorderPane rootPane = new BorderPane();
	InfoPane infoPanel = new InfoPane();

	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	TextField remarkText = new TextField();
	ComboBox<KeyText> categoryBox = new ComboBox<KeyText>();
	Button newButton = new Button();
	EventAction<String> eventAction;
	
	public AddFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(460);
		this.setHeight(355);
		this.setCenter(rootPane);
		
		textInput.setTitle("输入分组");
		textInput.setContentText("名称:");

		infoPanel.setHeadSize(100);
		infoPanel.setMaxWidth(130);
		infoPanel.setStyle("-fx-background-color:#e8f0f3");

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		button.setText("确定");
		button.setPrefWidth(80);

		categoryBox.getSelectionModel().select(0);
		categoryBox.setConverter(new StringConverter<KeyText>() {

			@Override
			public String toString(KeyText object) {
				return object.getText();
			}

			@Override
			public KeyText fromString(String string) {
				return null;
			}

		});
		categoryBox.setCellFactory(new Callback<ListView<KeyText>, ListCell<KeyText>>() {
			@Override
			public ListCell<KeyText> call(ListView<KeyText> param) {
				ListCell<KeyText> cell = new ListCell<KeyText>() {
					{
						super.setPrefWidth(100);
					}

					@Override
					public void updateItem(KeyText item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.getText());
						} else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});

		remarkText.setPromptText("备注 选填");
		newButton.setText("新建分组");

		remarkText.setLayoutX(75);
		remarkText.setLayoutY(20);
		remarkText.setPrefSize(155, 23);

		categoryBox.setLayoutX(75);
		categoryBox.setLayoutY(55);
		categoryBox.setPrefSize(155, 23);

		newButton.setLayoutX(235);
		newButton.setLayoutY(55);
		newButton.setPrefSize(70, 23);

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(remarkText);
		infoPane.getChildren().add(categoryBox);
		infoPane.getChildren().add(newButton);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(button);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setLeft(infoPanel);
		rootPane.setCenter(infoPane);
		rootPane.setBottom(bottomBox);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			AddFrame.this.hide();
		});
		newButton.setOnAction(a -> {
			textInput.showAndWait().ifPresent(response -> {
				if (null==response||response.isEmpty()) {
					// System.out.println("No name was inserted");
				} else {
					if (null != eventAction) {
						eventAction.execute(response);
					}
				}
			});
		});
	}

	public void clearData() {
		remarkText.setText("");
		textInput.getEditor().setText("");
	}

	public InfoPane getInfoPanel() {
		return infoPanel;
	}

	public String getRemark() {
		return remarkText.getText();
	}

	public void addCategory(String id, String name) {
		categoryBox.getItems().add(new KeyText(id, name));
	}

	public void selectCategory(int index) {
		categoryBox.getSelectionModel().select(index);
	}

	public String getCategoryId() {
		KeyText kt = categoryBox.getValue();
		return (null == kt) ? null : kt.getKey();
	}

	public void clearCategory() {
		categoryBox.getItems().clear();
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setNewCategoryAction(EventAction<String> eventAction) {
		this.eventAction = eventAction;
	}

}
