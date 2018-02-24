package com.im.server.general.common.bean;

import com.im.base.bean.BaseBean;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

/**
 * @description:群的聊天记录
 * @author: Only
 * @date: 2016年8月15日 下午2:59:51
 */
@Entity(name = "im_group_chat_item")
public class GroupChatItem extends BaseBean {

	private String groupChatContentId;
	private String messageId;// 消息id
	private String groupId;// 群id
	private String userId;// 发送消息用户id
	private String type;//
	@Type(type = "text")
	private String value;//
	@Type(type = "text")
	private String filterValue;// 过滤后内容
	private int rank;// 排序
	private int section;// 段落

	public String getGroupChatContentId() {
		return groupChatContentId;
	}

	public void setGroupChatContentId(String groupChatContentId) {
		this.groupChatContentId = groupChatContentId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
