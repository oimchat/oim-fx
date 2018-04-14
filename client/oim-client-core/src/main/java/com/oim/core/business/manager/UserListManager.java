package com.oim.core.business.manager;

import java.util.List;

import com.oim.core.business.box.HeadBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.view.MainView;
import com.oim.core.common.util.AppCommonUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * 描述： 主界面好友列表、群列表的管理
 * 
 * @author XiaHui
 * @date 2015年4月12日 上午10:18:18
 * @version 0.0.1
 */
public class UserListManager extends AbstractManager {

	public UserListManager(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}


	public List<UserCategory> getUserCategoryList() {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		return ub.getUserCategoryList();
	}
	
	public void setUserCategoryWithUserList(List<UserCategory> userCategoryList, List<UserData> userDataList, List<UserCategoryMember> userCategoryMemberList) {
		setUserCategoryList(userCategoryList);
		setUserDataList(userDataList);
		setUserCategoryMemberList(userCategoryMemberList);
	}

	public void setUserCategoryList(List<UserCategory> userCategoryList) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.clearUserCategory();

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.clearUserCategory();

		if (null != userCategoryList) {
			for (UserCategory userCategory : userCategoryList) {
				addOrUpdateUserCategoryInfo(userCategory);
			}
			updateUserCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setUserDataList(List<UserData> userDataList) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.setUserDataList(userDataList);
		if (null != userDataList) {
			updateUserCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setUserCategoryMemberList(List<UserCategoryMember> userCategoryMemberList) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.clearUserCategoryMember();
		ub.setUserCategoryMemberList(userCategoryMemberList);

		if (null != userCategoryMemberList) {
			updateUserCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setUserHeadList(List<UserHead> headList) {
		HeadBox hb = appContext.getBox(HeadBox.class);
		hb.setUserHeadList(headList);
	}

	public void addOrUpdateUserCategory(UserCategory userCategory) {
		String userCategoryId = userCategory.getId();
		addOrUpdateUserCategoryInfo(userCategory);
		updateUserCategoryMember(userCategoryId);
		updateCategoryMemberCount(userCategoryId);
	}

	public void addOrUpdateUserCategoryInfo(UserCategory userCategory) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.putUserCategory(userCategory);

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateUserCategory(userCategory);
	}

	/**
	 * 重置分组成员
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:50:59
	 */
	public void updateUserCategoryMember() {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategory> userCategoryList = ub.getUserCategoryList();
		for (UserCategory userCategory : userCategoryList) {
			updateUserCategoryMember(userCategory.getId());
		}
	}

	/**
	 * 重置分组成员
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:50:59
	 */
	public void updateUserCategoryMember(String userCategoryId) {

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.clearUserCategoryMember(userCategoryId);

		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategoryMember> list = ub.getUserCategoryMemberList(userCategoryId);
		if (null != list && !list.isEmpty()) {
			for (UserCategoryMember m : list) {
				UserData userData = ub.getUserData(m.getMemberUserId());
				if (null != userData) {
					mainView.addOrUpdateUser(userCategoryId, userData);
				}
			}
		}
	}

	/**
	 * 更新所有的好友分组的在线人数和总人数
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void updateCategoryMemberCount() {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategory> userCategoryList = ub.getUserCategoryList();
		for (UserCategory userCategory : userCategoryList) {
			updateCategoryMemberCount(userCategory.getId());
		}
	}

	/**
	 * 更新好友分组的总数量和在线人数
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userCategoryId
	 */
	public void updateCategoryMemberCount(String userCategoryId) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategoryMember> userCategoryMemberList = ub.getUserCategoryMemberList(userCategoryId);
		int totalCount = 0;
		int onlineCount = 0;
		for (UserCategoryMember userCategoryMember : userCategoryMemberList) {
			UserData userData = ub.getUserData(userCategoryMember.getMemberUserId());
			if (null != userData) {
				totalCount++;
				if (!AppCommonUtil.isOffline(userData.getStatus())) {
					onlineCount++;
				}
			}
		}
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateUserCategoryMemberCount(userCategoryId, totalCount, onlineCount);
	}

	public void add(UserData userData, UserCategoryMember userCategoryMember) {
		addUserData(userData);
		addUserCategoryMember(userCategoryMember);
	}

	public void addUserData(UserData userData) {
		String userId = userData.getId();
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.putUserData(userData);
		List<UserCategoryMember> list = ub.getUserInCategoryMemberListByUserId(userId);
		if (null != list) {
			for (UserCategoryMember ucm : list) {
				if (null != ucm) {
					String userCategoryId = ucm.getUserCategoryId();
					MainView mainView = this.appContext.getSingleView(MainView.class);
					mainView.addOrUpdateUser(userCategoryId, userData);
					updateCategoryMemberCount(userCategoryId);
				}
			}
		}
	}

	/***
	 * 插入好友分组信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userCategoryMember
	 */
	public void addUserCategoryMember(UserCategoryMember userCategoryMember) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.putUserCategoryMember(userCategoryMember);

		String userId = userCategoryMember.getMemberUserId();
		String userCategoryId = userCategoryMember.getUserCategoryId();

		UserData userData = ub.getUserData(userId);
		if (null != userData) {
			MainView mainView = this.appContext.getSingleView(MainView.class);
			mainView.addOrUpdateUser(userCategoryId, userData);
			updateCategoryMemberCount(userCategoryId);
		}
	}

	/**
	 * 执行用户信息更新操作
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userData
	 */
	public void updateUserData(UserData userData) {
		if (null != userData) {
			UserDataBox ub = appContext.getBox(UserDataBox.class);
			ub.putUserData(userData);

			String userId = userData.getId();

			List<UserCategoryMember> list = ub.getUserInCategoryMemberListByUserId(userId);
			if (null != list) {
				for (UserCategoryMember ucm : list) {
					if (null != ucm) {
						String userCategoryId = ucm.getUserCategoryId();
						MainView mainView = this.appContext.getSingleView(MainView.class);
						mainView.addOrUpdateUser(userCategoryId, userData);
						updateCategoryMemberCount(userCategoryId);
					}
				}
			}
		}
	}

	public void updateRemarkName(String userId, String remarkName) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategoryMember> list = ub.getUserInCategoryMemberListByUserId(userId);
		if (null != list) {
			for (UserCategoryMember userCategoryMember : list) {
				if (null != userCategoryMember) {
					userCategoryMember.setRemark(remarkName);
					addUserCategoryMember(userCategoryMember);
				}
			}
		}
	}

	public void refreshUserHead() {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.refreshUserHead();
	}

	public void deleteUser(String userId) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		List<UserCategoryMember> list = ub.removeUserCategoryMemberList(userId);
		if (null != list) {
			for (UserCategoryMember userCategoryMember : list) {
				if (null != userCategoryMember) {
					MainView mainView = this.appContext.getSingleView(MainView.class);
					mainView.removeUserCategoryMember(userCategoryMember.getUserCategoryId(), userId);
				}
			}
		}
	}

}
