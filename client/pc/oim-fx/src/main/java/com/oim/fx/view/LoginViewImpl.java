package com.oim.fx.view;

//import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.oim.core.business.controller.PersonalController;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.view.LoginView;
import com.oim.core.business.view.NetSettingView;
import com.oim.core.business.view.RegisterView;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.data.LoginConfig;
import com.oim.core.common.data.UserSaveData;
import com.oim.core.common.data.UserSaveDataBox;
import com.oim.fx.common.box.ImageBox;
import com.oim.fx.ui.LoginFrame;
import com.only.common.util.OnlyMD5Util;
import com.only.fx.common.action.ExecuteAction;
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
import javafx.util.StringConverter;

/**
 * @author: XiaHui
 * @date: 2016年10月10日 上午11:04:46
 */
public class LoginViewImpl extends AbstractView implements LoginView {

	protected LoginFrame loginFrame;// = new LoginFrame();

	public LoginViewImpl(AppContext appContext) {
		super(appContext);
		set(createFrame());
		initComponent();
		initEvent();
	}

	void set(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	private void initComponent() {
		// File video=new File("Resources/Video/Login/login.mp4");
		// loginFrame.setVideo(video);
		Image image = ImageBox.getImagePath("Resources/Images/Head/User/1_100.gif");
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
		loginFrame.setSettingAction(new EventHandler<ActionEvent>() {

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

		loginFrame.setLoginAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				login();
			}
		});

		loginFrame.setForgetLOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ForgetPasswordView view = appContext.getSingleView(ForgetPasswordView.class);
				view.setVisible(true);
			}
		});

		UserSaveDataBox usdb = (UserSaveDataBox) ConfigManage.get(UserSaveDataBox.path, UserSaveDataBox.class);

		List<Object> list = new ArrayList<Object>(usdb.getList());

		StringConverter<Object> stringConverter = new StringConverter<Object>() {
			@Override
			public String toString(Object t) {
				String value = null;
				if (t instanceof UserSaveData) {
					value = ((UserSaveData) t).getAccount();
				}
				return value;
			}

			@Override
			public Object fromString(String text) {
				UserSaveData ud = usdb.get(text);
				return ud;
			}
		};

		loginFrame.setList(list, stringConverter);

		loginFrame.setRemoveAction(new ExecuteAction() {
			public <T, E> E execute(T value) {
				if (value instanceof UserSaveData) {
					UserSaveData ud = ((UserSaveData) value);
					usdb.remove(ud.getAccount());
					ConfigManage.addOrUpdate(UserSaveDataBox.path, usdb);
					loginFrame.removeAccount(ud);
				}
				return null;
			}
		});

		loginFrame.setAccountAction(new ExecuteAction() {
			public <T, E> E execute(T value) {
				if (value instanceof UserSaveData) {
					UserSaveData ud = ((UserSaveData) value);
					// Image image =
					// ImageBox.getImagePath("Resources/Images/Head/User/" +
					// ud.getHead() + "_100.gif", 80, 80);
					// loginFrame.setHeadImage(image);
					String account = (null == ud.getAccount()) ? "" : ud.getAccount();
					// String password=(null==ud.getp)?"":ud.getAccount();
					loginFrame.setAccount(account);
					// loginFrame.setPassword(value);
				}
				return null;
			}
		});

		loginFrame.addAccountChangeListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (null != newValue) {
					UserSaveData ud = usdb.get(newValue);
					//HeadImageManager him = appContext.getManager(HeadImageManager.class);
					if (null != ud) {
						//Image image = him.getUserHeadImage(ud.getUserHead(), 80);
						//loginFrame.setHeadImage(image);
					} else {
						//Image image = him.getDefaultUserHeadImage(80);
						// Image image =
						// ImageBox.getImagePath("Resources/Images/Head/User/1_100.gif",
						// 80, 80);
						//loginFrame.setHeadImage(image);
					}
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

		LoginData loginData = new LoginData();
		loginData.setStatus(status);
		loginData.setAccount(account);
		loginData.setPassword(OnlyMD5Util.md5L32(password));
		LoginConfig loginConfig = new LoginConfig();

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

	public LoginFrame createFrame() {
		return new LoginFrame();
	}

	@Override
	public void setUserSaveDataList(List<UserSaveData> list) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void setCurrentUserSaveData(UserSaveData userSaveData) {
		// TODO 自动生成的方法存根

	}
}
