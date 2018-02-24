package com.oim.fx.v1.view;

import java.util.ArrayList;
import java.util.List;

import com.oim.core.business.controller.PersonalController;
import com.oim.core.business.manager.HeadImageManager;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.view.LoginView;
import com.oim.core.business.view.NetSettingView;
import com.oim.core.business.view.RegisterView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.LoginConfig;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.view.ForgetPasswordView;
import com.oim.ui.fx.classics.LoginStage;
import com.only.common.util.OnlyMD5Util;
import com.only.common.util.OnlyStringUtil;
import com.only.fx.common.action.ValueAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.LoginData;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author XiaHui
 * @date 2017-12-07 17:36:52
 */
public class LoginViewImpl extends AbstractView implements LoginView {

	protected LoginStage loginFrame;// = new LoginFrame();

	public LoginViewImpl(AppContext appContext) {
		super(appContext);
		set(createFrame());
		initComponent();
		initEvent();
	}

	void set(LoginStage loginFrame) {
		this.loginFrame = loginFrame;
	}

	private void initComponent() {
		Image image = ImageBox.getImageClassPath("/oim/common/images/head/user/1_100.gif");
		loginFrame.setHeadImage(image);
	}

	private void initEvent() {
		loginFrame.getOnlyTitlePane().addOnCloseAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				SystemModule sm = appContext.getModule(SystemModule.class);
				sm.exit();
			}
		});
		loginFrame.setOnSettingAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				NetSettingView netSettingView = appContext.getSingleView(NetSettingView.class);
				netSettingView.setVisible(true);
			}
		});

		loginFrame.setRegisterOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				RegisterView registerView = appContext.getSingleView(RegisterView.class);
				registerView.setVisible(true);
			}
		});

		loginFrame.setOnLoginAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				login();
			}
		});

		loginFrame.setForgetOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ForgetPasswordView view = appContext.getSingleView(ForgetPasswordView.class);
				view.setVisible(true);
			}
		});

		UserSaveDataBox usdb = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);

		// List<String> list = new ArrayList<String>();
		//
		// for (UserSaveData us : usdb.getList()) {
		// list.add(us.getAccount());
		// }
		//
		// loginFrame.setAccountList(list);
		loginFrame.setOnAccountRemove(new ValueAction<String>() {

			@Override
			public void value(String value) {
				usdb.remove(value);
				ConfigManage.addOrUpdate(UserSaveDataBox.path, usdb);
				loginFrame.removeAccount(value);
			}
		});

		loginFrame.addAccountChangeListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (null != newValue) {
					UserSaveData ud = usdb.get(newValue);
					setCurrentUserSaveData(ud);
				}
			}
		});
	}

	protected void login() {

		String account = loginFrame.getAccount();
		String password = loginFrame.getPassword();
		String status = loginFrame.getStatus();

		if (!loginFrame.verify()) {
			return;
		}
		boolean autoLogin = loginFrame.isAutoLogin();
		boolean rememberPassword = loginFrame.isRememberPassword();

		LoginConfig loginConfig = new LoginConfig();
		loginConfig.setAutoLogin(autoLogin);
		loginConfig.setRememberPassword(rememberPassword);

		LoginData loginData = new LoginData();
		loginData.setStatus(status);
		loginData.setAccount(account);

		if ("!@#$%^&*()".equals(password)) {
			UserSaveDataBox box = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);
			UserSaveData ud = box.get(account);
			if (null != ud) {
				String p = ud.getPassword();
				if (OnlyStringUtil.isNotBlank(p)) {
					loginData.setPassword(p);
				} else {
					loginData.setPassword(OnlyMD5Util.md5L32(password));
				}
			} else {
				loginData.setPassword(OnlyMD5Util.md5L32(password));
			}
		} else {
			loginData.setPassword(OnlyMD5Util.md5L32(password));
		}
		PersonalController pc = appContext.getController(PersonalController.class);
		pc.login(loginData, loginConfig);
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					loginFrame.show();
				} else {
					loginFrame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return loginFrame.isShowing();
	}

	@Override
	public boolean isSavePassword() {
		return false;
	}

	@Override
	public void showWaiting(boolean show) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loginFrame.showWaiting(show);
			}
		});
	}

	@Override
	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loginFrame.showPrompt(text);
			}
		});
	}

	public LoginStage createFrame() {
		LoginStage stage = new LoginStage();
		stage.setResizable(false);
		stage.setTitlePaneStyle(2);
		stage.setRadius(5);
		return stage;
	}

	@Override
	public void setUserSaveDataList(List<UserSaveData> list) {
		List<String> accountList = new ArrayList<String>();
		for (UserSaveData us : list) {
			accountList.add(us.getAccount());
		}
		loginFrame.setAccountList(accountList);
	}

	@Override
	public void setCurrentUserSaveData(UserSaveData userSaveData) {
		HeadImageManager him = appContext.getManager(HeadImageManager.class);
		if (null != userSaveData) {

			boolean autoLogin = false;
			boolean rememberPassword = false;
			LoginConfig loginConfig = userSaveData.getLoginConfig();
			if (null != loginConfig) {
				autoLogin = loginConfig.isAutoLogin();
				rememberPassword = loginConfig.isRememberPassword();
			}
			String account = userSaveData.getAccount();
			String password = userSaveData.getPassword();
			String status = userSaveData.getStatus();
			if (rememberPassword) {
				password = "!@#$%^&*()";
			} else {
				password = "";
			}
			String head = userSaveData.getHead();
			String headPath = userSaveData.getHeadPath();

			Image image = null;
			if (OnlyStringUtil.isNotBlank(headPath)) {
				image = him.getUserHeadImageByPath(headPath);
			} else if (OnlyStringUtil.isNotBlank(head)) {
				image = him.getUserHeadImageByKey(head, 80);
			} else {
				image = him.getUserHeadImageByKey("1", 80);
			}
			loginFrame.setHeadImage(image);
			loginFrame.setPassword(password);
			loginFrame.setAccount(account);
			loginFrame.setStatus(status);

			loginFrame.setAutoLogin(autoLogin);
			loginFrame.setRememberPassword(rememberPassword);
		} else {
			Image image = him.getUserHeadImageByKey("1", 80);
			loginFrame.setHeadImage(image);
		}
	}
}
