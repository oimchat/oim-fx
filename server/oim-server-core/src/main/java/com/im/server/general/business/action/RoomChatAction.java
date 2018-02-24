package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.im.server.general.common.service.BanService;
import com.im.server.general.common.service.RoomChatLogService;
import com.im.server.general.common.service.RoomChatService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.only.query.page.DefaultPage;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.query.ChatQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

@Component
@ActionMapping(value = "1.500")
public class RoomChatAction {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	RoomChatService roomChatService;
	@Resource
	BanService banService;
	@Resource
	RoomChatLogService roomChatLogService;
	/**
	 * 向聊天室发送信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午10:36:10
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午10:36:10
	 */
	@MethodMapping(value = "1.1.3001")
	public void roomChat(Head head, SocketSession socketSession,
			@Define("userId") String userId,
			@Define("roomId") String roomId,
			@Define("content") Content content) {
		roomId = (null == roomId || "".equals(roomId)) ? "default" : roomId;
		roomChatService.roomChat(head.getKey(), userId, roomId, content);
	}

	/**
	 * 获取聊天室聊天记录
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年11月29日 上午10:37:28
	 * @update: XiaHui
	 * @updateDate: 2016年11月29日 上午10:37:28
	 */
	@MethodMapping(value = "1.1.3004")
	public Object queryRoomChatLog(
			@Define("roomId") String roomId,
			@Define("chatQuery") ChatQuery chatQuery,
			@Define("page") PageData pageData) {
		DefaultPage page = new DefaultPage();
		page.setPageSize(pageData.getPageSize());
		page.setPageNumber(pageData.getPageNumber());
		ResultMessage message = roomChatLogService.queryRoomChatContentList(roomId, chatQuery, page);
		return message;
	}
}
