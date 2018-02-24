package com.oim.fx.view;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.sender.PersonalSender;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.UpdatePasswordView;
import com.oim.ui.fx.classics.UpdatePasswordFrame;
import com.only.common.result.Info;
import com.only.common.result.util.MessageUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.im.message.data.LoginData;

import javafx.application.Platform;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年2月10日 下午4:34:09
 * @version 0.0.1
 */
public class UpdatePasswordViewImpl extends AbstractView implements UpdatePasswordView{
	
	UpdatePasswordFrame frame;
	
	public UpdatePasswordViewImpl(AppContext appContext) {
		super(appContext);
		initUI();
	}

	private void initUI() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				frame = new UpdatePasswordFrame();
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
					frame.show();
					frame.toFront();
					frame.clearData();
				} else {
					frame.hide();
				}
			}
		});
	}

	private void initEvent() {
		frame.setOnDoneAction(a->{
			updatePassword();
		});
	}


	private void updatePassword() {
		String oldPassword = frame.getOldPassword();
		String newPassword = frame.getNewPassword();
		String verifyPassword = frame.getConfirmPassword();

		if (null == oldPassword || "".equals(oldPassword.trim())) {
			frame.showPrompt("请输入原来的密码！");
			return;
		}
		if (null == newPassword || "".equals(newPassword.trim())) {
			frame.showPrompt("请输新密码！");
			return;
		}
		if (null == verifyPassword || "".equals(verifyPassword.trim())) {
			frame.showPrompt("请再一次输入新密码！");
			return;
		}
		if (!newPassword.equals(verifyPassword.trim())) {
			frame.showPrompt("两次输入密码不一致！");
			return;
		}

		DataBackActionAdapter action = new DataBackActionAdapter() {// 这是消息发送后回掉
			@Override
			public void lost() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						frame.showPrompt("修改失败。");
					}
				});
			}

			@Override
			public void timeOut() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						frame.showPrompt("修改失败。");
					}
				});
			}

			@Back
			public void back(Info info) {
				if (info.isSuccess()) {
					setVisible(false);
					MainView mv = appContext.getSingleView(MainView.class);
					mv.showPrompt("修改成功。");
					PersonalBox pb=appContext.getBox(PersonalBox.class);
					LoginData loginData = pb.getLoginData();
					loginData.setPassword(newPassword);
					//PersonalBox.put(UserData.class, user);
				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							String text=MessageUtil.getDefaultErrorText(info);
							if(null==text){
								text="修改失败。";
							}
							frame.showPrompt(text);
						}
					});
				}
			}
		};
		PersonalSender ph = this.appContext.getSender(PersonalSender.class);
		ph.upadtePassword(OnlyMD5Util.md5L32(oldPassword),OnlyMD5Util.md5L32(newPassword), action);
	}

	@Override
	public boolean isShowing() {
		// TODO Auto-generated method stub
		return false;
	}
}
