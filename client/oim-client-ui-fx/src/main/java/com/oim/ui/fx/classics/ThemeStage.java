package com.oim.ui.fx.classics;

import java.util.List;

import com.oim.fx.common.component.IconButton;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ThemeStage extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	Label titleLabel = new Label();

	BorderPane borderPane = new BorderPane();
	FlowPane pane = new FlowPane();
	ScrollPane scrollPane = new ScrollPane();

	Slider slider = new Slider();
	ColorPicker colorPicker = new ColorPicker();

	public ThemeStage() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(750);
		this.setHeight(540);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("外观设置");

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		button.setText("确定");
		button.setPrefWidth(80);

		rootPane.setTop(topBox);
		rootPane.setCenter(borderPane);
		rootPane.setBottom(bottomBox);

		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setContent(pane);
		scrollPane.widthProperty().addListener((Observable observable) -> {
			pane.setMinWidth(scrollPane.getWidth());
		});

		borderPane.setCenter(scrollPane);

		Tooltip tooltip = new Tooltip("背景覆盖颜色");
		colorPicker.setTooltip(tooltip);

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().add(slider);
		slider.setTooltip(new Tooltip("背景覆盖颜色透明度"));

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(vBox);
		bottomBox.getChildren().add(colorPicker);
		bottomBox.getChildren().add(cancelButton);
		bottomBox.getChildren().add(button);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			ThemeStage.this.hide();
		});
		slider.valueProperty().addListener(l -> {
			double opacity = slider.getValue();
			opacity = opacity / 100D;
			setColorOpacity(opacity);
		});
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Color newColor = colorPicker.getValue();
				setBackgroundColor(newColor);
			}
		});
	}

	private void setColorOpacity(double opacity) {
		Color color = colorPicker.getValue();
		color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
		colorPicker.setValue(color);
		super.setBackgroundColor(color);
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setImageStyleButtonList(List<IconButton> list) {
		pane.getChildren().clear();
		if (null != list) {
			for (IconButton b : list) {
				b.setCursor(Cursor.HAND);
				pane.getChildren().add(b);
			}
		}
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}

	public void setColor(Color color) {
		double opacity = color.getOpacity();
		opacity = opacity * 100D;
		slider.setValue(opacity);
		colorPicker.setValue(color);
		super.setBackgroundColor(color);
	}

	public Color getColor() {
		return colorPicker.getValue();
	}
}