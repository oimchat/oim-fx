package com.oim.core.business.action;

import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.RemoteManage;
import com.oim.core.business.sender.UserSender;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.bean.UserData;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@ActionMapping(value = "1.504")
public class RemoteAction extends AbstractAction {

	public RemoteAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.2.0002")
	public void receivedServerBack() {

	}

	@MethodMapping(value = "1.2.0004")
	public void remoteControlRequest(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {

		final RemoteManage rm = this.appContext.getManager(RemoteManage.class);

		final UserDataBox ub=appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(sendUserId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null == userData) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了

			DataBackAction dataBackAction = new DataBackActionAdapter() {
				@Back
				public void back(@Define("userData") UserData userData) {
					ub.putUserData(userData);
					rm.getRequestRemoteControl(userData);
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(sendUserId, dataBackAction);
		} else {
			rm.getRequestRemoteControl(userData);
		}
	}

	@MethodMapping(value = "1.2.0005")
	public void remoteControlResponse(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType,
			@Define("code") String code,
			@Define("message") String message) {
		RemoteManage rm = this.appContext.getManager(RemoteManage.class);
		rm.getResponseRemoteControl(sendUserId, actionType, code, message);
	}

	@MethodMapping(value = "1.2.0006")
	public void remoteAssistRequest(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
	}

	@MethodMapping(value = "1.2.0007")
	public void remoteAssistResponse(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType,
			@Define("code") String code,
			@Define("message") String message) {
	}

	@MethodMapping(value = "1.2.0008")
	public void releaseRemoteControl(
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		RemoteManage rm = this.appContext.getManager(RemoteManage.class);
		rm.getReleaseRemoteControl(sendUserId);
	}
}
