package com.oim.fx.v1.view;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.view.SettingView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.LoginConfig;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.oim.ui.fx.classics.CommonStage;
import com.oim.ui.fx.classics.setting.LoginSettingPane;
import com.oim.ui.fx.classics.setting.SettingPane;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.LoginData;

import javafx.application.Platform;

/**
 * @author XiaHui
 * @date 2017-11-24 21:46:45
 */
public class SettingViewImpl extends AbstractView implements SettingView {
	CommonStage frame;
	SettingPane settingPane;
	LoginSettingPane loginSettingPane;

	public SettingViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new CommonStage();
				frame.setResizable(false);
				frame.setTitlePaneStyle(2);
				frame.setWidth(560);
				frame.setHeight(420);
				frame.setTitle("系统设置");
				settingPane = new SettingPane();
				loginSettingPane = new LoginSettingPane();

				settingPane.addTab("登录设置", loginSettingPane);

				frame.setCenter(settingPane);
				initEvent();
			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					if (!frame.isShowing()) {
						initConfig();
					}
					frame.show();
					frame.toFront();
				} else {
					frame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return (null != frame) && frame.isShowing();
	}

	private void initEvent() {
		loginSettingPane.setOnAutoLoginAction(a -> {
			boolean autoLogin = loginSettingPane.isAutoLogin();
			PersonalBox pb = appContext.getBox(PersonalBox.class);
			LoginData loginData = pb.getLoginData();
			String account = loginData.getAccount();
			UserSaveDataBox box = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);
			UserSaveData ud = box.get(account);
			if (null == ud) {
				ud = new UserSaveData();
			}
			LoginConfig lc = ud.getLoginConfig();
			if (lc == null) {
				lc = new LoginConfig();
				ud.setLoginConfig(lc);
			}
			lc.setAutoLogin(autoLogin);
			box.put(account, ud);
			ConfigManage.addOrUpdate(UserSaveDataBox.path, box);
		});
	}

	protected void initConfig() {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		LoginData loginData = pb.getLoginData();
		String account = loginData.getAccount();
		UserSaveDataBox box = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);
		UserSaveData ud = box.get(account);
		boolean autoLogin = false;
		if (null != ud) {
			LoginConfig lc = ud.getLoginConfig();
			if (lc != null) {
				autoLogin = lc.isAutoLogin();
			}
		}
		loginSettingPane.setAutoLogin(autoLogin);
	}
}
