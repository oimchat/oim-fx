package com.oim.core.business.controller;

import java.io.File;
import java.util.List;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.common.util.KeyUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.manager.VideoManager;
import com.oim.core.business.sender.UserChatSender;
import com.oim.core.business.sender.VideoSender;
import com.oim.core.business.service.UserSendChatService;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.only.common.result.Info;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;

public class UserChatController extends AbstractController {

	public UserChatController(AppContext appContext) {
		super(appContext);
	}

	public void sendUserChatMessage(final String receiveUserId, final Content content) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData owner = pb.getUserData();
		final String sendUserId = owner.getId();
		final String messageId = KeyUtil.getKey();
		content.setTimestamp(System.currentTimeMillis());

		final UserChatSender userChatSender = appContext.getSender(UserChatSender.class);
		List<Item> items = CoreContentUtil.getImageItemList(content);
		if (!items.isEmpty()) {

			ImageManager im = this.appContext.getManager(ImageManager.class);
			BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

				@Override
				public void back(BackInfo backInfo, List<FileHandleInfo> t) {
					userChatSender.sendUserChatMessage(messageId, sendUserId, receiveUserId, content, null);
				}
			};
			im.uploadImage(items, backAction);
		} else {
			userChatSender.sendUserChatMessage(messageId, sendUserId, receiveUserId, content, null);
		}
	}

	
	public Info sendFile(final String receiveUserId, final File file ) {
		UserSendChatService cs=appContext.getService(UserSendChatService.class);
		cs.sendUserFile(receiveUserId, file);
		return null;
	}
	
	/**
	 * 发送抖动操作
	 * 
	 * @author XiaHui
	 * @date 2018-01-19 17:08:42
	 * @param receiveId
	 */
	public void sendShake(String receiveUserId) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		String sendUserId = user.getId();
		UserChatSender cs = this.appContext.getSender(UserChatSender.class);
		cs.sendShake(receiveUserId, sendUserId);
	}
	
	public void requestVideo(UserData userData) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData sendUser = pb.getUserData();
		VideoManager vm = this.appContext.getManager(VideoManager.class);
		vm.showSendVideoFrame(userData);
		VideoSender vh = this.appContext.getSender(VideoSender.class);
		vh.requestVideo(sendUser.getId(), userData.getId());
	}
}
