package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月8日 下午8:22:48
 * @version 0.0.1
 */
public class GroupCategorySender extends BaseSender {

	public GroupCategorySender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 向服务器发送添加群分组信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param name
	 * @param action
	 */
	public void addGroupCategory(String name, DataBackAction action) {

		GroupCategory groupCategory = new GroupCategory();
		groupCategory.setName(name);

		Message message = new Message();
		message.put("groupCategory", groupCategory);
		
		Head head = new Head();
		head.setAction("1.202");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, action);
	}
	
	public void getGroupCategory(String groupCategoryId) {

		Message message = new Message();
		message.put("groupCategoryId", groupCategoryId);
		
		Head head = new Head();
		head.setAction("1.202");
		head.setMethod("1.1.0002");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message);
	}
	
	
	public void updateGroupCategoryName(String groupCategoryId,String groupCategoryName, DataBackAction action) {


		Message message = new Message();
		message.put("groupCategoryId", groupCategoryId);
		message.put("groupCategoryName", groupCategoryName);
		
		Head head = new Head();
		head.setAction("1.202");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, action);
	}
}
