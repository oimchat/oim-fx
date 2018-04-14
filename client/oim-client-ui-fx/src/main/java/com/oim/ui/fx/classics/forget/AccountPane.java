package com.oim.ui.fx.classics.forget;

import com.only.fx.OnlyPopupOver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AccountPane extends BorderPane {

	VBox baseVBox = new VBox();
	VBox infoVBox = new VBox();
	Label textLabel = new Label();
	TextField textField = new TextField();

	Button button = new Button();
	OnlyPopupOver over = new OnlyPopupOver();// 提示
	Label overLabel = new Label("账号不能为空！");

	public AccountPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setCenter(baseVBox);

		button.setText("下一步");
		textLabel.setText("请输入账号");
		textField.setPromptText("账号");

		infoVBox.setAlignment(Pos.CENTER);
		infoVBox.setSpacing(20);
		infoVBox.getChildren().add(textLabel);
		infoVBox.getChildren().add(textField);
		infoVBox.getChildren().add(button);

		baseVBox.setAlignment(Pos.CENTER);
		baseVBox.setPadding(new Insets(0,20,0,20));
		baseVBox.getChildren().add(infoVBox);

		over.setContentNode(overLabel);
	}

	private void iniEvent() {

	}

	public boolean verify() {
		String answer = textField.getText();
		boolean mark = true;
		if (null == answer || "".equals(answer)) {
			over.show(textField);
			textField.requestFocus();
			mark = false;
		} else {
			mark = true;
		}
		return mark;
	}

	public void setOnNextAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public String getAccount() {
		return textField.getText();
	}
}
