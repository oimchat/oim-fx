package com.oim.core.business.manager;

import com.oim.core.business.view.MainView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;

/**
 * 
 * @author XiaHui
 * @date 2018-01-18 11:35:07
 */
public class GroupLastManager extends AbstractManager {

	public GroupLastManager(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	/**
	 * 
	 * @author XiaHui
	 * @date 2018-01-18 11:14:13
	 * @param group
	 */
	public void addOrUpdateLastGroup(Group group) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateLastGroup(group);
	}

	public void updateLastGroupChatInfo(String groupId, String text, String time) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateLastGroupChatInfo(groupId, text, time);
	}
	
	public void setLastGroupItemRed(String groupId, boolean red, int count) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.setLastGroupItemRed(groupId, red, count);
	}
}
