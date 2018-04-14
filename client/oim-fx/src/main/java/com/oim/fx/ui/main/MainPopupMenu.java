package com.oim.fx.ui.main;

import com.only.fx.common.component.OnlyMenuItem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SeparatorMenuItem;

/**
 * @author XiaHui
 * @date 2015年3月13日 上午10:03:25
 */
public class MainPopupMenu extends StatusPopupMenu {

	private SeparatorMenuItem separator4 = new SeparatorMenuItem();

	protected OnlyMenuItem updatePasswordMenuItem = new OnlyMenuItem();
	private OnlyMenuItem updateMenuItem = new OnlyMenuItem();
	private OnlyMenuItem quitMenuItem = new OnlyMenuItem();

	public MainPopupMenu() {
		initMenu();
		initEvent();
	}

	private void initMenu() {
		updateMenuItem.setText("修改资料");
		updatePasswordMenuItem.setText("修改密码");
		quitMenuItem.setText("退出");

		this.getItems().add(separator4);
		this.getItems().add(updateMenuItem);
		this.getItems().add(updatePasswordMenuItem);
		this.getItems().add(quitMenuItem);

	}

	private void initEvent() {

	}

	public void setQuitAction(EventHandler<ActionEvent> value) {
		quitMenuItem.setOnAction(value);
	}

	public void setUpdateAction(EventHandler<ActionEvent> value) {
		updateMenuItem.setOnAction(value);
	}

	public void setUpdatePasswordAction(EventHandler<ActionEvent> value) {
		this.updatePasswordMenuItem.setOnAction(value);
	}
}
