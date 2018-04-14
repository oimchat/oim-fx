package com.oim.ui.fx.classics.setting;

import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author XiaHui
 * @date 2017-12-08 20:34:10
 */
public class LoginSettingPane extends VBox {

	private final HBox autoHBox = new HBox();
	protected JFXCheckBox autoCheckBox = new JFXCheckBox();// 自动登录

	public LoginSettingPane() {
		initComponent();
		initEvent();
	}

	private void initComponent() {
		this.getChildren().add(autoHBox);
		this.getChildren().add(autoCheckBox);

		autoCheckBox.setText("自动登录");
	}

	private void initEvent() {

	}

	public boolean isAutoLogin() {
		return autoCheckBox.isSelected();
	}

	public void setAutoLogin(boolean autoLogin) {
		autoCheckBox.setSelected(autoLogin);
	}

	public void setOnAutoLoginAction(EventHandler<ActionEvent> value) {
		autoCheckBox.setOnAction(value);
	}

	public void addAutoLoginChangeListener(ChangeListener<? super Boolean> listener) {
		autoCheckBox.selectedProperty().addListener(listener);
	}
}
