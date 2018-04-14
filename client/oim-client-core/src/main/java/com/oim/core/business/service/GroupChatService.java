package com.oim.core.business.service;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.GroupChatManager;
import com.oim.core.business.manager.GroupLastManager;
import com.oim.core.business.manager.PromptManager;
import com.oim.core.business.sender.UserSender;
import com.oim.core.common.action.CallAction;
import com.oim.core.common.app.dto.PromptData;
import com.oim.core.common.app.dto.PromptData.IconType;
import com.oim.core.common.sound.SoundHandler;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 16:00:52
 */
public class GroupChatService extends AbstractService {

	public GroupChatService(AppContext appContext) {
		super(appContext);
	}

	public void groupChat(

			final String messageId,
			final String groupId,
			final String userId,
			final Content content) {

		PersonalBox pb = appContext.getBox(PersonalBox.class);
		final UserDataBox ub = appContext.getBox(UserDataBox.class);
		final GroupBox gb = appContext.getBox(GroupBox.class);

		final Group group = gb.getGroup(groupId);

		if (null == group) {
			return;
		}

		final UserData ownUser = pb.getUserData();
		final boolean isOwn = userId.equals(ownUser.getId());
		final UserData chatUserData = (isOwn) ? ownUser : ub.getUserData(userId);

		if (null == chatUserData) {
			DataBackAction dataBackAction = new DataBackActionAdapter() {

				@Back
				public void back(@Define("userData") UserData userData) {
					if (null != userData) {
						ub.putUserData(userData);
						showChatMessage(isOwn, messageId, group, userData, content);
					}
				}
			};
			UserSender uh = this.appContext.getSender(UserSender.class);
			uh.getUserDataById(userId, dataBackAction);
		} else {
			showChatMessage(isOwn, messageId, group, chatUserData, content);
		}
	}

	protected void showChatMessage(boolean isOwn, String messageId, final Group group, UserData chatUserData, Content content) {
		String groupId = group.getId();

		GroupLastManager glm = appContext.getManager(GroupLastManager.class);
		GroupChatManager cm = this.appContext.getManager(GroupChatManager.class);

		String time = cm.getTimeText(groupId, content.getTimestamp());
		String text = CoreContentUtil.getText(content);

		cm.putGroupMessageId(groupId, messageId);
		cm.groupChat(isOwn, group, chatUserData, content);
		cm.updateGroupChatItemInfo(groupId, text, time);

		glm.addOrUpdateLastGroup(group);
		glm.updateLastGroupChatInfo(groupId, text, time);

		boolean isGroupChatShowing = cm.isGroupChatShowing(groupId);

		if ((!isGroupChatShowing) && !isOwn) {
			cm.plusUnread(groupId);

			CallAction callAction = new CallAction() {

				@Override
				public void execute() {
					showGroupChat(group);
				}
			};

			// TODO 消息头像提醒做的有点生硬，有机会再重构
			PromptManager pm = this.appContext.getManager(PromptManager.class);

			PromptData pd = new PromptData(IconType.groupHead, group.getHead(), callAction);
			pd.setKey(groupId);

			pm.put(groupId, pd);

			pm.showGroupHeadPulse(groupId, true);
			pm.playSound(SoundHandler.sound_type_message);

			int count = cm.getUnreadCount(groupId);
			glm.setLastGroupItemRed(groupId, true, count);
			cm.setChatGroupItemRed(groupId, true, count);
		}
	}

	public void showGroupChat(Group group) {
		GroupChatManager cm = this.appContext.getManager(GroupChatManager.class);
		cm.showGroupChat(group);

		String groupId = group.getId();
		removeGroupPrompt(groupId);
	}

	public void showGroupHistory(String groupId) {
		GroupChatManager cm = this.appContext.getManager(GroupChatManager.class);
		cm.showGroupHistory(groupId);

		removeGroupPrompt(groupId);
	}

	public void removeGroupPrompt(String groupId) {
		PromptManager pm = this.appContext.getManager(PromptManager.class);
		pm.remove(groupId);// 系统托盘停止跳动
		pm.showGroupHeadPulse(groupId, false);// 停止头像跳动

		GroupLastManager glm = appContext.getManager(GroupLastManager.class);
		glm.setLastGroupItemRed(groupId, false, 0);
		GroupChatManager cm = this.appContext.getManager(GroupChatManager.class);
		cm.readGroupChat(groupId);
	}
}