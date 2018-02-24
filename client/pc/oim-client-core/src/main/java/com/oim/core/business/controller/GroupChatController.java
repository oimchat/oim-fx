package com.oim.core.business.controller;

import java.util.List;

import com.oim.common.chat.util.CoreContentUtil;
import com.oim.common.util.KeyUtil;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.ImageManager;
import com.oim.core.business.sender.GroupChatSender;
import com.oim.core.common.action.BackAction;
import com.oim.core.common.action.BackInfo;
import com.oim.core.common.component.file.FileHandleInfo;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;

public class GroupChatController extends AbstractController {

	public GroupChatController(AppContext appContext) {
		super(appContext);
	}

	public void sendGroupChatMessage(final String groupId, final Content content) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		UserData owner = pb.getUserData();
		final String userId = owner.getId();
		final String messageId = KeyUtil.getKey();
		content.setTimestamp(System.currentTimeMillis());

		final GroupChatSender cs = appContext.getSender(GroupChatSender.class);
		List<Item> items = CoreContentUtil.getImageItemList(content);
		if (!items.isEmpty()) {

			ImageManager im = this.appContext.getManager(ImageManager.class);
			BackAction<List<FileHandleInfo>> backAction = new BackAction<List<FileHandleInfo>>() {

				@Override
				public void back(BackInfo backInfo, List<FileHandleInfo> t) {
					cs.sendGroupChatMessage(messageId, groupId, userId, content, null);
				}
			};
			im.uploadImage(items, backAction);
		} else {
			cs.sendGroupChatMessage(messageId, groupId, userId, content, null);
		}
	}
}
