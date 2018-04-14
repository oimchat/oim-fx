/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.ui.fx.classics;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.oim.fx.common.box.ImageBox;
import com.oim.fx.common.component.WaitingPane;
import com.oim.fx.common.component.list.KeyText;
import com.only.fx.OnlyPopupOver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

/**
 *
 * @author Only
 */
public class UserRegisterFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	TextField nicknameField = new TextField();
	PasswordField passwordField = new PasswordField();
	PasswordField confirmPasswordField = new PasswordField();
	ComboBox<KeyText> genderBox = new ComboBox<KeyText>();
	DatePicker birthdayPicker = new DatePicker();
	ComboBox<KeyText> bloodTypeBox = new ComboBox<KeyText>();
	TextField homeAddressField = new TextField();
	TextField locationAddressField = new TextField();
	TextField mobileField = new TextField();
	TextField emailField = new TextField();
	TextArea signatureTextArea = new TextArea();
	TextArea introduceTextArea = new TextArea();

	TextField questionField = new TextField();
	TextField answerField = new TextField();

	private OnlyPopupOver nicknameOver = new OnlyPopupOver();// 昵称提示
	private OnlyPopupOver passwordOver = new OnlyPopupOver();// 密码提示

	private Label nicknameOverLabel = new Label();
	private Label passwordOverLabel = new Label();

	private WaitingPane waitingPanel = new WaitingPane();

	public UserRegisterFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setTitle("注册");
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setWidth(400);
		this.setHeight(670);
		this.setCenter(rootPane);
		this.setResizable(false);

		birthdayPicker.setEditable(false);

		genderBox.setConverter(new StringConverter<KeyText>() {

			@Override
			public String toString(KeyText object) {
				return object.getText();
			}

			@Override
			public KeyText fromString(String string) {
				return null;
			}

		});

		bloodTypeBox.setConverter(new StringConverter<KeyText>() {

			@Override
			public String toString(KeyText object) {
				return object.getText();
			}

			@Override
			public KeyText fromString(String string) {
				return null;
			}

		});

		genderBox.getItems().addAll(new KeyText("1", "男"), new KeyText("2", "女"), new KeyText("3", "保密"));
		bloodTypeBox.getItems().add(new KeyText("", ""));
		bloodTypeBox.getItems().add(new KeyText("A", "A型"));
		bloodTypeBox.getItems().add(new KeyText("B", "B型"));
		bloodTypeBox.getItems().add(new KeyText("O", "O型"));
		bloodTypeBox.getItems().add(new KeyText("AB", "AB型"));
		bloodTypeBox.getItems().add(new KeyText("other", "其他血型"));

		Label nicknameLabel = new Label("昵 \t 称");
		Label passwordLabel = new Label("密 \t 码");
		Label confirmPasswordLabel = new Label("确认密码");
		Label genderLabel = new Label("性\t别");
		Label birthdayLabel = new Label("生\t日");
		Label bloodTypeLabel = new Label("血\t型");
		Label homeAddressLabel = new Label("故\t乡");
		Label locationAddressLabel = new Label("所在地");
		Label mobileLabel = new Label("手机号码");
		Label emailLabel = new Label("电子邮箱");
		Label signatureLabel = new Label("个性签名");
		Label introduceLabel = new Label("个人说明");

		Label questionLabel = new Label("密保问题");
		Label answerLabel = new Label("密保答案");

		nicknameField.setPromptText("昵称");
		passwordField.setPromptText("密码");
		confirmPasswordField.setPromptText("确认密码");
		genderBox.setPromptText("性别");
		birthdayPicker.setPromptText("生日");
		bloodTypeBox.setPromptText("血型");
		homeAddressField.setPromptText("故乡");
		locationAddressField.setPromptText("所在地");
		mobileField.setPromptText("手机号码");
		emailField.setPromptText("电子邮箱");
		signatureTextArea.setPromptText("个性签名");
		introduceTextArea.setPromptText("个人说明");

		questionField.setPromptText("密保问题");
		answerField.setPromptText("密保答案");

		nicknameLabel.setPrefSize(55, 25);
		nicknameLabel.setLayoutX(10);
		nicknameLabel.setLayoutY(20);
		nicknameField.setPrefSize(290, 25);
		nicknameField.setLayoutX(nicknameLabel.getLayoutX() + nicknameLabel.getPrefWidth() + 10);
		nicknameField.setLayoutY(nicknameLabel.getLayoutY());

		passwordLabel.setPrefSize(55, 25);
		passwordLabel.setLayoutX(10);
		passwordLabel.setLayoutY(nicknameField.getLayoutY() + nicknameField.getPrefHeight() + 15);
		passwordField.setPrefSize(290, 25);
		passwordField.setLayoutX(passwordLabel.getLayoutX() + passwordLabel.getPrefWidth() + 10);
		passwordField.setLayoutY(passwordLabel.getLayoutY());

		confirmPasswordLabel.setPrefSize(55, 25);
		confirmPasswordLabel.setLayoutX(10);
		confirmPasswordLabel.setLayoutY(passwordField.getLayoutY() + passwordField.getPrefHeight() + 15);
		confirmPasswordField.setPrefSize(290, 25);
		confirmPasswordField.setLayoutX(confirmPasswordLabel.getLayoutX() + confirmPasswordLabel.getPrefWidth() + 10);
		confirmPasswordField.setLayoutY(confirmPasswordLabel.getLayoutY());

		genderLabel.setPrefSize(55, 25);
		genderLabel.setLayoutX(10);
		genderLabel.setLayoutY(confirmPasswordField.getLayoutY() + confirmPasswordField.getPrefHeight() + 15);
		genderBox.setPrefSize(110, 25);
		genderBox.setLayoutX(genderLabel.getLayoutX() + genderLabel.getPrefWidth() + 10);
		genderBox.setLayoutY(genderLabel.getLayoutY());

		bloodTypeLabel.setPrefSize(55, 25);
		bloodTypeLabel.setLayoutX(genderBox.getLayoutX() + genderBox.getPrefWidth() + 10);
		bloodTypeLabel.setLayoutY(genderBox.getLayoutY());
		bloodTypeBox.setPrefSize(110, 25);
		bloodTypeBox.setLayoutX(bloodTypeLabel.getLayoutX() + bloodTypeLabel.getPrefWidth() + 10);
		bloodTypeBox.setLayoutY(bloodTypeLabel.getLayoutY());

		birthdayLabel.setPrefSize(55, 25);
		birthdayLabel.setLayoutX(10);
		birthdayLabel.setLayoutY(bloodTypeBox.getLayoutY() + bloodTypeBox.getPrefHeight() + 10);
		birthdayPicker.setPrefSize(290, 25);
		birthdayPicker.setLayoutX(birthdayLabel.getLayoutX() + birthdayLabel.getPrefWidth() + 10);
		birthdayPicker.setLayoutY(birthdayLabel.getLayoutY());

		homeAddressLabel.setPrefSize(55, 25);
		homeAddressLabel.setLayoutX(10);
		homeAddressLabel.setLayoutY(birthdayPicker.getLayoutY() + birthdayPicker.getPrefHeight() + 15);
		homeAddressField.setPrefSize(290, 25);
		homeAddressField.setLayoutX(nicknameField.getLayoutX());
		homeAddressField.setLayoutY(homeAddressLabel.getLayoutY());

		locationAddressLabel.setPrefSize(55, 25);
		locationAddressLabel.setLayoutX(10);
		locationAddressLabel.setLayoutY(homeAddressLabel.getLayoutY() + homeAddressLabel.getPrefHeight() + 15);
		locationAddressField.setPrefSize(290, 25);
		locationAddressField.setLayoutX(homeAddressField.getLayoutX());
		locationAddressField.setLayoutY(locationAddressLabel.getLayoutY());

		mobileLabel.setPrefSize(55, 25);
		mobileLabel.setLayoutX(10);
		mobileLabel.setLayoutY(locationAddressLabel.getLayoutY() + locationAddressLabel.getPrefHeight() + 15);
		mobileField.setPrefSize(290, 25);
		mobileField.setLayoutX(homeAddressField.getLayoutX());
		mobileField.setLayoutY(mobileLabel.getLayoutY());

		emailLabel.setPrefSize(55, 25);
		emailLabel.setLayoutX(10);
		emailLabel.setLayoutY(mobileLabel.getLayoutY() + mobileLabel.getPrefHeight() + 15);
		emailField.setPrefSize(290, 25);
		emailField.setLayoutX(homeAddressField.getLayoutX());
		emailField.setLayoutY(emailLabel.getLayoutY());

		signatureLabel.setPrefSize(55, 25);
		signatureLabel.setLayoutX(10);
		signatureLabel.setLayoutY(emailField.getLayoutY() + emailField.getPrefHeight() + 15);
		signatureTextArea.setPrefSize(290, 55);
		signatureTextArea.setLayoutX(homeAddressField.getLayoutX());
		signatureTextArea.setLayoutY(signatureLabel.getLayoutY());

		introduceLabel.setPrefSize(55, 25);
		introduceLabel.setLayoutX(10);
		introduceLabel.setLayoutY(signatureTextArea.getLayoutY() + signatureTextArea.getPrefHeight() + 15);
		introduceTextArea.setPrefSize(290, 55);
		introduceTextArea.setLayoutX(homeAddressField.getLayoutX());
		introduceTextArea.setLayoutY(introduceLabel.getLayoutY());

		questionLabel.setPrefSize(55, 25);
		questionLabel.setLayoutX(10);
		questionLabel.setLayoutY(introduceTextArea.getLayoutY() + introduceTextArea.getPrefHeight() + 15);
		questionField.setPrefSize(290, 25);
		questionField.setLayoutX(homeAddressField.getLayoutX());
		questionField.setLayoutY(questionLabel.getLayoutY());

		answerLabel.setPrefSize(55, 25);
		answerLabel.setLayoutX(10);
		answerLabel.setLayoutY(questionField.getLayoutY() + questionField.getPrefHeight() + 15);
		answerField.setPrefSize(290, 25);
		answerField.setLayoutX(homeAddressField.getLayoutX());
		answerField.setLayoutY(answerLabel.getLayoutY());

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(nicknameLabel);
		infoPane.getChildren().add(passwordLabel);
		infoPane.getChildren().add(confirmPasswordLabel);
		infoPane.getChildren().add(genderLabel);
		infoPane.getChildren().add(birthdayLabel);
		infoPane.getChildren().add(bloodTypeLabel);
		infoPane.getChildren().add(homeAddressLabel);
		infoPane.getChildren().add(locationAddressLabel);
		infoPane.getChildren().add(mobileLabel);
		infoPane.getChildren().add(emailLabel);
		infoPane.getChildren().add(signatureLabel);
		infoPane.getChildren().add(introduceLabel);

		infoPane.getChildren().add(questionLabel);
		infoPane.getChildren().add(answerLabel);

		infoPane.getChildren().add(nicknameField);
		infoPane.getChildren().add(passwordField);
		infoPane.getChildren().add(confirmPasswordField);
		infoPane.getChildren().add(genderBox);
		infoPane.getChildren().add(birthdayPicker);
		infoPane.getChildren().add(bloodTypeBox);
		infoPane.getChildren().add(homeAddressField);
		infoPane.getChildren().add(locationAddressField);
		infoPane.getChildren().add(mobileField);
		infoPane.getChildren().add(emailField);
		infoPane.getChildren().add(signatureTextArea);
		infoPane.getChildren().add(introduceTextArea);

		infoPane.getChildren().add(questionField);
		infoPane.getChildren().add(answerField);

		cancelButton.setText("关闭");
		cancelButton.setPrefWidth(80);

		button.setText("确定");
		button.setPrefWidth(80);

		bottomBox.setStyle("-fx-background-color:#c9e1e9");
		bottomBox.setAlignment(Pos.BASELINE_RIGHT);
		bottomBox.setPadding(new Insets(5, 10, 5, 10));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().add(button);
		bottomBox.getChildren().add(cancelButton);

		waitingPanel.add(WaitingPane.show_waiting, "注册...", ImageBox.getImageClassPath("/common/images/loading/loading_312_4.gif"));
		waitingPanel.show(WaitingPane.show_waiting);

		// waitingPanel.setPrefWidth(428);
		// waitingPanel.setPrefHeight(150);
		waitingPanel.setStyle("-fx-background-color:#ebf2f9;");

		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(waitingPanel);
		stackPane.getChildren().add(bottomBox);

		rootPane.setCenter(infoPane);
		rootPane.setBottom(stackPane);

		nicknameOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);
		passwordOver.setArrowLocation(OnlyPopupOver.ArrowLocation.TOP_LEFT);

		nicknameOver.setDetachable(false);
		nicknameOver.setDetached(false);

		passwordOver.setDetachable(false);
		passwordOver.setDetached(false);

		nicknameOverLabel.setPadding(new Insets(4));
		passwordOverLabel.setPadding(new Insets(4));

		nicknameOver.setContentNode(nicknameOverLabel);
		passwordOver.setContentNode(passwordOverLabel);

		showWaiting(false);
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			UserRegisterFrame.this.hide();
		});
	}

	public boolean verify() {
		String account = nicknameField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();
		boolean mark = true;
		if (null == account || "".equals(account)) {
			nicknameOverLabel.setText("请输入您的昵称!");
			nicknameOver.show(nicknameField);
			nicknameField.requestFocus();
			mark = false;
			return mark;
		} else {
			mark = true;
		}

		if (null == password || "".equals(password)) {
			passwordOverLabel.setText("请输入您的密码登录!");
			passwordOver.show(passwordField);
			if (mark) {
				passwordField.requestFocus();
			}
			mark = false;
			return mark;
		}

		if (null == confirmPassword || "".equals(confirmPassword)) {
			passwordOverLabel.setText("请再次输入您的密码登录!");
			passwordOver.show(confirmPasswordField);
			if (mark) {
				confirmPasswordField.requestFocus();
			}
			mark = false;
		} else {
			if (!confirmPassword.equals(password)) {
				passwordOverLabel.setText("两次输入的密码不相同!");
				passwordOver.show(confirmPasswordField);
				if (mark) {
					confirmPasswordField.requestFocus();
				}
				mark = false;
				return mark;
			}
		}
		return mark;
	}

	public void clearData() {
		nicknameField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
		genderBox.setValue(null);
		birthdayPicker.setValue(null);
		bloodTypeBox.setValue(null);
		homeAddressField.setText("");
		locationAddressField.setText("");
		mobileField.setText("");
		emailField.setText("");
		signatureTextArea.setText("");
		introduceTextArea.setText("");
		questionField.setText("");
		answerField.setText("");
	}

	public void showWaiting(boolean show) {
		waitingPanel.setVisible(show);
		bottomBox.setVisible(!show);
	}

	public void setOnDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}

	public void setNickname(String value) {
		nicknameField.setText(value);
	}

	public void setGender(String value) {
		genderBox.setValue(new KeyText(value, ""));
	}

	public void setBirthday(int year, int month, int dayOfMonth) {
		birthdayPicker.setValue(LocalDate.of(year, month, dayOfMonth));
	}

	public void setBloodType(String value) {
		// bloodTypeBox.setValue(value);
	}

	public void setHomeAddress(String value) {
		homeAddressField.setText(value);
	}

	public void setLocationAddress(String value) {
		locationAddressField.setText(value);
	}

	public void setMobile(String value) {
		mobileField.setText(value);
	}

	public void setEmail(String value) {
		emailField.setText(value);
	}

	public void setSignature(String value) {
		signatureTextArea.setText(value);
	}

	public void setIntroduce(String value) {
		introduceTextArea.setText(value);
	}

	/**************************/
	public String getNickname() {
		return nicknameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public String getGender() {
		String g = "3";
		KeyText kt = genderBox.getValue();
		return (null == kt) ? g : kt.getKey();
	}

	public Date getBirthday() {
		Date d = null;
		LocalDate ld = birthdayPicker.getValue();
		if (null != ld) {
			ZoneId zone = ZoneId.systemDefault();
			Instant instant = ld.atStartOfDay().atZone(zone).toInstant();
			d = Date.from(instant);
		}
		return d;
	}

	public String getBloodType() {
		String g = "other";
		KeyText kt = bloodTypeBox.getValue();
		return (null == kt) ? g : kt.getKey();
	}

	public String getHomeAddress() {
		return homeAddressField.getText();
	}

	public String getLocationAddress() {
		return locationAddressField.getText();
	}

	public String getMobile() {
		return mobileField.getText();
	}

	public String getEmail() {
		return emailField.getText();
	}

	public String getSignature() {
		return signatureTextArea.getText();
	}

	public String getIntroduce() {
		return introduceTextArea.getText();
	}

	public String getQuestion() {
		return questionField.getText();
	}

	public String getAnswer() {
		return answerField.getText();
	}
}
