package com.oim.ui.fx.classics.component;

import java.util.List;

import com.only.fx.common.action.ValueAction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

/**
 * @author XiaHui
 * @date 2017-11-28 10:35:19
 */
public class FreeComboBox<T> extends BorderPane {

	private static final String DEFAULT_STYLE_CLASS = "free-combo-box";
	private final TextField textField = new TextField();// 账号输入框
	// private final Button button = new Button();
	private final ImageView arrows = new ImageView();
	private ListPopup<T> autoCompletionPopup = new ListPopup<T>();
	ValueAction<T> selectedValueAction;
	ValueAction<T> removeValueAction;
	private final StackPane arrowButton = new StackPane();

	public FreeComboBox() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.getStyleClass().add(DEFAULT_STYLE_CLASS);

		textField.getStyleClass().remove("text-field");
		textField.setBackground(Background.EMPTY);
		textField.setBorder(Border.EMPTY);

		// button.getStyleClass().remove("button");
		// button.setGraphic(arrows);
		// button.getStyleClass().add("free-arrow-button");

		arrowButton.getChildren().add(arrows);
		// pane.getChildren().add(button);
		arrowButton.getStyleClass().add("free-arrow-button");

		this.setCenter(textField);
		this.setRight(arrowButton);
	}

	private void iniEvent() {
		this.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				autoCompletionPopup.setPrefWidth(newValue.doubleValue());
			}
		});
		arrowButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (null != autoCompletionPopup) {
					autoCompletionPopup.show(textField);
				}
				event.consume();
			}
		});
		// button.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// if (null != autoCompletionPopup) {
		// autoCompletionPopup.show(textField);
		// }
		// event.consume();
		// }
		// });

		autoCompletionPopup.setOnSuggestion(sce -> {
			autoCompletionPopup.hide();
			T t = sce.getSuggestion();
			if (null != t) {
				StringConverter<T> converter = autoCompletionPopup.getConverter();
				textField.setText(null != converter ? converter.toString(t) : t.toString());
			}
			if (null != selectedValueAction) {
				selectedValueAction.value(sce.getSuggestion());
			}
		});

		autoCompletionPopup.setOnRemove(sce -> {
			autoCompletionPopup.hide();
			if (null != removeValueAction) {
				removeValueAction.value(sce.getRemove());
			}
		});
	}

	public void setOnSelected(ValueAction<T> valueAction) {
		selectedValueAction = valueAction;
	}

	public void setOnRemove(ValueAction<T> valueAction) {
		removeValueAction = valueAction;
	}

	public void setStringConverter(StringConverter<T> converter) {
		autoCompletionPopup.setConverter(converter);
	}

	public void setList(List<T> list) {
		autoCompletionPopup.getSuggestions().setAll(list);
	}

	public void remove(T value) {
		autoCompletionPopup.getSuggestions().remove(value);
	}

	public void addTextChangeListener(ChangeListener<? super String> listener) {
		textField.textProperty().addListener(listener);
	}

	public String getText() {
		return textField.getText();
	}

	public void setText(String text) {
		textField.setText(text);
	}

	public void setPromptText(String text) {
		textField.setPromptText(text);
	}

	public StackPane getArrowButton() {
		return arrowButton;
	}

	public TextField getTextField() {
		return textField;
	}

}
