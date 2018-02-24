package com.oim.ui.fx.classics.forget;

import java.util.List;

import com.only.fx.OnlyPopupOver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * 忘记密码重设密码
 * @author: XiaHui
 *
 */
public class QuestionPane extends VBox {

	private BorderPane baseBorderPane=new BorderPane();
	private VBox topVBox = new VBox();

	private PasswordField passwordField = new PasswordField();
	private PasswordField confirmPasswordField = new PasswordField();
	private Button upButton = new Button();
	private Button button = new Button();
	private OnlyPopupOver over = new OnlyPopupOver();// 密码提示

	private Label overLabel = new Label();
	private VBox box = new VBox();
	private ScrollPane scrollPane = new ScrollPane();

	public QuestionPane() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setStyle("-fx-background-color:#ffffff");
		upButton.setText("上一步");
		button.setText("确定");

		upButton.setPrefSize(80, 25);
		button.setPrefSize(80, 25);
		
		Label passwordLabel = new Label("新密码");
		Label confirmPasswordLabel = new Label("确认密码");

		passwordField.setPromptText("新密码");
		confirmPasswordField.setPromptText("确认密码");

		passwordLabel.setPrefSize(50, 25);
		passwordLabel.setLayoutX(10);
		passwordLabel.setLayoutY(15);
		passwordField.setPrefSize(290, 25);
		passwordField.setLayoutX(passwordLabel.getLayoutX() + passwordLabel.getPrefWidth() + 10);
		passwordField.setLayoutY(passwordLabel.getLayoutY());

		confirmPasswordLabel.setPrefSize(50, 25);
		confirmPasswordLabel.setLayoutX(10);
		confirmPasswordLabel.setLayoutY(passwordField.getLayoutY() + passwordField.getPrefHeight() + 15);
		confirmPasswordField.setPrefSize(290, 25);
		confirmPasswordField.setLayoutX(confirmPasswordLabel.getLayoutX() + confirmPasswordLabel.getPrefWidth() + 10);
		confirmPasswordField.setLayoutY(confirmPasswordLabel.getLayoutY());

		AnchorPane infoPane = new AnchorPane();

		infoPane.getChildren().add(passwordLabel);
		infoPane.getChildren().add(confirmPasswordLabel);

		infoPane.getChildren().add(passwordField);
		infoPane.getChildren().add(confirmPasswordField);
		infoPane.setPrefHeight(confirmPasswordField.getLayoutY() + 50);

		topVBox.getChildren().add(infoPane);

		scrollPane.setBackground(Background.EMPTY);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setContent(box);

		HBox bottomBox = new HBox();
		bottomBox.setAlignment(Pos.CENTER);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(upButton);
		bottomBox.getChildren().add(button);

		over.setContentNode(overLabel);
		
		baseBorderPane.setTop(topVBox);
		baseBorderPane.setCenter(scrollPane);
		baseBorderPane.setBottom(bottomBox);
		
		this.getChildren().add(baseBorderPane);
	}

	private void iniEvent() {

	}

	public boolean verify() {
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();
		boolean mark = true;

		if (null == password || "".equals(password)) {
			overLabel.setText("请输入您的新密码!");
			over.show(passwordField);
			if (mark) {
				passwordField.requestFocus();
			}
			mark = false;
			return mark;
		}

		if (null == confirmPassword || "".equals(confirmPassword)) {
			overLabel.setText("请再次输入您的密码!");
			over.show(confirmPasswordField);
			if (mark) {
				confirmPasswordField.requestFocus();
			}
			mark = false;
		} else {
			if (!confirmPassword.equals(password)) {
				overLabel.setText("两次输入的密码不相同!");
				over.show(confirmPasswordField);
				if (mark) {
					confirmPasswordField.requestFocus();
				}
				mark = false;
			}
		}
		return mark;
	}

	public void setQuestionItemList(List<QuestionItem> nodeList) {
		box.getChildren().clear();
		if (null != nodeList) {
			for (Node node : nodeList) {
				box.getChildren().add(node);
			}
		}
	}

	public String getPassword() {
		return passwordField.getText();
	}

	/**
	 * 
	 * @author: XiaHui
	 * @param value
	 * @createDate: 2017年6月8日 下午2:48:59
	 * @update: XiaHui
	 * @updateDate: 2017年6月8日 下午2:48:59
	 */
	public void setOnDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setOnUpAction(EventHandler<ActionEvent> value) {
		upButton.setOnAction(value);
	}

	public void setDoneButtonVisible(boolean value) {
		button.setVisible(value);
	}
}
