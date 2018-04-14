package com.oim.core.business.action;

import java.util.List;

import com.oim.core.business.service.ChatService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;

/**
 * 描述：负责接受聊天相关业务的控制层
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */

@ActionMapping(value = "1.500")
public class ChatAction extends AbstractAction {

	public ChatAction(AppContext appContext) {
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
		ChatService chatService = appContext.getService(ChatService.class);
		chatService.receiveUserChatMessage(messageId, sendUserId, receiveUserId, content);
	}

	/**
	 * 收到抖动窗口信息
	 */
	@MethodMapping(value = "1.2.1002")
	public void receiveShake(@Define("sendUserId") String sendUserId) {
		ChatService chatService = appContext.getService(ChatService.class);
		chatService.receiveShake(sendUserId);
	}

	/**
	 * 收到群信息
	 */
	@MethodMapping(value = "1.2.2001")
	public void receiveGroupChatMessage(
			@Define("userId") String userId,
			@Define("groupId") String groupId,
			@Define("content") Content content) {
		ChatService chatService = appContext.getService(ChatService.class);
		chatService.receiveGroupChatMessage(userId, groupId, content);
	}

	/**
	 * 收到最后聊天列表
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @param userLastList
	 * @param groupLastList
	 * @createDate: 2017年6月9日 下午3:56:50
	 * @update: XiaHui
	 * @updateDate: 2017年6月9日 下午3:56:50
	 */
	@MethodMapping(value = "1.1.1011")
	public void setLastList(
			@Define("userId") String userId,
			@Define("userLastList") List<UserChatHistoryData> userLastList,
			@Define("groupLastList") List<GroupChatHistoryData> groupLastList) {
		ChatService chatService = appContext.getService(ChatService.class);
		chatService.setLastList(userId, userLastList, groupLastList);
	}
}
