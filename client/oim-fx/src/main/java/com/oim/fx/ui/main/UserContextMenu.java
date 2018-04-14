package com.oim.fx.ui.main;

import com.only.fx.common.action.EventAction;
import com.only.fx.common.component.OnlyMenuItem;
import com.onlyxiahui.im.bean.UserData;

import javafx.scene.control.ContextMenu;

/**
 * @author XiaHui
 * @date 2015年3月13日 上午10:03:25
 */
public class UserContextMenu extends ContextMenu {

	//private OnlyMenuItem updateMenuItem = new OnlyMenuItem();
	private OnlyMenuItem showMenuItem = new OnlyMenuItem();
	UserData userData;
	EventAction<UserData> updateEventAction;
	EventAction<UserData> showEventAction;

	public UserContextMenu() {
		initMenu();
		initEvent();
	}

	private void initMenu() {
		showMenuItem.setText("查看信息");
		//updateMenuItem.setText("修改群信息");
		this.getItems().add(showMenuItem);
		//this.getItems().add(updateMenuItem);
	}

	private void initEvent() {
//		updateMenuItem.setOnAction(a -> {
//			if (null != updateEventAction) {
//				updateEventAction.execute(userData);
//			}
//		});
		showMenuItem.setOnAction(a -> {
			if (null != showEventAction) {
				showEventAction.execute(userData);
			}
		});
	}

//	public void setUpdateAction(EventAction<UserData> value) {
//		updateEventAction = value;
//	}

	public void setShowAction(EventAction<UserData> value) {
		showEventAction = value;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
}
