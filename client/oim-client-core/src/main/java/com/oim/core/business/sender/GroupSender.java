package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.query.GroupQuery;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class GroupSender extends BaseSender {


	public GroupSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 发送获取群列表请求
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void getGroupCategoryWithGroupList() {
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message);
	}

	/**
	 * 发送查询群消息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 * @param page
	 * @param dataBackAction
	 */
	public void queryGroupList(GroupQuery groupQuery, PageData page, DataBackActionAdapter dataBackAction) {
		Message message = new Message();
		message.put("groupQuery", groupQuery);
		message.put("page", page == null ? (new PageData()) : page);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0006");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	/**
	 * 发送新建群信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 * @param action
	 */
	public void addGroup(Group group, DataBackAction action) {
		Message message = new Message();
		message.put("group", group);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0008");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/***
	 * 发送修改群信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 * @param action
	 */
	public void updateGroup(Group group, DataBackAction action) {
		Message message = new Message();
		message.put("group", group);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0009");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/**
	 * 发送获取群成员信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param groupId
	 * @param action
	 */
	public void getGroupMemberListWithUserDataList(String groupId, DataBackAction action) {
		Message message = new Message();
		message.put("groupId", groupId);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0010");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}


	/**
	 * 上传群头像
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 下午5:21:20
	 * @param groupHead
	 * @param action
	 */
	public void uploadHead(GroupHead groupHead, DataBackAction action) {
		Message message = new Message();
		message.put("groupHead", groupHead);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0011");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	public void getGroupHead(String groupId, DataBackAction action) {
		Message message = new Message();
		message.put("groupId", groupId);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0012");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/**
	 * 获取群列表头像
	 * @author XiaHui
	 * @date 2017年6月18日 下午5:22:58
	 * @param userId
	 */
	public void getGroupHeadList(String userId) {
		Message message = new Message();
		message.put("userId", userId);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0013");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
	
	
	public void getUserInGroupMemberList() {
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0016");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	
	public void getGroupById(String groupId, DataBackAction dataBackAction) {
		Message message = new Message();
		message.put("groupId", groupId);
		Head head = new Head();
		head.setAction("1.201");
		head.setMethod("1.1.0005");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message,dataBackAction);
	}
}
