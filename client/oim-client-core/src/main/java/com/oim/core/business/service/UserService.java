package com.oim.core.business.service;

import java.util.List;

import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.manager.PromptManager;
import com.oim.core.business.manager.UserListManager;
import com.oim.core.business.sender.UserSender;
import com.oim.core.common.sound.SoundHandler;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 16:42:20
 */
public class UserService extends AbstractService {

	public UserService(AppContext appContext) {
		super(appContext);
	}
	
	public void setUserCategoryList(List<UserCategory> userCategoryList) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.setUserCategoryList(userCategoryList);
	}

	public void setUserDataList(List<UserData> userDataList) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.setUserDataList(userDataList);
	}

	public void setUserCategoryMemberList(List<UserCategoryMember> userCategoryMemberList) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.setUserCategoryMemberList(userCategoryMemberList);
	}
	
	public void setUserCategoryWithUserList(List<UserCategory> userCategoryList, List<UserData> userDataList, List<UserCategoryMember> userCategoryMemberList) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.setUserCategoryWithUserList(userCategoryList, userDataList, userCategoryMemberList);
	}


//	public void setUserHeadList(List<UserHead> headList) {
//		UserListManage ulm = this.appContext.getManage(UserListManage.class);
//		ulm.setUserHeadList(headList);
//	}
	
	
	public void updateUserStatus(String userId,String status) {
		UserDataBox ub=appContext.getBox(UserDataBox.class);
		UserData userData = ub.getUserData(userId);
		if (null != userData) {
			
			userData.setStatus(status);
			UserListManager ulm = this.appContext.getManager(UserListManager.class);
			ulm.updateUserData(userData);
			
			if (UserData.status_online.equals(status)) {// 如果用户是上线信息，那么播放好友上线提醒声音
				PromptManager pm = this.appContext.getManager(PromptManager.class);
				pm.playSound(SoundHandler.sound_type_status);
			}
		}
	}
	
	/***
	 * 更新用户信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userData
	 */
	public void updateUserData(UserData userData) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.updateUserData(userData);
	}

	public void updateUserData(String userId) {
		DataBackAction dataBackAction = new DataBackActionAdapter() {
			@Back
			public void back(@Define("userData") UserData userData) {
				if (null != userData) {
					updateUserData(userData);
				}
			}
		};
		UserSender uh = this.appContext.getSender(UserSender.class);
		uh.getUserDataById(userId, dataBackAction);
	}
	
	public void updateRemarkName(String userId,String remarkName) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.updateRemarkName(userId, remarkName);
	}
	

	public void updateUserCategory(UserCategory userCategory) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.addOrUpdateUserCategoryInfo(userCategory);
	}

	
	public void addUserCategory(UserCategory userCategory) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.addOrUpdateUserCategory(userCategory);
	}
	
	public void deleteUser(String userId) {
		UserListManager ulm = this.appContext.getManager(UserListManager.class);
		ulm.deleteUser(userId);
	}
}
