package com.oim.fx.common.component;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * @author: XiaHui
 * @date: 2017年5月24日 上午10:50:25
 */
public class SearchBox extends Region {

	private TextField textField;
	private Button clearButton;

	public SearchBox() {
		String searchBoxCss = SearchBox.class.getResource("/common/css/searchbox.css").toExternalForm();
		getStylesheets().add(searchBoxCss);
		setId("SearchBox");
		getStyleClass().add("search-box");
		setMinHeight(24);
		// setPrefSize(200, 24);
		setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		textField = new TextField();
		textField.setPromptText("查找");

		clearButton = new Button();
		clearButton.setVisible(false);
		getChildren().addAll(textField, clearButton);
		clearButton.setOnAction((ActionEvent actionEvent) -> {
			textField.setText("");
			textField.requestFocus();
		});

		textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			clearButton.setVisible(textField.getText().length() != 0);
		});
	}

	@Override
	protected void layoutChildren() {
		textField.resize(getWidth(), getHeight());
		clearButton.resizeRelocate(getWidth() - 18, 6, 12, 13);
	}

	public String getText() {
		return textField.getText();
	}

	public void setPromptText(String value) {
		textField.setPromptText(value);
	}

	public void setText(String value) {
		textField.setText(value);
	}

	public TextField getTextField() {
		return textField;
	}

}