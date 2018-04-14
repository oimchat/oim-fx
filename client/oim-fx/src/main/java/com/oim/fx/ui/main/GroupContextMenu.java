package com.oim.fx.ui.main;

import com.only.fx.common.action.EventAction;
import com.only.fx.common.component.OnlyMenuItem;
import com.onlyxiahui.im.bean.Group;

import javafx.scene.control.ContextMenu;

/**
 * @author XiaHui
 * @date 2015年3月13日 上午10:03:25
 */
public class GroupContextMenu extends ContextMenu {

	private OnlyMenuItem updateMenuItem = new OnlyMenuItem();
	private OnlyMenuItem showMenuItem = new OnlyMenuItem();
	Group group;
	EventAction<Group> updateEventAction;
	EventAction<Group> showEventAction;

	public GroupContextMenu() {
		initMenu();
		initEvent();
	}

	private void initMenu() {
		showMenuItem.setText("查看群信息");
		updateMenuItem.setText("修改群信息");
		this.getItems().add(showMenuItem);
		this.getItems().add(updateMenuItem);
	}

	private void initEvent() {
		updateMenuItem.setOnAction(a -> {
			if (null != updateEventAction) {
				updateEventAction.execute(group);
			}
		});
		showMenuItem.setOnAction(a -> {
			if (null != showEventAction) {
				showEventAction.execute(group);
			}
		});
	}

	public void setUpdateAction(EventAction<Group> value) {
		updateEventAction = value;
	}

	public void setShowAction(EventAction<Group> value) {
		showEventAction = value;
	}
	
	public void showEdit(boolean show){
		updateMenuItem.setVisible(show);
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}
