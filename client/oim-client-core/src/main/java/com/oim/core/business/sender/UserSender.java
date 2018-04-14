package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.query.UserQuery;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class UserSender extends BaseSender {

	public UserSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 发送获取好友信息
	 */
	public void getUserCategoryWithUserList() {
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	/**
	 * 查询用户
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userData
	 * @param page
	 * @param dataBackAction
	 */
	public void queryUserDataList(UserQuery userQuery, PageData page, DataBackAction dataBackAction) {
		Message message = new Message();

		message.put("userQuery", userQuery);
		message.put("page", page == null ? (new PageData()) : page);

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0006");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	/**
	 * 用户状态发生变化时，给好友发送状态变化信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param status
	 */
	public void sendUpdateStatus(String status) {

		Message message = new Message();
		message.put("userId", "");
		message.put("status", status);

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0008");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	/***
	 * 获取用户详细信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userId
	 * @param dataBackAction
	 */
	public void getUserDataById(String userId, DataBackAction dataBackAction) {
		Message message = new Message();
		message.put("userId", userId);

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0005");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	public void sendUpdate() {

		Message message = new Message();
		message.put("userId", "");

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0009");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
	
	
	public void getUserDataStatusList(String userId) {

		Message message = new Message();
		message.put("userId", userId);

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0011");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	/**
	 * 获取好友头像
	 * @param userId
	 */
	public void getUserHeadList(String userId) {

		Message message = new Message();
		message.put("userId", userId);

		Head head = new Head();
		head.setAction("1.101");
		head.setMethod("1.1.0013");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
