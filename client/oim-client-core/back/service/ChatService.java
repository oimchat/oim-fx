package com.oim.core.business.service;

import java.util.List;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.ChatManage;
import com.oim.core.business.manager.LastManage;
import com.oim.core.business.manager.PromptManage;
import com.oim.core.business.sender.ChatSender;
import com.oim.core.business.sender.UserSender;
import com.oim.core.common.action.CallAction;
import com.oim.core.common.app.dto.PromptData;
import com.oim.core.common.app.dto.PromptData.IconType;
import com.oim.core.common.sound.SoundHandler;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年3月31日 上午11:45:15 version 0.0.1
 */
public class ChatService extends AbstractService {

	public ChatService(AppContext appContext) {
		super(appContext);
	}

	public void receiveUserChatMessage(String messageId,String sendUserId, String receiveUserId,final Content content) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		final UserData ownUser = pb.getUserData();
		final boolean isOwn = sendUserId.equals(ownUser.getId());

		if (isOwn) {
			return;
		}
		ChatManage chatManage = this.appContext.getManager(ChatManage.class);
		chatManage.putUserMessageId(sendUserId, messageId);
		final UserDataBox ub=appContext.getBox(UserDataBox.class);
		final UserData userData = ub.getUserData(sendUserId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null == userData) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
			DataBackAction dataBackAction = new DataBackActionAdapter() {

				@Back
				public void back(@Define("userData") UserData userData) {
					ub.putUserData(userData);
					showChatData(isOwn, userData, content);
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(sendUserId, dataBackAction);
		} else {
			showChatData(isOwn, userData, content);
		}
	}

	public void receiveShake(String sendUserId) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData ownUser = pb.getUserData();
		boolean isOwn = sendUserId.equals(ownUser.getId());
		if (isOwn) {
			return;
		}
		PromptManage pm = this.appContext.getManager(PromptManage.class);
		pm.playSound(SoundHandler.sound_type_shake);// 播放抖动声音
		ChatManage chatManage = appContext.getManager(ChatManage.class);
		chatManage.doShake(sendUserId);// 执行抖动效果
	}

	public void receiveGroupChatMessage(String userId, String groupId,final Content content) {
		GroupBox gb=appContext.getBox(GroupBox.class);
		final Group group=gb.getGroup(groupId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null != group) {
			final UserDataBox ub=appContext.getBox(UserDataBox.class);
			UserData userData = ub.getUserData(userId);
			if (null == userData) {
				DataBackAction dataBackAction = new DataBackActionAdapter() {
					@Back
					public void back(@Define("userData") UserData userData) {
						ub.putUserData(userData);
						showChatData(group, userData, content);
					}
				};
				UserSender uh = this.appContext.getSender(UserSender.class);
				uh.getUserDataById(userId, dataBackAction);
			} else {
				showChatData(group, userData, content);
			}
		}
	}

	/**
	 * 显示聊天信息
	 * 
	 * @param userData
	 * @param chatDataList
	 */
	private void showChatData(boolean isOwn, final UserData userData, Content content) {
		ChatManage chatManage = this.appContext.getManager(ChatManage.class);
		if (!chatManage.isUserChatShowing(userData.getId())) {// 如果正显示的聊天窗口并不是发送信息的用户，那么就要跳动其头像、以及闪动系统托盘
			CallAction callAction = new CallAction() {
				@Override
				public void execute() {
					ChatManage chatManage = appContext.getManager(ChatManage.class);
					chatManage.showCahtFrame(userData);
				}
			};
			chatManage.putUserCaht(userData.getId(), content);
			//TODO 消息头像提醒做的有点生硬，有机会再重构
			PromptManage pm = this.appContext.getManager(PromptManage.class);
			PromptData pd=new PromptData(IconType.userHead, userData.getHead(), callAction);
			pd.setKey(userData.getId());
			
			pm.put(userData.getId(), pd);
			
			pm.showUserHeadPulse(userData.getId(), true);// 跳动用户头像
			pm.playSound(SoundHandler.sound_type_message);// 播放消息声音
		} else {
			chatManage.userChat(isOwn, userData, content);
		}
		LastManage lastManage = this.appContext.getManager(LastManage.class);
		String text = CoreContentUtil.getText(content);
		lastManage.addOrUpdateLastUserData(userData, text);
	}

	private void showChatData(final Group group, UserData userData, Content content) {
		ChatManage chatManage = this.appContext.getManager(ChatManage.class);
		if (!chatManage.isGroupChatShowing(group.getId())) {

			CallAction callAction = new CallAction() {

				@Override
				public void execute() {
					ChatManage chatManage = appContext.getManager(ChatManage.class);
					chatManage.showCahtFrame(group);
				}
			};
			chatManage.putGroupCaht(group.getId(), userData, content);
			PromptManage pm = this.appContext.getManager(PromptManage.class);
			pm.put(group.getId(), IconType.groupHead, group.getHead(), callAction);
			pm.showGroupHeadPulse(group.getId(), true);
			pm.playSound(SoundHandler.sound_type_message);
		} else {
			chatManage.groupChat(group, userData, content);
		}
		String text = CoreContentUtil.getText(content);
		LastManage lastManage = this.appContext.getManager(LastManage.class);
		lastManage.addOrUpdateLastGroup(group, text);
	}

	public void getOfflineMessage() {
		ChatSender cs = appContext.getSender(ChatSender.class);
		DataBackAction dba = new DataBackActionAdapter() {
			@Back
			public void back(Info info, @Define("userIds") List<String> userIds) {
				if (null != userIds) {
					for (String userId : userIds) {
						setOffline(userId);
					}
				}
			}
		};
		cs.getOfflineUserIdList(dba);
	}

	private void setOffline(String userId) {
		final UserDataBox ub=appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(userId);// 先从好友集合里面获取用户信息，如果用户不是好友，那么从服务器下载用户信息
		if (null == userData) {// 为null说明发送信息的不在好友列表，那么就要从服务器获取发送信息用户的信息了
			DataBackAction dataBackAction = new DataBackActionAdapter() {

				@Back
				public void back(@Define("userData") UserData userData) {
					ub.putUserData(userData);
					setOffline(userData);
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(userId, dataBackAction);
		} else {
			setOffline(userData);
		}
	}

	private void setOffline(final UserData userData) {
//		ChatSender cs = appContext.getSender(ChatSender.class);
		CallAction callAction = new CallAction() {
			@Override
			public void execute() {
				ChatManage chatManage = appContext.getManager(ChatManage.class);
				chatManage.showCahtFrame(userData);
//				showChatHistory(userData);
//				cs.updateUserOfflineMessageToRead(userData.getId());
			}
		};
		PromptManage pm = this.appContext.getManager(PromptManage.class);
		PromptData pd=new PromptData(IconType.userHead, userData.getHead(), callAction);
		pd.setKey(userData.getId());
		
		pm.put("offline," + userData.getId(), pd);
		
		pm.playSound(SoundHandler.sound_type_message);// 播放消息声音
	}

//	private void showChatHistory(UserData userData) {
//		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {
//
//			@Override
//			public void lost() {
//			}
//
//			@Override
//			public void timeOut() {
//			}
//
//			@Back
//			public void back(Info info, @Define("sendUserId") String sendUserId, @Define("receiveUserId") String receiveUserId, @Define("contents") List<UserChatHistoryData> contents, @Define("page") PageImpl page) {
//				if (info.isSuccess()) {
//					ChatListView view = appContext.getSingleView(ChatListView.class);
//					view.userChatHistory(userData, contents);
//				}
//			}
//		};
//		UserData sendUser = PersonalDataBox.get(UserData.class);
//		PageImpl page = new PageImpl();
//		page.setPageSize(50);
//		page.setPageNumber(1);
//		ChatSender ds = appContext.getSender(ChatSender.class);
//		ds.queryUserChatLog(sendUser.getId(), userData.getId(), new ChatQuery(), page, dataBackAction);
//		ChatManage chatManage = appContext.getManage(ChatManage.class);
//		chatManage.showCahtFrame(userData);
//	}

	public void setLastList(String userId, List<UserChatHistoryData> userLastList, List<GroupChatHistoryData> groupLastList) {
		UserDataBox ub=appContext.getBox(UserDataBox.class);
		
		if (null != userLastList) {
			LastManage lastManage = this.appContext.getManager(LastManage.class);
			PersonalBox pb=appContext.getBox(PersonalBox.class);
			UserData u = pb.getUserData();
			String id = u.getId();
			for (UserChatHistoryData uc : userLastList) {
				Content content = uc.getContent();
				UserData sd = uc.getSendUserData();
				UserData rd = uc.getReceiveUserData();

				UserData ud = (id.equals(sd.getId())) ? rd : sd;
				String uid = ud.getId();
				UserData userData = ub.getUserData(uid);
				if (null != userData) {
					ud = userData;
				} 
				String text = CoreContentUtil.getText(content);
				lastManage.addOrUpdateLastUserData(ud, text);
			}
		}
	}
}
