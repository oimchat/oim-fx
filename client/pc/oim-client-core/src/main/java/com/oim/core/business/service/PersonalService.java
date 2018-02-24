package com.oim.core.business.service;

import com.oim.common.util.FileLockUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.SystemManager;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.view.LoginView;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.TrayView;
import com.oim.core.common.AppConstant;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.LoginConfig;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.only.common.result.ErrorInfo;
import com.only.common.result.Info;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.app.base.task.ExecuteTask;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2016-01-07 08:37:18
 * @version 0.0.1
 */
public class PersonalService extends AbstractService {

	public PersonalService(AppContext appContext) {
		super(appContext);
	}

	public void login() {

	}

	/**
	 * 登录后回掉
	 *
	 * @param info
	 * @param user
	 */
	public void loginBack(
			final Info info,
			final UserData user,
			final UserHead userHead,
			final LoginConfig loginConfig) {
		boolean isLogin = info.isSuccess();
		LoginView loginView = this.appContext.getSingleView(LoginView.class);
		if (isLogin) {

			// PersonalBox pb = appContext.getBox(PersonalBox.class);
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			// pb.setUserHead(userHead);
			pb.setUserData(user);
			ub.putUserData(user);

			loginView.setVisible(false);
			MainView mainView = this.appContext.getSingleView(MainView.class);
			mainView.setVisible(true);
			mainView.setPersonalData(user);
			TrayView trayView = this.appContext.getSingleView(TrayView.class);
			trayView.showAllMenu(true);

			ExecuteTask et = new ExecuteTask() {

				@Override
				public void execute() {

					SystemManager sm = appContext.getManager(SystemManager.class);
					sm.initApp(user);
					PersonalBox pb = appContext.getBox(PersonalBox.class);

					UserDataBox ub = appContext.getBox(UserDataBox.class);
					ub.putUserData(user);

					LoginData loginData = pb.getLoginData();
					UserSender us = appContext.getSender(UserSender.class);

					us.sendUpdateStatus(loginData.getStatus());
					saveUserData(user, userHead, loginConfig);
				}
			};
			appContext.add(et);

		} else {
			NetModule nm = appContext.getModule(NetModule.class);
			nm.getConnectThread().setAutoCloseConnectTime(1);
			if (null != info.getWarnings() && !info.getWarnings().isEmpty()) {
				ErrorInfo e = info.getWarnings().get(0);
				loginView.showPrompt(e.getText());
			} else if (null != info.getErrors() && !info.getErrors().isEmpty()) {
				ErrorInfo e = info.getErrors().get(0);
				loginView.showPrompt(e.getText());
			} else {
				loginView.showPrompt("登录失败");
			}
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			LoginData loginData = pb.getLoginData();

			StringBuilder lock = new StringBuilder();
			lock.append(AppConstant.userHome);
			lock.append("/");
			lock.append(AppConstant.app_home_path);
			lock.append("/lock/");
			lock.append(loginData.getAccount());
			lock.append(".Lock");
			String accountLock = lock.toString();
			FileLockUtil.releaseLock(accountLock);
		}
	}

	private void saveUserData(UserData userData, UserHead userHead, final LoginConfig loginConfig) {
		UserSaveDataBox usdb = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);
		int size = usdb.getSize();// 获取登录保存的用户账号数量
		if (size > 20) {// 默认只保存20个用户的数量，多于20个，则删除多余的。
			usdb.remove((size - 20));
		}

		PersonalBox pb = appContext.getBox(PersonalBox.class);
		LoginData loginData = pb.getLoginData();

		UserSaveData usd = new UserSaveData();
		usd.setAccount(userData.getAccount());
		usd.setStatus(loginData.getStatus());
		if (null != userHead && UserHead.type_system.equals(userHead.getType())) {
			usd.setHead(userHead.getHeadId());
		} else {
			usd.setHead(userData.getHead());
		}
		usd.setLoginConfig(loginConfig);
		boolean rememberPassword = loginConfig.isRememberPassword();
		boolean autoLogin = loginConfig.isAutoLogin();
		if (rememberPassword || autoLogin) {
			usd.setPassword(loginData.getPassword());
		} else {
			usd.setPassword("");
		}
		usdb.put(userData.getAccount(), usd);
		ConfigManage.addOrUpdate(UserSaveDataBox.path, usdb);
	}

	public void handleDifferentLogin() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.getConnectThread().setAutoConnect(false);// 另外登录了账号后，设置为不自动连接
		nm.getConnectThread().closeConnect();// 关闭连接
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.setVisible(true);
		mainView.shwoDifferentLogin();// 弹出被另外登录提示框
	}
}
