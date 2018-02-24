package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.query.ChatQuery;

/**
 * 
 * @author: XiaHui
 * @date 2017-12-25 16:32:57
 */
public class UserChatSender extends BaseSender {

	public UserChatSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 给联系人发送信息
	 * 
	 * @author XiaHui
	 * @date 2018-01-06 17:32:47
	 * @param messageId
	 * @param sendUserId
	 * @param receiveUserId
	 * @param content
	 * @param action
	 */
	public void sendUserChatMessage(String messageId, String sendUserId, String receiveUserId, Content content, DataBackActionAdapter action) {
		Message message = new Message();
		message.put("receiveUserId", receiveUserId);// 接受信息的用户id
		message.put("sendUserId", sendUserId);// 发送人的id
		message.put("content", content);// 聊天内容

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.1001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/**
	 * 发送抖动窗口
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param receiveId
	 * @param sendId
	 */
	public void sendShake(String receiveUserId, String sendUserId) {
		Message message = new Message();
		message.put("receiveUserId", receiveUserId);// 接受信息的用户id
		message.put("sendUserId", sendUserId);// 发送人的id

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.1002");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	public void queryUserChatLog(String sendUserId, String receiveUserId, ChatQuery chatQuery, PageData page, DataBackAction action) {
		Message message = new Message();

		message.put("sendUserId", sendUserId);// 接受信息的群id
		message.put("receiveUserId", receiveUserId);// 发送人的id
		message.put("chatQuery", chatQuery);// 聊天内容
		message.put("page", page);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.1004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	public void getOfflineUserIdList(DataBackAction dataBackAction) {
		Message message = new Message();

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.1008");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	/**
	 * 更新消息为已读
	 */
	public void updateUserOfflineMessageToRead(String sendUserId) {
		Message message = new Message();
		message.put("sendUserId", sendUserId);

		Head head = new Head();
		head.setAction("1.500");
		head.setMethod("1.1.1009");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
