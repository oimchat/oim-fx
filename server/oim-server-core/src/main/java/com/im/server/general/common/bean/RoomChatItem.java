package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
/**
 * @description:聊天室的聊天记录
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_room_chat_item")
public class RoomChatItem extends BaseBean  {

	
	private String roomChatContentId;
	private String messageId;// 消息id
	private String roomId;// 聊天室id
	private String userId;// 发送消息用户id
	private String type;//
	@Type(type="text") 
	private String value;//
	@Type(type="text") 
	private String filterValue;// 过滤后内容
	private int rank;// 排序
	private int section;// 段落
	

	public String getRoomChatContentId() {
		return roomChatContentId;
	}

	public void setRoomChatContentId(String roomChatContentId) {
		this.roomChatContentId = roomChatContentId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

}
