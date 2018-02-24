package com.oim.server.api.tools.ui.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.only.fx.OnlyPopupOver;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * @author: XiaHui
 * @date: 2018-02-22 14:13:06
 */
public class AddressPane extends BorderPane {

	Pattern pattern = Pattern.compile("0|(-?([1-9]\\d*)?)");

	TextField addressField = new TextField();
	TextField portField = new TextField();
	Label addressLabel = new Label();
	Label portLabel = new Label();

	VBox vBox = new VBox();

	private OnlyPopupOver popupOver = new OnlyPopupOver();// 昵称提示
	private Label popupLabel = new Label();

	public AddressPane() {
		init();
	}

	private void init() {

		addressLabel.setText("服务器地址:");
		portLabel.setText("服务器端口:");

		addressField.setPromptText("服务器地址");
		portField.setPromptText("端口");

		addressField.setPrefSize(200, 30);
		portField.setPrefSize(200, 30);

		HBox addressHBox = new HBox();
		addressHBox.setSpacing(10);
		addressHBox.getChildren().add(addressLabel);
		addressHBox.getChildren().add(addressField);

		HBox portHBox = new HBox();
		portHBox.setSpacing(10);
		portHBox.getChildren().add(portLabel);
		portHBox.getChildren().add(portField);

		vBox.setSpacing(10);
		vBox.getChildren().add(addressHBox);
		vBox.getChildren().add(portHBox);

		popupLabel.setPadding(new Insets(4));

		popupOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);
		popupOver.setDetachable(false);
		popupOver.setDetached(false);
		popupOver.setContentNode(popupLabel);

		this.setCenter(vBox);

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

	public boolean verify() {
		String address = getAddress();
		int port = getPort();
		boolean mark = true;
		if (null == address || "".equals(address)) {
			popupLabel.setText("请输入服务器地址!");
			popupOver.show(addressField);
			addressField.requestFocus();
			mark = false;
			return mark;
		} else {
			mark = true;
		}

		if (port <= 0) {
			popupLabel.setText("请输入服务器端口!");
			popupOver.show(portField);
			if (mark) {
				portField.requestFocus();
			}
			mark = false;
			return mark;
		}
		return mark;
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
			port = Integer.parseInt(text);
		} catch (Exception e) {
		}
		return port;
	}
}
