package com.oim.core.business.action;

import java.util.HashMap;
import java.util.List;

import com.oim.core.business.service.ChatService;
import com.oim.core.business.service.HeadService;
import com.oim.core.business.service.UserService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */

@ActionMapping(value = "1.101")
public class UserAction extends AbstractAction {

	public UserAction(AppContext appContext) {
		super(appContext);
	}

	/***
	 * 接受服务器传来好友列表信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	@MethodMapping(value = "1.1.0004")
	public void setUserCategoryWithUserList(
			@Define("userCategoryList") List<UserCategory> userCategoryList,
			@Define("userDataList") List<UserData> userDataList,
			@Define("userCategoryMemberList") List<UserCategoryMember> userCategoryMemberList) {
		UserService userService = appContext.getService(UserService.class);
		userService.setUserCategoryWithUserList(userCategoryList, userDataList, userCategoryMemberList);
		ChatService cs = appContext.getService(ChatService.class);
		cs.getOfflineMessage();
	}

	/***
	 * 接受用户信息更新
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	@MethodMapping(value = "1.2.0008")
	public void updateUserStatus(@Define("userId") String userId, @Define("status") String status) {
		UserService userService = appContext.getService(UserService.class);
		userService.updateUserStatus(userId, status);
	}
	

	@MethodMapping(value = "1.2.0009")
	public void updateUser(@Define("userId") String userId) {
		UserService userService = appContext.getService(UserService.class);
		userService.updateUserData(userId);
	}

	/**
	 * 跟新用户在线状态
	 * @param list
	 */
	@MethodMapping(value = "1.1.0011")
	public void updateUserStatusList(@Define("list") List<HashMap<String,String>> list ) {
//		UserBox ub=appContext.getBox(UserBox.class);
//		UserData userData = ub.getUserData(userId);
//		if (null != userData) {
//			userData.setStatus(status);
//			UserService userService = appContext.getService(UserService.class);
//			userService.updateUserData(userData);
//		}
	}
	
	@MethodMapping(value = "1.1.0013")
	public void setUserHeadList(@Define("headList") List<UserHead> headList) {
		HeadService headService = appContext.getService(HeadService.class);
		headService.setUserHeadList(headList);
	}

	@MethodMapping(value = "0010")
	public void updateUser(@Define("userData") UserData userData) {
		UserService userService = appContext.getService(UserService.class);
		userService.updateUserData(userData);
	}
}
