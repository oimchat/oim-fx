package com.oim.core.business.controller;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.RemoteManager;
import com.oim.core.business.sender.RemoteSender;
import com.oim.core.business.sender.UserSender;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;
import com.onlyxiahui.im.bean.UserData;

public class RemoteController extends AbstractController {

	public RemoteController(AppContext appContext) {
		super(appContext);
	}

	public void requestRemoteControl(UserData userData) {

		RemoteManager rm = this.appContext.getManager(RemoteManager.class);
		rm.showRequestRemoteControlFrame(userData);
		
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();

		RemoteSender rs = this.appContext.getSender(RemoteSender.class);
		rs.requestRemoteControl(sendUser.getId(), userData.getId());
	}

	public void requestRemoteControl(String userId) {
		final UserDataBox ub=appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(userId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null == userData) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
			DataBackAction dataBackAction = new DataBackActionAdapter() {
				@Back
				public void back(@Define("userData") UserData userData) {
					ub.putUserData(userData);
					requestRemoteControl(userData);
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(userId, dataBackAction);
		} else {
			requestRemoteControl(userData);
		}
	}
}
