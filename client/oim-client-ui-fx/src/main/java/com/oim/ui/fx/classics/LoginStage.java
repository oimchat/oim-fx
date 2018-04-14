package com.oim.ui.fx.classics;

import java.io.File;
import java.util.List;

import com.oim.ui.fx.classics.login.LoginPane;
import com.only.fx.OnlyTitlePane;
import com.only.fx.common.action.ValueAction;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * @author XiaHui
 * @date 2017-12-05 12:08:47
 */
public class LoginStage extends ClassicsStage {

	private Button settingButton = new Button();// 顶部设置按钮
	private LoginPane loginPane = new LoginPane();

	public LoginStage() {
		initComponent();
		initEvent();
	}

	private void initComponent() {
		this.getScene().getStylesheets().add(this.getClass().getResource("/classics/css/login.css").toString());
		this.setCenter(loginPane);
		this.setResizable(false);
		this.setTitlePaneStyle(2);
		settingButton.getStyleClass().remove("button");
		settingButton.getStyleClass().add("setting-button");
		settingButton.setGraphic(new ImageView());

		OnlyTitlePane titlePane = getOnlyTitlePane();
		titlePane.getChildren().add(0, settingButton);
	}

	private void initEvent() {
		this.setOnHidden(h -> {
			loginPane.stop();
		});
		this.setOnShowing(s -> {
			loginPane.play();
		});
	}

	public void setOnSettingAction(EventHandler<ActionEvent> value) {
		settingButton.setOnAction(value);
	}

	public boolean verify() {
		return loginPane.verify();
	}

	public void setAccountList(List<String> list) {
		loginPane.setAccountList(list);
	}

	public void removeAccount(String value) {
		loginPane.removeAccount(value);
	}

	public void setOnAccountRemove(ValueAction<String> valueAction) {
		loginPane.setOnAccountRemove(valueAction);
	}

	public void addAccountChangeListener(ChangeListener<String> listener) {
		loginPane.addAccountListener(listener);
	}

	public void showWaiting(boolean show) {
		loginPane.showWaiting(show);
	}

	public void setOnLoginAction(EventHandler<ActionEvent> value) {
		loginPane.setOnLoginAction(value);
	}

	public void setRegisterOnMouseClicked(EventHandler<MouseEvent> value) {
		loginPane.setRegisterOnMouseClicked(value);
	}

	public void setForgetOnMouseClicked(EventHandler<MouseEvent> value) {
		loginPane.setForgetOnMouseClicked(value);
	}

	public String getStatus() {
		return loginPane.getStatus();
	}

	public void setLogoImageView(Image image) {
	}

	public void setBackgroundImage(Image image) {
		loginPane.setBackgroundImage(image);
	}

	public void setHeadImage(Image headImage) {
		loginPane.setHeadImage(headImage);
	}

	public void setHeadSize(double value) {
		loginPane.setHeadSize(value);
	}

	public void setHeadRadius(double value) {
		loginPane.setHeadRadius(value);
	}

	public String getAccount() {
		return loginPane.getAccount();
	}

	public String getPassword() {
		return loginPane.getPassword();
	}

	public void setAccount(String value) {
		loginPane.setAccount(value);
	}

	public void setPassword(String value) {
		loginPane.setPassword(value);
	}

	public boolean isAutoLogin() {
		return loginPane.isAutoLogin();
	}

	public void setAutoLogin(boolean autoLogin) {
		loginPane.setAutoLogin(autoLogin);
	}

	public boolean isRememberPassword() {
		return loginPane.isRememberPassword();
	}

	public void setRememberPassword(boolean rememberPassword) {
		loginPane.setRememberPassword(rememberPassword);
	}

	public void setStatus(String status) {
		loginPane.setStatus(status);
	}

	public void setVideo(File file) {
		loginPane.setVideo(file);
	}
}
