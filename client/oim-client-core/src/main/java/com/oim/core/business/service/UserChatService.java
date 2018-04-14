package com.oim.core.business.service;

import java.util.List;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.PromptManager;
import com.oim.core.business.manager.UserChatManager;
import com.oim.core.business.manager.UserLastManager;
import com.oim.core.business.sender.UserChatSender;
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
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 16:00:52
 */
public class UserChatService extends AbstractService {

	public UserChatService(AppContext appContext) {
		super(appContext);
	}

	public void userChat(
			final String messageId,
			String sendUserId,
			String receiveUserId,
			final Content content) {

		final UserDataBox ub = appContext.getBox(UserDataBox.class);
		PersonalBox pb = appContext.getBox(PersonalBox.class);

		final UserData ownUser = pb.getUserData();
		final boolean isOwn = sendUserId.equals(ownUser.getId());

		final String showUserId = (isOwn) ? receiveUserId : sendUserId;
		final UserData ud = ub.getUserData(showUserId);

		final UserData showUserData = ud;
		final UserData chatUserData = (isOwn) ? ownUser : ud;

		if (null == showUserData) {
			DataBackAction dataBackAction = new DataBackActionAdapter() {

				@Back
				public void back(@Define("userData") UserData userData) {
					if (null != userData) {
						ub.putUserData(userData);
						UserData showUser = userData;
						UserData chatUser = (isOwn) ? ownUser : userData;
						showChatMessage(isOwn, messageId, showUser, chatUser, content);
					}
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(showUserId, dataBackAction);
		} else {
			showChatMessage(isOwn, messageId, showUserData, chatUserData, content);
		}
	}

	protected void showChatMessage(boolean isOwn, String messageId, final UserData showUserData, UserData chatUserData, Content content) {
		String userId = showUserData.getId();

		UserLastManager ulm = this.appContext.getManager(UserLastManager.class);
		UserChatManager cm = this.appContext.getManager(UserChatManager.class);
		
		String time = cm.getTimeText(userId, content.getTimestamp());
		String text = CoreContentUtil.getText(content);
		
		cm.putUserMessageId(userId, messageId);
		cm.userChat(isOwn, showUserData, chatUserData, content);
		cm.updateUserChatItemInfo(userId, text, time);
		ulm.addOrUpdateLastUser(showUserData);
		ulm.updateLastUserChatInfo(userId, text, time);

		boolean isUserChatShowing = cm.isUserChatShowing(userId);
		if ((!isUserChatShowing) && !isOwn) {
			cm.plusUnread(userId);

			CallAction callAction = new CallAction() {

				@Override
				public void execute() {
					showUserChat(showUserData);
				}
			};

			// TODO 消息头像提醒做的有点生硬，有机会再重构
			PromptManager pm = this.appContext.getManager(PromptManager.class);
			PromptData pd = new PromptData(IconType.userHead, showUserData.getHead(), callAction);
			pd.setKey(userId);

			pm.put(userId, pd);
			pm.showUserHeadPulse(userId, true);// 跳动用户头像
			pm.playSound(SoundHandler.sound_type_message);// 播放消息声音

			int count = cm.getUnreadCount(userId);
			ulm.setLastUserItemRed(userId, true, count);
			cm.setChatUserItemRed(userId, true, count);
		}
	}

	public void receiveShake(String sendUserId) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData ownUser = pb.getUserData();
		boolean isOwn = sendUserId.equals(ownUser.getId());
		if (isOwn) {
			return;
		}
		PromptManager pm = this.appContext.getManager(PromptManager.class);
		pm.playSound(SoundHandler.sound_type_shake);// 播放抖动声音
		UserChatManager cm = this.appContext.getManager(UserChatManager.class);
		cm.doShake(sendUserId);// 执行抖动效果
	}

	public void showUserChat(UserData userData) {
		UserChatManager cm = this.appContext.getManager(UserChatManager.class);
		cm.showUserChat(userData);

		String userId = userData.getId();
		removeUserPrompt(userId);
	}

	public void showUserHistory(String userId) {
		UserChatManager cm = this.appContext.getManager(UserChatManager.class);
		cm.showUserHistory(userId);
		removeUserPrompt(userId);
	}

	public void removeUserPrompt(String userId) {
		PromptManager pm = this.appContext.getManager(PromptManager.class);
		pm.remove(userId);// 系统托盘停止跳动
		pm.showUserHeadPulse(userId, false);// 停止头像跳动
		
		UserChatManager cm = this.appContext.getManager(UserChatManager.class);
		UserLastManager ulm = this.appContext.getManager(UserLastManager.class);
		int count = cm.getUnreadCount(userId);
		ulm.setLastUserItemRed(userId, false, count);
		cm.readUserChat(userId);
		pm.remove("offline," + userId);// 系统托盘停止跳动
	}

	public void getOfflineMessage() {
		UserChatSender cs = appContext.getSender(UserChatSender.class);
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
		final UserDataBox ub = appContext.getBox(UserDataBox.class);
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
		CallAction callAction = new CallAction() {
			@Override
			public void execute() {
				showUserChat(userData);
			}
		};
		PromptManager pm = this.appContext.getManager(PromptManager.class);
		PromptData pd = new PromptData(IconType.userHead, userData.getHead(), callAction);
		pd.setKey(userData.getId());

		pm.put("offline," + userData.getId(), pd);
		pm.playSound(SoundHandler.sound_type_message);// 播放消息声音
	}
}