package com.oim.core.business.controller;

import com.oim.common.util.FileLockUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.ServerAddressBox;
import com.oim.core.business.constant.ServerAddressConstant;
import com.oim.core.business.manager.PersonalManager;
import com.oim.core.business.manager.ServerAddressManager;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.sender.PersonalSender;
import com.oim.core.business.service.HeadService;
import com.oim.core.business.service.PersonalService;
import com.oim.core.business.view.LoginView;
import com.oim.core.common.AppConstant;
import com.oim.core.common.data.LoginConfig;
import com.only.common.result.Info;
import com.only.common.result.util.MessageUtil;
import com.only.common.util.OnlyFileUtil;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.action.ConnectBackAction;
import com.only.net.connect.ConnectData;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.data.LoginData;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

public class PersonalController extends AbstractController {

	public PersonalController(AppContext appContext) {
		super(appContext);
	}

	public void login(final LoginData loginData, final LoginConfig loginConfig) {

		LoginView loginView = appContext.getSingleView(LoginView.class);
		boolean mark = true;
		StringBuilder sb = new StringBuilder();
		if (null == loginData.getAccount() || "".equals(loginData.getAccount())) {
			sb.append("账号不能为空!");
			sb.append("\n");
			mark = false;
		}
		if (null == loginData.getPassword() || "".equals(loginData.getPassword())) {
			sb.append("密码不能为空!");
			mark = false;
		}

		if (mark) {

			StringBuilder lock = new StringBuilder();
			lock.append(AppConstant.userHome);
			lock.append("/");
			lock.append(AppConstant.app_home_path);
			lock.append("/lock/");
			lock.append(loginData.getAccount());
			lock.append(".Lock");
			String accountLock = lock.toString();
			OnlyFileUtil.checkOrCreateFile(accountLock);
			if (FileLockUtil.isLock(accountLock)) {
				sb.append(loginData.getAccount());
				sb.append("已经登录，不能重复登录!");
				mark = false;
			}
		}

		if (!mark) {
			loginView.showPrompt(sb.toString());
		} else {
			loginView.showWaiting(true);
			new Thread() {
				@Override
				public void run() {

					SystemModule sm = appContext.getModule(SystemModule.class);
					while (!sm.isViewReady()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					loadServerAddress(loginData, loginConfig);
					// doLogin(loginData);
				}
			}.start();
		}
	}

	private void loadServerAddress(final LoginData loginData, final LoginConfig loginConfig) {
		ServerAddressManager sam = appContext.getManager(ServerAddressManager.class);
		Info backInfo = sam.loadServerAddress(loginData.getAccount());
		if (backInfo.isSuccess()) {
			doLogin(loginData, loginConfig);
		} else {
			String error = MessageUtil.getDefaultErrorText(backInfo);
			final LoginView loginView = appContext.getSingleView(LoginView.class);
			loginView.showWaiting(false);
			loginView.showPrompt(error);
		}
	}

	private void doLogin(final LoginData loginData, final LoginConfig loginConfig) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		pb.setLoginData(loginData);
		final LoginView loginView = appContext.getSingleView(LoginView.class);
		final DataBackActionAdapter action = new DataBackActionAdapter() {// 这是消息发送后回掉
			@Override
			public void lost() {
				loginView.showWaiting(false);
				loginView.showPrompt("登录失败，请检查网络是否正常。");
			}

			@Override
			public void timeOut() {
				loginView.showWaiting(false);
				loginView.showPrompt("登录超时，请检查网络是否正常。");
			}

			@Back
			public void back(
					Info info,
					@Define("userData") UserData user,
					@Define("userHead") UserHead userHead) {
				loginView.showWaiting(false);
				PersonalService ps = appContext.getService(PersonalService.class);

				ps.loginBack(info, user, userHead, loginConfig);
				if (info.isSuccess()) {
					HeadService hs = appContext.getService(HeadService.class);
					hs.setPersonalHead(userHead);
				}
			}
		};

		ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
		ServerAddressConfig sac = sab.getAddress(ServerAddressConstant.server_main_tcp);

		ConnectData connectData = new ConnectData();
		connectData.setAddress(sac.getAddress());
		connectData.setPort(sac.getPort());

		final PersonalSender ps = this.appContext.getSender(PersonalSender.class);
		ConnectBackAction cba = new ConnectBackAction() {

			@Override
			public void connectBack(boolean success) {
				ps.login(loginData, action);
			}
		};
		NetModule nm = appContext.getModule(NetModule.class);
		if (nm.getConnectThread().isConnected()) {
			ps.login(loginData, action);
		} else {
			// 因为负责连接服务器的和负责发送消息的线程不同，在执行登录之前是没有连接的，所以在这里先添加个连接后回掉的action
			// 当连接成功后再把登陆消息发出去，不然先把消息发了，再连接就没有执行登陆操作了
			nm.getConnectThread().addConnectBackAction(cba);
			nm.getConnectThread().setConnectData(connectData);
			nm.getConnectThread().setAutoConnect(true);
		}
	}

	/**
	 * 重连接，当断网后又恢复网络时
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void autoReconnect() {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		LoginData loginData = pb.getLoginData();
		PersonalSender ps = this.appContext.getSender(PersonalSender.class);
		ps.reconnect(loginData);
	}

	public void reconnect() {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		final LoginData loginData = pb.getLoginData();
		final PersonalSender ps = this.appContext.getSender(PersonalSender.class);

		ConnectBackAction cba = new ConnectBackAction() {

			@Override
			public void connectBack(boolean success) {
				ps.reconnect(loginData);
			}
		};
		NetModule nm = appContext.getModule(NetModule.class);
		if (nm.getConnectThread().isConnected()) {
			ps.reconnect(loginData);
		} else {
			// 因为负责连接服务器的和负责发送消息的线程不同，在执行登录之前是没有连接的，所以在这里先添加个连接后回掉的action
			// 当连接成功后再把登陆消息发出去，不然先把消息发了，再连接就没有执行登陆操作了
			nm.getConnectThread().addConnectBackAction(cba);
			nm.getConnectThread().setAutoConnect(true);
		}
	}

	public void logout() {
		PersonalManager pm = this.appContext.getManager(PersonalManager.class);
		pm.logout();
	}
}
