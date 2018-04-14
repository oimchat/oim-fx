package com.oim.core.business.manager;

import java.util.HashSet;
import java.util.Set;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.ServerAddressBox;
import com.oim.core.business.constant.ServerAddressConstant;
import com.oim.core.business.module.NetModule;
import com.oim.core.business.module.SystemModule;
import com.oim.core.business.sender.ChatSender;
import com.oim.core.business.sender.GroupSender;
import com.oim.core.business.sender.RemoteSender;
import com.oim.core.business.sender.SettingSender;
import com.oim.core.business.sender.UserSender;
import com.oim.core.business.sender.VideoSender;
import com.oim.core.common.action.CallAction;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.connect.ConnectData;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.AddressData;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

/**
 * 程序信息相关的管理
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午1:37:57
 */
public class SystemManager extends AbstractManager {

	Set<CallAction> set=new HashSet<CallAction>();
	
	public SystemManager(AppContext appContext) {
		super(appContext);
	}
	
	public void addInitAction(CallAction call) {
		set.add(call);
	}

	public void initApp(UserData user) {
		SystemModule sm = appContext.getModule(SystemModule.class);
		sm.setLogin(true);

		SettingSender ss = appContext.getSender(SettingSender.class);
		UserSender us = appContext.getSender(UserSender.class);
		GroupSender gs = appContext.getSender(GroupSender.class);
		
		
		ss.getSetting();//获取一些系统设置
		
		us.getUserHeadList(user.getId());//获取用户的头像
		us.getUserCategoryWithUserList();
		
		gs.getGroupHeadList(user.getId());
		gs.getGroupCategoryWithGroupList();
		gs.getUserInGroupMemberList();
		
		ChatSender cs = appContext.getSender(ChatSender.class);
		cs.getLastChatWithDataList(20);

		PersonalBox pb=appContext.getBox(PersonalBox.class);
		pb.setUserData(user);
		
		initVideoService();
		initRemoteService(user.getId());
		initBeatMessage();
		
		if(!set.isEmpty()) {
			for(CallAction call:set) {
				call.execute();
			}
		}
	}
	
	/**
	 * 设置发送心跳包(当tcp太久没发送消息的时候，可能已经断开连接了，这个用来保持连接)(不熟mina的心跳机制，就这样了，懒得去研究了)
	 */
	private void initBeatMessage() {
		Message beatData = new Message();
		Head head = new Head();
		head.setAction("1.000");
		head.setMethod("1.1.0001");
		beatData.setHead(head);
		NetModule nm = appContext.getModule(NetModule.class);
		nm.getDataWriteThread().setIntervalBeatTime(1000 * 60 * 5);
		nm.getDataWriteThread().setBeatData("heartbeat", beatData);
		nm.getDataWriteThread().setSendBeatData(true);
	}

	private void initVideoService() {
		DataBackAction dataBackAction = new DataBackActionAdapter() {
			@Back
			public void back(@Define("videoAddress") AddressData videoAddress) {
				
				ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
				ServerAddressConfig sac = sab.getAddress(ServerAddressConstant.server_main_tcp);
				
				videoAddress.setAddress(sac.getAddress());
				setVideoAddress(videoAddress);
			}
		};
		VideoSender vh = this.appContext.getSender(VideoSender.class);
		vh.getVideoServerPort(dataBackAction);
	}

	private void initRemoteService(final  String userId) {
		DataBackAction dataBackAction = new DataBackAction() {

			@Override
			public void lost() {
			}

			@Override
			public void timeOut() {
			}

			@Back
			public void back(Info info, @Define("remoteAddress") AddressData videoAddress) {
				if (info.isSuccess()) {
					RemoteManager rm = appContext.getManager(RemoteManager.class);
					ConnectData cd = new ConnectData();
					cd.setAddress(videoAddress.getAddress());
					cd.setPort(videoAddress.getPort());
					rm.getRemoteModule().setConnectData(cd);
					rm.getRemoteModule().setUserId(userId);
				}
			}
		};
		RemoteSender rs = this.appContext.getSender(RemoteSender.class);
		rs.getRemoteServerPort(dataBackAction);
	}

	private void setVideoAddress(AddressData videoAddress) {
		VideoManager vm = this.appContext.getManager(VideoManager.class);
		vm.setVideoServerAddress(videoAddress);
	}
}
