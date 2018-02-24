package com.oim.core.business.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.constant.RemoteConstant;
import com.oim.core.business.sender.RemoteSender;
import com.oim.core.business.view.ChatListView;
import com.oim.core.business.view.RemoteView;
import com.oim.core.common.action.CallBackAction;
import com.oim.core.common.component.RemoteModule;
import com.oim.core.common.component.remote.EventData;
import com.oim.core.common.component.remote.EventDataAction;
import com.oim.core.common.component.remote.ScreenHandler;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.connect.ConnectData;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.AddressData;

/**
 * 描述：远程协助管理
 * 
 * @author XiaHui
 * @date 2016年1月15日 下午7:52:44
 * @version 0.0.1
 */
public class RemoteManager extends AbstractManager {

	private RemoteModule remoteModule = new RemoteModule();// 远程协助模块
	private Map<String, RemoteView> rvMap = new ConcurrentHashMap<String, RemoteView>();// 被控制的

	public RemoteManager(AppContext appContext) {
		super(appContext);
		init();
	}

	public RemoteModule getRemoteModule() {
		return remoteModule;
	}

	private void init() {
		remoteModule.addHandler(new ScreenHandler() {

			@Override
			public void handle(String userId, byte[] bytes) {
				RemoteView rv = rvMap.get(userId);
				if (null != rv) {
					rv.setScreenBytes(bytes);
				}
			}
		});
	}

	/**
	 * 收到控制请求
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 下午1:16:02
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 下午1:16:02
	 */
	public void getRequestRemoteControl(final  UserData userData) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		final UserData sendUser =pb.getUserData();

		final ChatListView chatListView = appContext.getSingleView(ChatListView.class);
		boolean hasRequestRemoteControl = chatListView.hasRequestRemoteControl(userData.getId());
		boolean hasServerId = remoteModule.hasServerId();
		boolean hasConnectData = remoteModule.hasConnectData();
		final  RemoteSender rs = this.appContext.getSender(RemoteSender.class);
		if (hasServerId) {
			rs.responseRemoteControl(sendUser.getId(), userData.getId(), RemoteConstant.action_type_shut, "0001", "对方电脑正在被远程控制中");
		} else if (hasRequestRemoteControl) {
			rs.responseRemoteControl(sendUser.getId(), userData.getId(), RemoteConstant.action_type_shut, "0003", "有人正在请求远程控制对方电脑正");
		} else if (!hasConnectData) {
			DataBackAction dataBackAction = new DataBackAction() {

				@Override
				public void lost() {
					rs.responseRemoteControl(sendUser.getId(), userData.getId(), RemoteConstant.action_type_shut, "0002", "对方远程服务异常");
				}

				@Override
				public void timeOut() {
					rs.responseRemoteControl(sendUser.getId(), userData.getId(), RemoteConstant.action_type_shut, "0002", "对方远程服务异常");
				}

				@Back
				public void back(Info info, @Define("remoteAddress") AddressData videoAddress) {
					if (info.isSuccess()) {
						ConnectData cd = new ConnectData();
						cd.setAddress(videoAddress.getAddress());
						cd.setPort(videoAddress.getPort());
						remoteModule.setConnectData(cd);

						chatListView.showRequestRemoteControl(userData);
						// remoteModule.setServerId(userData.getId());
						// rs.responseRemoteControl(sendUser.getId(),
						// userData.getId(), RemoteConstant.action_type_agree,
						// "", "");
					} else {
						rs.responseRemoteControl(sendUser.getId(), userData.getId(), RemoteConstant.action_type_shut, "0002", "对方远程服务异常");
					}
				}
			};
			rs.getRemoteServerPort(dataBackAction);
		} else {
			chatListView.showRequestRemoteControl(userData);
			// remoteModule.setServerId(userData.getId());
			// rs.responseRemoteControl(sendUser.getId(), userData.getId(),
			// RemoteConstant.action_type_agree, "", "");
		}
	}

	public void responseRemoteControl(String userId, String actionType) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		String code = "";
		String message = "";
		RemoteSender rs = this.appContext.getSender(RemoteSender.class);
		if (RemoteConstant.action_type_agree.equals(actionType)) {
			remoteModule.setServerId(userId);
			rs.responseRemoteControl(sendUser.getId(), userId, RemoteConstant.action_type_agree, "", "");

		} else {
			code = "004";
			message = "对方拒绝了你的远程控制请求！";
			rs.responseRemoteControl(sendUser.getId(), userId, RemoteConstant.action_type_shut, code, message);
		}
	}

	/**
	 * 收到控制请求的回应
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 下午1:16:31
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 下午1:16:31
	 */
	public void getResponseRemoteControl(final String sendUserId, String actionType, String code, String message) {
		if (RemoteConstant.action_type_agree.equals(actionType)) {

			boolean hasConnectData = remoteModule.hasConnectData();
			if (!hasConnectData) {
				RemoteSender rs = this.appContext.getSender(RemoteSender.class);
				DataBackAction dataBackAction = new DataBackAction() {

					@Override
					public void lost() {
						RemoteView rv = rvMap.get(sendUserId);
						if (null != rv) {
							rv.showShutPrompt("协助服务异常！");
						}
					}

					@Override
					public void timeOut() {
						RemoteView rv = rvMap.get(sendUserId);
						if (null != rv) {
							rv.showShutPrompt("协助服务异常！");
						}
					}

					@Back
					public void back(Info info, @Define("remoteAddress") AddressData videoAddress) {
						if (info.isSuccess()) {
							ConnectData cd = new ConnectData();
							cd.setAddress(videoAddress.getAddress());
							cd.setPort(videoAddress.getPort());
							remoteModule.setConnectData(cd);
							remoteModule.addClientId(sendUserId);
						} else {
							RemoteView rv = rvMap.get(sendUserId);
							if (null != rv) {
								rv.showShutPrompt("协助服务异常！");
							}
						}
					}
				};
				rs.getRemoteServerPort(dataBackAction);
			} else {
				remoteModule.addClientId(sendUserId);
			}
		} else {
			RemoteView rv = rvMap.get(sendUserId);
			if (null != rv) {
				rv.showShutPrompt(message);
			}
		}
	}

	public void getRequestRemoteAssist(UserData userData) {

	}

	public void getResponseRemoteAssist(String sendUserId, String actionType, String code, String message) {

	}

	/**
	 * 释放控制
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 下午1:16:53
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 下午1:16:53
	 */
	public void getReleaseRemoteControl(String userId) {
		if (userId.equals(remoteModule.getServerId())) {
			remoteModule.setServerId(null);
		}
	}

	public void showRequestRemoteControlFrame(UserData userData) {
		RemoteView rv = getRemoteView(userData);
		rv.setUserData(userData);
		rv.setVisible(true);
	}

	private RemoteView getRemoteView(final  UserData userData) {
		RemoteView rv = rvMap.get(userData.getId());
		if (null == rv) {
			rv = appContext.getObject(RemoteView.class, true);//.getNewView(RemoteView.class);

			rv.addEventDataAction(new EventDataAction() {

				@Override
				public void action(String userId, EventData eventData) {
					// TODO Auto-generated method stub

				}
			});
			rv.addClose(new CallBackAction<String>() {

				@Override
				public void execute(String t) {
					PersonalBox pb=appContext.getBox(PersonalBox.class);
					UserData sendUser = pb.getUserData();
					RemoteSender rs = appContext.getSender(RemoteSender.class);
					rs.releaseRemoteControl(sendUser.getId(), userData.getId());
					remoteModule.removeClientId(userData.getId());
				}
			});
			rvMap.put(userData.getId(), rv);
		}
		return rv;
	}
}
