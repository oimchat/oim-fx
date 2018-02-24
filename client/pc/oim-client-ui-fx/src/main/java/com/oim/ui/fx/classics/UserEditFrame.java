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
import java.util.HashMap;
import java.util.Map;

import com.oim.fx.common.component.list.KeyText;
import com.oim.fx.ui.list.HeadImagePanel;
import com.only.fx.OnlyPopupOver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

/**
 *
 * @author Only
 */
public class UserEditFrame extends CommonStage {

	BorderPane rootPane = new BorderPane();

	HBox topBox = new HBox();
	HBox bottomBox = new HBox();
	Button button = new Button();
	Button cancelButton = new Button();

	HeadImagePanel headPane = new HeadImagePanel();
	
	TextField nicknameField = new TextField();
	ComboBox<KeyText> genderBox = new ComboBox<KeyText>();
	DatePicker birthdayPicker = new DatePicker();
	ComboBox<KeyText> bloodTypeBox = new ComboBox<KeyText>();
	TextField homeAddressField = new TextField();
	TextField locationAddressField = new TextField();
	TextField mobileField = new TextField();
	TextField emailField = new TextField();
	TextArea signatureTextArea = new TextArea();
	TextArea introduceTextArea = new TextArea();

	Map<String, String> genderMap = new HashMap<String, String>();
	Map<String, String> bloodMap = new HashMap<String, String>();
	Label titleLabel = new Label();
	
	private OnlyPopupOver nicknameOver = new OnlyPopupOver();// 昵称提示
	private Label nicknameOverLabel = new Label();
	
	public UserEditFrame() {
		initComponent();
		iniEvent();
	}

	private void initComponent() {
		this.setWidth(400);
		this.setHeight(550);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		this.setRadius(5);
		this.setCenter(rootPane);
		this.setTitle("修改资料");

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

		genderMap.put("1", "男");
		genderMap.put("2", "女");
		genderMap.put("3", "保密");

		bloodMap.put("", "");
		bloodMap.put("A", "A型");
		bloodMap.put("B", "B型");
		bloodMap.put("O", "O型");
		bloodMap.put("AB", "AB型");
		bloodMap.put("other", "其他血型");

		genderBox.getItems().addAll(new KeyText("1", "男"), new KeyText("2", "女"), new KeyText("3", "保密"));
		bloodTypeBox.getItems().add(new KeyText("", ""));
		bloodTypeBox.getItems().add(new KeyText("A", "A型"));
		bloodTypeBox.getItems().add(new KeyText("B", "B型"));
		bloodTypeBox.getItems().add(new KeyText("O", "O型"));
		bloodTypeBox.getItems().add(new KeyText("AB", "AB型"));
		bloodTypeBox.getItems().add(new KeyText("other", "其他血型"));

		Label nicknameLabel = new Label("昵 \t 称");
		Label genderLabel = new Label("性\t别");
		Label birthdayLabel = new Label("生\t日");
		Label bloodTypeLabel = new Label("血\t型");
		Label homeAddressLabel = new Label("故\t乡");
		Label locationAddressLabel = new Label("所在地");
		Label mobileLabel = new Label("手机号码");
		Label emailLabel = new Label("电子邮箱");
		Label signatureLabel = new Label("个性签名");
		Label introduceLabel = new Label("个人说明");

		nicknameField.setPromptText("昵称");
		genderBox.setPromptText("性别");
		birthdayPicker.setPromptText("生日");
		bloodTypeBox.setPromptText("血型");
		homeAddressField.setPromptText("故乡");
		locationAddressField.setPromptText("所在地");
		mobileField.setPromptText("手机号码");
		emailField.setPromptText("电子邮箱");
		signatureTextArea.setPromptText("个性签名");
		introduceTextArea.setPromptText("个人说明");

		headPane.setHeadSize(65);
		headPane.setHeadRadius(65);
		headPane.setPrefSize(65, 65);
		headPane.setLayoutX(70);
		headPane.setLayoutY(10);
		
		nicknameLabel.setPrefSize(55, 25);
		nicknameLabel.setLayoutX(10);
		nicknameLabel.setLayoutY(100);
		nicknameField.setPrefSize(110, 25);
		nicknameField.setLayoutX(nicknameLabel.getLayoutX() + nicknameLabel.getPrefWidth() + 10);
		nicknameField.setLayoutY(nicknameLabel.getLayoutY());

		genderLabel.setPrefSize(55, 25);
		genderLabel.setLayoutX(nicknameField.getLayoutX() + nicknameField.getPrefWidth() + 10);
		genderLabel.setLayoutY(100);
		genderBox.setPrefSize(110, 25);
		genderBox.setLayoutX(genderLabel.getLayoutX() + genderLabel.getPrefWidth() + 10);
		genderBox.setLayoutY(genderLabel.getLayoutY());

		birthdayLabel.setPrefSize(55, 25);
		birthdayLabel.setLayoutX(10);
		birthdayLabel.setLayoutY(nicknameLabel.getLayoutY() + nicknameLabel.getPrefHeight() + 10);
		birthdayPicker.setPrefSize(110, 25);
		birthdayPicker.setLayoutX(nicknameField.getLayoutX());
		birthdayPicker.setLayoutY(birthdayLabel.getLayoutY());

		bloodTypeLabel.setPrefSize(55, 25);
		bloodTypeLabel.setLayoutX(genderLabel.getLayoutX());
		bloodTypeLabel.setLayoutY(birthdayLabel.getLayoutY());
		bloodTypeBox.setPrefSize(110, 25);
		bloodTypeBox.setLayoutX(genderBox.getLayoutX());
		bloodTypeBox.setLayoutY(bloodTypeLabel.getLayoutY());

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

		AnchorPane infoPane = new AnchorPane();
		infoPane.setStyle("-fx-background-color:#ffffff");
		infoPane.getChildren().add(nicknameLabel);
		infoPane.getChildren().add(genderLabel);
		infoPane.getChildren().add(birthdayLabel);
		infoPane.getChildren().add(bloodTypeLabel);
		infoPane.getChildren().add(homeAddressLabel);
		infoPane.getChildren().add(locationAddressLabel);
		infoPane.getChildren().add(mobileLabel);
		infoPane.getChildren().add(emailLabel);
		infoPane.getChildren().add(signatureLabel);
		infoPane.getChildren().add(introduceLabel);
		
		infoPane.getChildren().add(headPane);
		infoPane.getChildren().add(nicknameField);
		infoPane.getChildren().add(genderBox);
		infoPane.getChildren().add(birthdayPicker);
		infoPane.getChildren().add(bloodTypeBox);
		infoPane.getChildren().add(homeAddressField);
		infoPane.getChildren().add(locationAddressField);
		infoPane.getChildren().add(mobileField);
		infoPane.getChildren().add(emailField);
		infoPane.getChildren().add(signatureTextArea);
		infoPane.getChildren().add(introduceTextArea);

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
	}

	private void iniEvent() {
		cancelButton.setOnAction(a -> {
			UserEditFrame.this.hide();
		});
	}
	
	public boolean verify() {
		String account = nicknameField.getText();
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
		return mark;
	}
	
	public void setOnDoneAction(EventHandler<ActionEvent> value) {
		button.setOnAction(value);
	}
	
	public void setOnHeadMouseClicked(EventHandler<? super MouseEvent> value){
		headPane.setOnMouseClicked(value);
	}
	
	public void setTitleText(String value) {
		this.setTitle(value);
		titleLabel.setText(value);
    }
	
	public void setNickname(String value) {
		nicknameField.setText(value);
	}

	public void setGender(String value) {
		genderBox.setValue(new KeyText(value, genderMap.get(value)));
	}

	public void setBirthday(int year, int month, int dayOfMonth) {
		birthdayPicker.setValue(LocalDate.of(year, month, dayOfMonth));
	}
	
	public void setBirthday(Date date) {
		if(null==date){
			birthdayPicker.setValue(null);
		}else{
			birthdayPicker.setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
	}

	public void setBloodType(String value) {
		bloodTypeBox.setValue(new KeyText(value, bloodMap.get(value)));
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
	
	public void setHeadImage(Image image){
		headPane.setImage(image);
	}

	/**************************/
	public String getNickname() {
		return nicknameField.getText();
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
	
}
