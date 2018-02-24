package com.oim.core.business.manager;

import com.oim.core.business.view.MainView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;

/**
 * 描述：对主界面的记录列表的管理
 * 
 * @author XiaHui
 * @date 2015年4月12日 上午10:18:18
 * @version 0.0.1
 */
public class UserLastManager extends AbstractManager {

	public UserLastManager(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	/**
	 * 
	 * @author XiaHui
	 * @date 2018-01-18 11:14:13
	 * @param userData
	 */
	public void addOrUpdateLastUser(UserData userData) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateLastUser(userData);
	}

	public void updateLastUserChatInfo(String userId, String text, String time) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateLastUserChatInfo(userId, text, time);
	}
	
	public void setLastUserItemRed(String userId, boolean red, int count) {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.setLastUserItemRed(userId, red, count);
	}
}
