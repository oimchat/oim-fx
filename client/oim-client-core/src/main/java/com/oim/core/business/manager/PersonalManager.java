package com.oim.core.business.manager;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.view.MainView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午1:37:57
 */
public class PersonalManager extends AbstractManager {

	public PersonalManager(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 更新个人在线状态
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param status
	 */
	public void updateStatus(String status) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		LoginData loginData = pb.getLoginData();
		loginData.setStatus(status);
		UserSender us = appContext.getSender(UserSender.class);
		us.sendUpdateStatus(loginData.getStatus());

		if (UserData.status_offline.equals(status)) {
			logout();
		} else {
			NetModule nm = appContext.getModule(NetModule.class);
			if (!nm.getConnectThread().isConnected()) {
				autoConnect();
			} else {
				MainView mainView = this.appContext.getSingleView(MainView.class);
				mainView.setStatus(status);
			}
		}
	}

	public void autoConnect() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.getConnectThread().setAutoConnect(true);
	}

	public void logout() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.getConnectThread().setAutoConnect(false);// 另外登录了账号后，设置为不自动连接
		nm.getConnectThread().closeConnect();// 关闭连接
	}
}
