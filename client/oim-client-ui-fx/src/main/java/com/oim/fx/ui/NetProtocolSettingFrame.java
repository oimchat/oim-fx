/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.fx.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

/**
 *
 * @author Only
 */
public class NetProtocolSettingFrame extends BaseFrame {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	Label titleLabel = new Label();

	TextField addressField = new TextField();
	TextField portField = new TextField();

	public NetProtocolSettingFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(520);
		this.setHeight(120);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("服务器地址设置");

		titleLabel.setText("服务器地址设置");
		titleLabel.setFont(Font.font("微软雅黑", 14));
		titleLabel.setStyle("-fx-text-fill:rgba(255, 255, 255, 1)");

		topBox.setStyle("-fx-background-color:#2cb1e0");
		topBox.setPrefHeight(35);
		topBox.setPadding(new Insets(5, 10, 5, 10));
		topBox.setSpacing(10);
		topBox.getChildren().add(titleLabel);

		Label addressLabel = new Label("服务器地址：");

		addressField.setPromptText("服务器地址");

		addressLabel.setPrefSize(75, 25);
		addressLabel.setLayoutX(10);
		addressLabel.setLayoutY(5);
		addressField.setPrefSize(250, 25);
		addressField.setLayoutX(addressLabel.getLayoutX() + addressLabel.getPrefWidth() + 10);
		addressField.setLayoutY(addressLabel.getLayoutY());

		portField.setPromptText("端口");
		Label partLabel = new Label("端口:");

		partLabel.setPrefSize(40, 25);
		partLabel.setLayoutX(355);
		partLabel.setLayoutY(5);
		portField.setPrefSize(80, 25);
		portField.setLayoutX(partLabel.getLayoutX() + partLabel.getPrefWidth() + 10);
		portField.setLayoutY(partLabel.getLayoutY());

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(addressLabel);
		infoPane.getChildren().add(addressField);

		infoPane.getChildren().add(partLabel);
		infoPane.getChildren().add(portField);

		cancelButton.setText("取消");
		cancelButton.setPrefWidth(80);

		button.setText("确定");
		button.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(button);
		bottomBox.getChildren().add(cancelButton);

		rootPane.setTop(topBox);
		rootPane.setCenter(infoPane);
		rootPane.setBottom(bottomBox);
		Pattern pattern = Pattern.compile("0|(-?([1-9]\\d*)?)");
		;
		TextFormatter<Integer> formatter = new TextFormatter<Integer>(new StringConverter<Integer>() {

			@Override
			public String toString(Integer value) {
				return null != value ? value.toString() : "0";
			}

			@Override
			public Integer fromString(String text) {
				int i = 0;
				if (null != text) {
					Matcher matcher = pattern.matcher(text);
					if (matcher.matches()) {
						i = Integer.parseInt(text);
					}
				}
				return i;
			}
		});
		portField.setTextFormatter(formatter);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			NetProtocolSettingFrame.this.hide();
		});
	}

	public void setDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setAddress(String value) {
		addressField.setText(value);
	}

	public String getAddress() {
		return addressField.getText();
	}

	public void setPort(int port) {
		this.portField.setText(port + "");
	}

	public int getPort() {
		String text = this.portField.getText();
		int port = 0;
		try {
			port =Integer.parseInt(text);
		} catch (Exception e) {
		}
		return port;
	}

	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
	}
}
