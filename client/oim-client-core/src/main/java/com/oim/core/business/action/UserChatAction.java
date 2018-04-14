package com.oim.core.business.action;

import com.oim.core.business.service.UserChatService;
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
public class UserChatAction extends AbstractAction {

	public UserChatAction(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 收到用户聊天信息
	 */
	@MethodMapping(value = "1.2.1001")
	public void receiveUserChatMessage(
			@Define("messageKey") String messageId,
			@Define("sendUserId") String sendUserId,
			@Define("receiveUserId") String receiveUserId,
			@Define("content") Content content) {
		UserChatService cs = appContext.getService(UserChatService.class);
		cs.userChat(messageId, sendUserId, receiveUserId, content);
	}

	/**
	 * 收到抖动窗口信息
	 */
	@MethodMapping(value = "1.2.1002")
	public void receiveShake(@Define("sendUserId") String sendUserId) {
		UserChatService cs = appContext.getService(UserChatService.class);
		cs.receiveShake(sendUserId);
	}
}
