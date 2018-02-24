package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * @description:用户最近聊天
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_user_last_chat")
public class UserLastChat extends BaseBean {

	private String userId;
	private String messageId;// 消息id
	private String contentId;
	private String chatId;//
	private String type;// 1:和用户
	private Timestamp time;//
	private long timestamp;//

	public static String TYPE_USER = "1";//联系人
	public static String TYPE_GROUP = "2";//群
	public static String TYPE_TEAM = "3";//讨论组
	public static String TYPE_ROOM = "4";//聊天室

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
