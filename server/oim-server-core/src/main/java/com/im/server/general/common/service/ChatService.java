//package com.im.server.general.common.service;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.im.server.general.common.bean.RoomChatContent;
//import com.im.server.general.common.bean.RoomChatItem;
//import com.im.server.general.common.bean.UserChatContent;
//import com.im.server.general.common.bean.UserChatItem;
//import com.im.server.general.common.bean.UserLastChat;
//import com.im.server.general.common.dao.RoomChatDAO;
//import com.im.server.general.common.dao.UserChatDAO;
//import com.im.server.general.common.data.ChatItem;
//import com.im.server.general.common.manager.LastChatManager;
//import com.im.server.general.common.service.api.RoomBaseService;
//import com.im.server.general.common.service.api.UserBaseService;
//import com.im.server.general.common.service.api.WordsFilterBaseService;
//import com.im.server.general.business.message.data.UserData;
//import com.im.server.general.business.message.data.chat.Content;
//import com.im.server.general.business.message.data.chat.Item;
//import com.im.server.general.business.message.data.chat.Section;
//import com.im.server.general.business.mq.MessageQueueWriteHandler;
//import com.im.server.general.business.push.ChatPush;
//import com.im.server.general.business.thread.SessionServerHandler;
//import com.im.base.common.util.KeyUtil;
//
///**
// * @description:
// * @author: Only
// * @date: 2016年8月16日 上午9:38:51
// */
//@Service
//public class ChatService {
//	protected final Logger logger = LogManager.getLogger(this.getClass());
//	@Resource
//	MessageQueueWriteHandler messageQueueWriteHandler;
//	@Resource
//	UserBaseService userBaseService;
//	@Resource
//	RoomBaseService roomBaseService;
//	@Resource
//	WordsFilterBaseService wordsFilterBaseService;
//	@Resource
//	RoomChatDAO roomChatDAO;
//	@Resource
//	UserChatDAO userChatDAO;
//	@Resource
//	ChatPush chatPush;
//	@Resource
//	SessionServerHandler sessionServerHandler;
//	@Resource
//	LastChatManager lastChatManager;
//	/**
//	 * 聊天室聊天信息推送，将用户所发的信息推送到其所在聊天室的其它用户
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年8月19日 下午3:25:08
//	 * @update: XiaHui
//	 * @updateDate: 2016年8月19日 下午3:25:08
//	 */
//	@Transactional
//	public void roomChat(String key, String userId, String roomId, Content content) {
//		List<ChatItem> chatItemList = wordsFilter(content);
//		long timestamp=content.getTimestamp();
//		UserData userData = userBaseService.getUserData(userId);
//		pushRoomChat(key, userId, roomId, content);
//		addRoomChatLog(userData, key, roomId, chatItemList,timestamp);
//	}
//
//	public void pushRoomChat(String key, String userId, String roomId, Content content) {
//		List<String> userIdList = roomBaseService.getUserIdList(roomId);
//		chatPush.pushRoomChat(key, roomId, userId, content, userIdList);
//	}
//
//	private List<ChatItem> wordsFilter(Content content) {
//		content.setTimestamp(System.currentTimeMillis());
//		List<ChatItem> chatItemList = new ArrayList<ChatItem>();
//
//		List<Section> sections = content.getSections();
//
//		if (null != sections) {
//			int sectionsSize = sections.size();
//			for (int i = 0; i < sectionsSize; i++) {
//				Section section = sections.get(i);
//				List<Item> items = section.getItems();
//				if (null != items) {
//					int itemsSize = items.size();
//					for (int j = 0; j < itemsSize; j++) {
//						ChatItem chatItem = new ChatItem();
//
//						Item item = items.get(j);
//						String type = item.getType();
//						String value = item.getValue();
//
//						chatItem.setType(type);
//						chatItem.setValue(value);
//						chatItem.setSection(i);
//						chatItem.setRank(j);
//
//						if (Item.type_text.equals(type)) {
//							value = wordsFilterBaseService.wordsFilter(value);
//							item.setValue(value);
//							chatItem.setFilterValue(value);
//						} else {
//							chatItem.setFilterValue(value);
//						}
//						chatItemList.add(chatItem);
//					}
//				}
//			}
//		}
//		return chatItemList;
//	}
//
//	/**
//	 * 记录聊天室的聊天内容
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年8月19日 下午3:26:10
//	 * @update: XiaHui
//	 * @updateDate: 2016年8月19日 下午3:26:10
//	 */
//	@Transactional
//	private void addRoomChatLog(UserData userData, String messageId, String roomId, List<ChatItem> chatItemList,long timestamp) {
//		messageId = (null == messageId || "".equals(messageId)) ? KeyUtil.getKey() : messageId;
//		if (null != userData) {
//			RoomChatContent content = createRoomChatContent(messageId, roomId, userData,timestamp);
//			roomChatDAO.save(content);
//			List<RoomChatItem> list = new ArrayList<RoomChatItem>();
//			for (ChatItem item : chatItemList) {
//				RoomChatItem chatLog = new RoomChatItem();
//				chatLog.setMessageId(messageId);
//				chatLog.setRoomChatContentId(content.getId());
//				chatLog.setRoomId(content.getRoomId());
//				chatLog.setUserId(content.getUserId());
//				chatLog.setRank(item.getRank());
//				chatLog.setSection(item.getSection());
//				chatLog.setType(item.getType());
//				chatLog.setValue(item.getValue());
//				chatLog.setFilterValue(item.getFilterValue());
//				list.add(chatLog);
//			}
//			roomChatDAO.saveList(list);
//		}
//	}
//
//	private RoomChatContent createRoomChatContent(String messageId, String roomId, UserData userData,long timestamp) {
//		RoomChatContent chatLog = new RoomChatContent();
//		chatLog.setMessageId(messageId);
//		chatLog.setRoomId(roomId);
//		chatLog.setRoomName("");
//		chatLog.setTime(new Timestamp(timestamp));
//		chatLog.setTimestamp(timestamp);
//		chatLog.setUserHead(userData.getHead());
//		chatLog.setUserId(userData.getId());
//		chatLog.setUserName(userData.getName());
//		chatLog.setUserNickname(userData.getNickname());
//		chatLog.setUserRemark("");
//		chatLog.setIsSend("1");
//		return chatLog;
//	}
//
//	/**
//	 * 用户对用户聊天
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年8月19日 下午3:26:32
//	 * @update: XiaHui
//	 * @updateDate: 2016年8月19日 下午3:26:32
//	 */
//	@Transactional
//	public void userChat(String key, String sendUserId, String receiveUserId, Content content) {
//
//		List<ChatItem> chatItemList = wordsFilter(content);
//		long timestamp=content.getTimestamp();
//		UserData sendUserData = userBaseService.getUserData(sendUserId);
//		UserData receiveUserData = userBaseService.getUserData(receiveUserId);
//		boolean isSend = sessionServerHandler.hasSession(receiveUserId);
//		chatPush.pushUserChat(key, sendUserId, receiveUserId, content);
//		if (null != sendUserData && null != receiveUserData) {
//			addUserChatLog(isSend, sendUserData, receiveUserData, key, chatItemList,timestamp);
//		}
//	}
//
//	/**
//	 * 记录用户聊天内容
//	 * 
//	 * @author: XiaHui
//	 * @createDate: 2016年8月19日 下午3:26:46
//	 * @update: XiaHui
//	 * @updateDate: 2016年8月19日 下午3:26:46
//	 */
//	@Transactional
//	private void addUserChatLog(boolean isSend, UserData sendUserData, UserData receiveUserData, String messageId,
//			List<ChatItem> chatItemList,long timestamp) {
//		messageId = (null == messageId || "".equals(messageId)) ? KeyUtil.getKey() : messageId;
//		UserChatContent content = createUserChatContent(isSend, messageId, sendUserData, receiveUserData,timestamp);
//		userChatDAO.save(content);
//		List<UserChatItem> list = new ArrayList<UserChatItem>();
//		for (ChatItem item : chatItemList) {
//			UserChatItem chatLog = new UserChatItem();
//			chatLog.setMessageId(messageId);
//			chatLog.setUserChatContentId(content.getId());
//			chatLog.setSendUserId(content.getSendUserId());
//			chatLog.setReceiveUserId(content.getReceiveUserId());
//			chatLog.setRank(item.getRank());
//			chatLog.setSection(item.getSection());
//			chatLog.setType(item.getType());
//			chatLog.setValue(item.getValue());
//			chatLog.setFilterValue(item.getFilterValue());
//			list.add(chatLog);
//		}
//		userChatDAO.saveList(list);
//		String sendId=sendUserData.getId();
//		String receiveId=receiveUserData.getId();
//		UserLastChat userLastChat1 = createUserLastChat(messageId, content.getId(), sendId,receiveId, UserLastChat.TYPE_USER);
//		saveOrUpdateLast(userLastChat1);
//		
//		if(!sendId.equals(receiveId)){
//			UserLastChat userLastChat2 = createUserLastChat(messageId, content.getId(), receiveId, sendId, UserLastChat.TYPE_USER);
//			saveOrUpdateLast(userLastChat2);
//		}
//	}
//
//	private UserChatContent createUserChatContent(boolean isSend, String messageId, UserData sendUserData, UserData receiveUserData,long timestamp) {
//		UserChatContent chatLog = new UserChatContent();
//		chatLog.setMessageId(messageId);
//		chatLog.setIsDeleted("0");
//		chatLog.setIsSend(isSend ? "1" : "0");
//		chatLog.setReceiveUserHead(receiveUserData.getHead());
//		chatLog.setReceiveUserId(receiveUserData.getId());
//		chatLog.setReceiveUserName(receiveUserData.getName());
//		chatLog.setReceiveUserNickname(receiveUserData.getNickname());
//		chatLog.setTime(new Timestamp(timestamp));
//		chatLog.setTimestamp(timestamp);
//		chatLog.setSendUserHead(sendUserData.getHead());
//		chatLog.setSendUserId(sendUserData.getId());
//		chatLog.setSendUserName(sendUserData.getName());
//		chatLog.setSendUserNickname(sendUserData.getNickname());
//		chatLog.setSendUserRemark("");
//		return chatLog;
//	}
//
//	public void pushRoomChatInfo(String roomId, UserData userData, Content content) {
//		List<String> userIdList = roomBaseService.getUserIdList(roomId);
//		chatPush.pushRoomChatInfo(roomId, userData, content, userIdList);
//	}
//	
//	private UserLastChat createUserLastChat(String messageId, String contentId, String userId, String chatId, String type) {
//
//		long timestamp = System.currentTimeMillis();
//
//		UserLastChat bean = new UserLastChat();
//		bean.setChatId(chatId);
//		bean.setContentId(contentId);
//		bean.setMessageId(messageId);
//		bean.setTime(new Timestamp(timestamp));
//		bean.setTimestamp(timestamp);
//		bean.setType(type);
//		bean.setUserId(userId);
//		return bean;
//	}
//
//	private void saveOrUpdateLast(UserLastChat userLastChat) {
//		lastChatManager.saveOrUpdate(userLastChat);
//	}
//}
