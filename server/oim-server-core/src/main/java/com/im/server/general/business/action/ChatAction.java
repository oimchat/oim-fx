package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.im.server.general.common.service.LastChatService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.server.ResultMessage;

@Component
@ActionMapping(value = "1.500")
public class ChatAction {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	LastChatService lastChatService;

	/**
	 * 获取最近聊天列表
	 * 
	 * @author: XiaHui
	 * @param head
	 * @param socketSession
	 * @return
	 * @createDate: 2017年4月26日 下午1:55:00
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午1:55:00
	 */
	@MethodMapping(value = "1.1.0001")
	public Object getLastChatList(Head head,
			SocketSession socketSession,
			@Define("userId") String userId,
			@Define("type") String type,
			@Define("count") int count) {
		userId = socketSession.getKey();
		ResultMessage message = lastChatService.getLastChatList(userId, type, count);
		return message;
	}
	
	@MethodMapping(value = "1.1.0002")
	public Object getLastChatWithDataList(Head head,
			SocketSession socketSession,
			@Define("userId") String userId,
			@Define("type") String type,
			@Define("count") int count) {
		userId = socketSession.getKey();
		ResultMessage message = lastChatService.getLastChatWithDataList(userId, type, count);
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public Object getLastChatWithContentList(Head head,
			SocketSession socketSession,
			@Define("userId") String userId,
			@Define("type") String type,
			@Define("count") int count) {
		userId = socketSession.getKey();
		ResultMessage message = lastChatService.getLastChatWithContentList(userId, type, count);
		return message;
	}

	/**
	 * 删除聊天记录
	 * 
	 * @param head
	 * @param socketSession
	 * @param userId
	 * @param chatId
	 * @param type
	 * @return
	 */
	@MethodMapping(value = "1.1.0004")
	public Object removeLastChat(Head head,
			SocketSession socketSession,
			@Define("userId") String userId,
			@Define("chatId") String chatId,
			@Define("type") String type) {
		// String userId = socketSession.getKey();
		ResultMessage message = new ResultMessage();
		lastChatService.remove(userId, chatId, type);
		return message;
	}
}
