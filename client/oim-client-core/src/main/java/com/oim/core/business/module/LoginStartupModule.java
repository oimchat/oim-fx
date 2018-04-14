package com.oim.core.business.module;

import java.util.List;

import com.oim.core.business.controller.PersonalController;
import com.oim.core.business.view.LoginView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.LoginConfig;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * @author XiaHui
 * @date 2017年9月20日 下午5:49:06
 */
public class LoginStartupModule extends AbstractComponent {

	public LoginStartupModule(AppContext appContext) {
		super(appContext);

	}

	public void startupLoginConfig() {
		LoginView lw = appContext.getSingleView(LoginView.class);
		UserSaveDataBox box = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);
		List<UserSaveData> list = box.getList();
		lw.setUserSaveDataList(list);

		if (null != list) {
			boolean autoLogin = false;
			UserSaveData autoData = null;
			for (UserSaveData ud : list) {
				autoLogin = (null != ud.getLoginConfig()) ? ud.getLoginConfig().isAutoLogin() : false;
				if (autoLogin) {
					autoData = ud;
					break;
				}
			}

			if (autoLogin) {
				LoginConfig lc = autoData.getLoginConfig();
				String status = autoData.getStatus();
				LoginData loginData = new LoginData();
				loginData.setAccount(autoData.getAccount());
				loginData.setPassword(autoData.getPassword());
				loginData.setStatus((null == status || status.isEmpty()) ? "1" : status);
				PersonalController pc = appContext.getController(PersonalController.class);
				pc.login(loginData, lc);
			}
		}
	}
}
