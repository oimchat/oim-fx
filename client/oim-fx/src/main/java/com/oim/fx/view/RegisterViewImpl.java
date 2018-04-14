package com.oim.fx.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.core.business.box.ServerAddressBox;
import com.oim.core.business.constant.ServerAddressConstant;
import com.oim.core.business.manager.ServerAddressManager;
import com.oim.core.business.view.RegisterView;
import com.oim.core.net.http.HttpHandler;
import com.oim.core.net.http.Request;
import com.oim.ui.fx.classics.ShowAccountDialog;
import com.oim.ui.fx.classics.UserRegisterFrame;
import com.only.common.result.Info;
import com.only.common.result.util.MessageUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.bean.SecurityQuestion;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

import javafx.application.Platform;

public class RegisterViewImpl extends AbstractView implements RegisterView {

	UserRegisterFrame userRegisterFrame;// = new UserRegisterFrame();// 注册主界面
	ShowAccountDialog showAccountDialog;// = new
										// ShowAccountDialog(userRegisterFrame);//
										// 注册成功后提示信息

	public RegisterViewImpl(AppContext appContext) {
		super(appContext);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				userRegisterFrame = new UserRegisterFrame();// 注册主界面
				showAccountDialog = new ShowAccountDialog(userRegisterFrame);// 注册成功后提示信息
				init();
			}
		});
	}

	private void init() {
		userRegisterFrame.setOnDoneAction(a -> {
			register();
		});
	}

	@Override
	public void setVisible(boolean visible) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (visible) {
					userRegisterFrame.show();
					userRegisterFrame.toFront();
					// userRegisterFrame.clearData();
				} else {
					userRegisterFrame.hide();
				}
			}
		});
	}

	@Override
	public boolean isShowing() {
		return false;
	}

	private void register() {
		boolean v = userRegisterFrame.verify();
		if (!v) {
			return;
		}
		String nickname = userRegisterFrame.getNickname();
		String password = userRegisterFrame.getPassword();
		String gender = userRegisterFrame.getGender();
		String blood = userRegisterFrame.getBloodType();
		Date birthdate = userRegisterFrame.getBirthday();
		String homeAddress = userRegisterFrame.getHomeAddress();
		String locationAddress = userRegisterFrame.getLocationAddress();
		String mobile = userRegisterFrame.getMobile();
		String email = userRegisterFrame.getEmail();
		String signature = userRegisterFrame.getSignature();
		String introduce = userRegisterFrame.getIntroduce();

		Request message = new Request();
		message.setController("user");
		message.setMethod("/register.do");

		// UserData user = new UserData();
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("password", OnlyMD5Util.md5L32(password));
		user.put("nickname", nickname);
		user.put("gender", gender);
		user.put("blood", blood);
		user.put("birthdate", birthdate);
		user.put("homeAddress", homeAddress);
		user.put("locationAddress", locationAddress);
		user.put("mobile", mobile);
		user.put("email", email);
		user.put("signature", signature);
		user.put("introduce", introduce);

		String q = userRegisterFrame.getQuestion();
		String a = userRegisterFrame.getAnswer();

		if ((null != q && !"".equals(q)) && (null != a && !"".equals(a))) {

			List<SecurityQuestion> list = new ArrayList<SecurityQuestion>();

			SecurityQuestion sq = new SecurityQuestion();
			sq.setQuestion(q);
			sq.setAnswer(a);

			list.add(sq);
			message.put("securityQuestionList", list);
		} else {

			if (null != q && !"".equals(q)) {
				userRegisterFrame.showPrompt("填写了密保问题请填写答案！");
				return;
			}

			if (null != a && !"".equals(a)) {
				userRegisterFrame.showPrompt("填写了密保答案请填写问题！");
				return;
			}
		}

		message.put("userData", user);

		userRegisterFrame.showWaiting(true);
		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				userRegisterFrame.showWaiting(false);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						userRegisterFrame.showPrompt("注册失败，\n请检查网络连接是否正常。");
					}
				});
			}

			@Back
			public void back(Info info, @Define("userData") UserData user) {
				userRegisterFrame.showWaiting(false);
				handle(info, user);
			}
		};
		Runnable runnable = new Runnable() {
			Request request;
			DataBackAction dataBackAction;

			public Runnable execute(Request request, DataBackAction dataBackAction) {
				this.request = request;
				this.dataBackAction = dataBackAction;
				return this;
			}

			public void run() {
				ServerAddressManager sam = appContext.getManager(ServerAddressManager.class);
				Info backInfo = sam.loadServerAddress("");
				if (backInfo.isSuccess()) {
					ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
					ServerAddressConfig sac = sab.getAddress(ServerAddressConstant.server_main_http);
					String url = sac.getAddress();
					new HttpHandler().execute(url, request, dataBackAction);
				} else {
					String error = MessageUtil.getDefaultErrorText(backInfo);
					showPrompt(error);
				}
			}
		}.execute(message, dataBackAction);
		new Thread(runnable).start();
	}

	private void handle(Info info, UserData user) {
		if (info.isSuccess()) {
			String text = "注册成功，你的账号为：" + user.getAccount();

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					showAccountDialog.setText(text);
					showAccountDialog.show();
					userRegisterFrame.clearData();
				}
			});
		} else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					userRegisterFrame.showPrompt("注册失败!");
				}
			});
		}
	}

	public void showPrompt(String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				userRegisterFrame.showPrompt(text);
			}
		});
	}
}
