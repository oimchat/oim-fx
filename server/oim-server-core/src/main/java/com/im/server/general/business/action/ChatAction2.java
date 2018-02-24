//package com.im.server.general.business.action;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import com.im.server.general.common.bean.GroupMember;
//import com.im.server.general.common.service.BanService;
//import com.im.server.general.common.service.ChatService;
//import com.im.server.general.common.service.GroupService;
//import com.im.server.general.common.service.InfoService;
//import com.im.server.general.common.service.LastChatService;
//import com.im.server.general.common.service.RoomChatLogService;
//import com.im.server.general.common.service.UserChatLogService;
//import com.im.server.general.common.message.Head;
//import com.im.server.general.common.message.ResultMessage;
//import com.im.server.general.business.message.data.PageData;
//import com.im.server.general.business.message.data.chat.Content;
//import com.im.server.general.business.message.data.query.ChatQuery;
//import com.im.server.general.business.mq.MessageQueueWriteHandler;
//import com.im.server.general.business.push.ChatPush;
//import com.only.general.annotation.action.ActionMapping;
//import com.only.general.annotation.action.MethodMapping;
//import com.only.general.annotation.parameter.Define;
//import com.only.net.session.SocketSession;
//import com.only.query.page.DefaultPage;
//
//@Component
//@ActionMapping(value = "1.500")
//public class ChatAction {
//	protected final Logger logger = LogManager.getLogger(this.getClass());
//	@Resource
//	ChatService chatService;
//	@Resource
//	InfoService infoService;
//	@Resource
//	BanService banService;
//	@Resource
//	RoomChatLogService roomChatLogService;
//	@Resource
//	UserChatLogService userChatLogService;
//	@Resource
//	MessageQueueWriteHandler messageQueueWriteHandler;
//	@Resource
//	GroupService groupService;
//	@Resource
//	ChatPush chatPush;
//	@Resource
//	LastChatService lastChatService;
//
//	/**
//	 * 向用户发送私聊信息
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年11月29日 上午10:36:03
//	 * @update: XiaHui
//	 * @updateDate: 2016年11月29日 上午10:36:03
//	 */
//	@MethodMapping(value = "1.1.1001")
//	public void userChat(Head head,
//			@Define("sendUserId") String sendUserId,
//			@Define("receiveUserId") String receiveUserId,
//			@Define("content") Content content) {
//		chatService.userChat(head.getKey(), sendUserId, receiveUserId, content);
//	}
//
//	@MethodMapping(value = "1.1.1002")
//	public void userShake(Head head, SocketSession socketSession, @Define("receiveUserId") String receiveUserId) {
//		chatPush.userShake(head.getKey(), socketSession.getKey(), receiveUserId);
//	}
//
//	@MethodMapping(value = "1.1.2001")
//	public void groupChat(Head head,
//			@Define("userId") String userId,
//			@Define("groupId") String groupId,
//			@Define("content") Content content) {
//
//		content.setTimestamp(System.currentTimeMillis());
//		List<GroupMember> list = groupService.getGroupMemberListByGroupId(groupId);
//		List<String> ids = new ArrayList<String>();
//		for (GroupMember gm : list) {
//			ids.add(gm.getUserId());
//		}
//		chatPush.groupChat(head.getKey(), groupId, userId, content, ids);
//	}
//
//	/**
//	 * 向聊天室发送信息
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年11月29日 上午10:36:10
//	 * @update: XiaHui
//	 * @updateDate: 2016年11月29日 上午10:36:10
//	 */
//	@MethodMapping(value = "1.1.3001")
//	public void roomChat(Head head, SocketSession socketSession,
//			@Define("userId") String userId,
//			@Define("roomId") String roomId,
//			@Define("content") Content content) {
//		roomId = (null == roomId || "".equals(roomId)) ? "default" : roomId;
//		chatService.roomChat(head.getKey(), userId, roomId, content);
//	}
//
//	/**
//	 * 获取聊天室聊天记录
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年11月29日 上午10:37:28
//	 * @update: XiaHui
//	 * @updateDate: 2016年11月29日 上午10:37:28
//	 */
//	@MethodMapping(value = "1.1.3004")
//	public Object queryRoomChatLog(
//			@Define("roomId") String roomId,
//			@Define("chatQuery") ChatQuery chatQuery,
//			@Define("page") PageData pageData) {
//		DefaultPage page = new DefaultPage();
//		page.setPageSize(pageData.getPageSize());
//		page.setPageNumber(pageData.getPageNumber());
//		ResultMessage message = roomChatLogService.queryRoomChatContentList(roomId, chatQuery, page);
//		return message;
//	}
//
//	@MethodMapping(value = "1.1.1004")
//	public Object queryUserChatLog(Head head,
//			@Define("sendUserId") String sendUserId,
//			@Define("receiveUserId") String receiveUserId,
//			@Define("chatQuery") ChatQuery chatQuery,
//			@Define("page") PageData pageData) {
//		DefaultPage page = new DefaultPage();
//		page.setPageSize(pageData.getPageSize());
//		page.setPageNumber(pageData.getPageNumber());
//		ResultMessage message = userChatLogService.queryUserChatLogList(sendUserId, receiveUserId, chatQuery, page);
//
//		Map<String, Object> pageMap = new HashMap<String, Object>();
//		pageMap.put("totalPage", page.getTotalPage());
//		pageMap.put("pageNumber", page.getPageNumber());
//		pageMap.put("pageSize", pageData.getPageSize());
//		pageMap.put("startResult", page.getStartResult());
//		pageMap.put("endResult", page.getEndResult());
//		pageMap.put("totalCount", page.getTotalCount());
//
//		message.put("page", page);
//		return message;
//	}
//
//	/**
//	 * 获取私聊聊天记录（此方法主要以数据库id为条件，为可靠的接口）
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年11月29日 下午5:00:54
//	 * @update: XiaHui
//	 * @updateDate: 2016年11月29日 下午5:00:54
//	 */
//	@MethodMapping(value = "1.1.1005")
//	public Object getUserChatLog(Head head,
//			@Define("sendUserId") String sendUserId,
//			@Define("receiveUserId") String receiveUserId,
//			@Define("startId") String startId,
//			@Define("direction") String direction,
//			@Define("count") int count) {
//		ResultMessage message = userChatLogService.getUserChatLogListByStartId(sendUserId, receiveUserId, startId, direction, count);
//		return message;
//	}
//
//	/**
//	 * 获取私聊聊天记录（此方法主要以消息key为条件，为不可靠的接口）
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年11月29日 上午10:37:54
//	 * @update: XiaHui
//	 * @updateDate: 2016年11月29日 上午10:37:54
//	 */
//	@MethodMapping(value = "1.1.1006")
//	public Object getUserChatLogByMessageId(Head head,
//			@Define("sendUserId") String sendUserId,
//			@Define("receiveUserId") String receiveUserId,
//			@Define("startMessageKey") String startMessageKey,
//			@Define("direction") String direction,
//			@Define("count") int count) {
//		ResultMessage message = userChatLogService.getUserChatLogListByStartMessageId(sendUserId, receiveUserId, startMessageKey, direction, count);
//		return message;
//	}
//
//	/**
//	 * 获取离线消息的发送者id
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2017年4月17日 上午11:21:13
//	 * @update: XiaHui
//	 * @updateDate: 2017年4月17日 上午11:21:13
//	 */
//	@MethodMapping(value = "1.1.1008")
//	public Object getOfflineUserIdList(Head head,
//			SocketSession socketSession) {
//		String userId = socketSession.getKey();
//		ResultMessage message = new ResultMessage();
//		List<String> list = userChatLogService.getOfflineUserIdList(userId);
//		message.put("userIds", list);
//		return message;
//	}
//
//	/**
//	 * 标记信息为已读
//	 * 
//	 * @author: XiaHui
//	 * @param head
//	 * @param socketSession
//	 * @param receiveUserId
//	 * @param sendUserId
//	 * @createDate: 2017年4月26日 下午3:27:26
//	 * @update: XiaHui
//	 * @updateDate: 2017年4月26日 下午3:27:26
//	 */
//	@MethodMapping(value = "1.1.1009")
//	public void updateToRead(Head head,
//			SocketSession socketSession,
//			@Define("sendUserId") String sendUserId) {
//		// String userId = socketSession.getKey();
//		String receiveUserId = socketSession.getKey();
//		userChatLogService.updateUserOfflineMessageToRead(receiveUserId, sendUserId);
//	}
//
//	/**
//	 * 获取离线消息数量
//	 * 
//	 * @author: XiaHui
//	 * @param head
//	 * @param socketSession
//	 * @return
//	 * @createDate: 2017年4月26日 下午1:53:54
//	 * @update: XiaHui
//	 * @updateDate: 2017年4月26日 下午1:53:54
//	 */
//	@MethodMapping(value = "1.1.1010")
//	public Object getOfflineCountList(Head head,
//			SocketSession socketSession,
//			@Define("userId") String userId,
//			@Define("count") int count) {
//
//		ResultMessage message = new ResultMessage();
//		List<Map<String, Object>> list = userChatLogService.getOfflineCountList(userId, count);
//		message.put("list", list);
//		return message;
//	}
//
//	/**
//	 * 获取最近聊天列表
//	 * 
//	 * @author: XiaHui
//	 * @param head
//	 * @param socketSession
//	 * @return
//	 * @createDate: 2017年4月26日 下午1:55:00
//	 * @update: XiaHui
//	 * @updateDate: 2017年4月26日 下午1:55:00
//	 */
//	@MethodMapping(value = "1.1.1011")
//	public Object getLastList(Head head,
//			SocketSession socketSession,
//			@Define("userId") String userId,
//			@Define("type") String type,
//			@Define("count") int count) {
//		userId = socketSession.getKey();
//		ResultMessage message = lastChatService.getLastChatList(userId, type, count);
//		return message;
//	}
//
//	/**
//	 * 根据离线发送者的用户id，获取离线消息数量
//	 * 
//	 * @author: XiaHui
//	 * @param head
//	 * @param socketSession
//	 * @param userId
//	 * @param ids
//	 * @return
//	 * @createDate: 2017年4月27日 上午11:37:57
//	 * @update: XiaHui
//	 * @updateDate: 2017年4月27日 上午11:37:57
//	 */
//	@MethodMapping(value = "1.1.1012")
//	public Object getOfflineCountList(Head head,
//			SocketSession socketSession,
//			@Define("userId") String userId,
//			@Define("ids") List<String> ids) {
//		// String userId = socketSession.getKey();
//		ResultMessage message = new ResultMessage();
//		List<Map<String, Object>> list = userChatLogService.getOfflineCountListByIds(userId, ids);
//		message.put("list", list);
//		return message;
//	}
//
//	/**
//	 * 删除聊天记录
//	 * 
//	 * @param head
//	 * @param socketSession
//	 * @param userId
//	 * @param chatId
//	 * @param type
//	 * @return
//	 */
//	@MethodMapping(value = "1.1.1013")
//	public Object removeLastChat(Head head,
//			SocketSession socketSession,
//			@Define("userId") String userId,
//			@Define("chatId") String chatId,
//			@Define("type") String type) {
//		// String userId = socketSession.getKey();
//		ResultMessage message = new ResultMessage();
//		lastChatService.remove(userId, chatId, type);
//		return message;
//	}
//}
