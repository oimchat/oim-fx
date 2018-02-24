package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.im.server.general.common.service.GroupChatLogService;
import com.im.server.general.common.service.GroupChatService;
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
public class GroupChatAction {
	
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource
	GroupChatLogService groupChatLogService;
	@Resource
	GroupChatService groupChatService;
	@MethodMapping(value = "1.1.2001")
	public void groupChat(Head head,
			@Define("userId") String userId,
			@Define("groupId") String groupId,
			@Define("content") Content content) {
		groupChatService.groupChat(head.getKey(), userId, groupId, content);
	}
	
	@MethodMapping(value = "1.1.2004")
	public Object queryGroupChatLog(
			SocketSession socketSession,
			@Define("groupId") String groupId,
			@Define("chatQuery") ChatQuery chatQuery,
			@Define("page") PageData pageData) {
		//String userId = socketSession.getKey();
		DefaultPage page = new DefaultPage();
		page.setPageSize(pageData.getPageSize());
		page.setPageNumber(pageData.getPageNumber());
		ResultMessage  message = groupChatLogService.queryGroupChatContentList(groupId, chatQuery, page);
		return message;
	}
}
