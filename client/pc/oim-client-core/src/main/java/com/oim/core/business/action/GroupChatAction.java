package com.oim.core.business.action;

import com.oim.core.business.service.GroupChatService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.message.data.chat.Content;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 15:58:46
 */
@ActionMapping(value = "1.500")
public class GroupChatAction extends AbstractAction {

	public GroupChatAction(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 收到群信息
	 */
	@MethodMapping(value = "1.2.2001")
	public void receiveGroupChatMessage(
			@Define("messageKey") String messageId,
			@Define("userId") String userId,
			@Define("groupId") String groupId,
			@Define("content") Content content) {
		GroupChatService chatService = appContext.getService(GroupChatService.class);
		chatService.groupChat(messageId, groupId, userId, content);
	}
}
