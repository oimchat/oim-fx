package com.oim.core.business.sender;

import com.oim.core.business.box.PersonalBox;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月8日 下午8:22:48
 * @version 0.0.1
 */
public class UserCategorySender extends BaseSender {

	public UserCategorySender(AppContext appContext) {
		super(appContext);
	}

	/***
	 * 新增好友分组
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param name
	 * @param action
	 */
	public void addUserCategory(String name, DataBackAction action) {
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		UserData user = pb.getUserData();
		
		UserCategory userCategory = new UserCategory();
		userCategory.setName(name);
		userCategory.setUserId(user.getId());
		

		Message message = new Message();
		message.put("userCategory", userCategory );

		Head head = new Head();
		
		head.setAction("1.102");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, action);
	}
	
	/**
	 * 修改分组名
	 * @author XiaHui
	 * @date 2017年9月4日 下午3:55:19
	 * @param userCategoryId
	 * @param userCategoryName
	 * @param action
	 */
	public void updateUserCategoryName(String userCategoryId,String userCategoryName, DataBackAction action) {

		Message message = new Message();
		message.put("userCategoryId", userCategoryId );
		message.put("userCategoryName", userCategoryName );

		Head head = new Head();
		
		head.setAction("1.102");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, action);
	}
}
