package com.oim.core.business.manager;

import java.util.List;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.HeadBox;
import com.oim.core.business.box.UserDataBox;
import com.oim.core.business.sender.GroupCategorySender;
import com.oim.core.business.view.MainView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;
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
public class ListManage extends AbstractManager {

	public ListManage(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	////// user
	public List<UserCategoryMember> getUserCategoryMemberList(String userCategoryId) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		return ub.getUserCategoryMemberList(userCategoryId);
	}

	public List<UserCategory> getUserCategoryList() {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		return ub.getUserCategoryList();
	}

	/// group

	public List<GroupCategory> getGroupCategoryList() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		return gb.getGroupCategoryList();
	}

	public List<GroupCategoryMember> getGroupCategoryMemberList(String groupCategoryId) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		return gb.getGroupCategoryMemberList(groupCategoryId);
	}

	public void setUserCategoryWithUserList(List<UserCategory> userCategoryList, List<UserData> userDataList, List<UserCategoryMember> userCategoryMemberList) {
		setUserCategoryList(userCategoryList);
		setUserDataList(userDataList);
		setUserCategoryMemberList(userCategoryMemberList);
	}

	public void setGroupCategoryWithGroupList(List<GroupCategory> groupCategoryList, List<Group> groupList, List<GroupCategoryMember> groupCategoryMemberList) {
		setGroupCategoryList(groupCategoryList);
		setGroupList(groupList);
		setGroupCategoryMemberList(groupCategoryMemberList);
	}

	public void setUserCategoryList(List<UserCategory> userCategoryList) {
		if (null != userCategoryList) {
			for (UserCategory userCategory : userCategoryList) {
				addUserCategory(userCategory);
			}
		}
	}

	public void setGroupCategoryList(List<GroupCategory> groupCategoryList) {
		if (null != groupCategoryList) {
			for (GroupCategory groupCategory : groupCategoryList) {
				addGroupCategory(groupCategory);
			}
		}
	}

	public void setUserDataList(List<UserData> userDataList) {
		if (null != userDataList) {
			for (UserData userData : userDataList) {
				addUserData(userData);
			}
		}
	}

	public void setGroupList(List<Group> groupList) {
		if (null != groupList) {
			for (Group group : groupList) {
				addGroup(group);
			}
		}
	}

	public void setUserCategoryMemberList(List<UserCategoryMember> userCategoryMemberList) {
		if (null != userCategoryMemberList) {
			for (UserCategoryMember userCategoryMember : userCategoryMemberList) {
				addUserCategoryMember(userCategoryMember);
			}
			updateCategoryMemberCount();
		}
	}

	public void setGroupCategoryMemberList(List<GroupCategoryMember> groupCategoryMemberList) {
		if (null != groupCategoryMemberList) {
			for (GroupCategoryMember groupCategoryMember : groupCategoryMemberList) {
				addGroupCategoryMember(groupCategoryMember);
			}
			updateGroupCategoryMemberCount();
		}
	}

	public void setUserHeadList(List<UserHead> headList) {
		if (null != headList) {
			for (UserHead userHead : headList) {
				addUserHead(userHead);
			}
		}
	}

	public void addUserCategory(UserCategory userCategory) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.putUserCategory(userCategory);

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateUserCategory(userCategory);
	}

	public void addGroupCategory(GroupCategory groupCategory) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroupCategory(groupCategory);
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateGroupCategory(groupCategory);
		updateGroupCategoryMemberCount(groupCategory.getId());
	}

	/**
	 * 插入用户信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userData
	 */
	public void addUserData(UserData userData) {
		UserDataBox ub = appContext.getBox(UserDataBox.class);
		ub.putUserData(userData);
	}

	public void addUserHead(UserHead userHead) {
		HeadBox hb = appContext.getBox(HeadBox.class);
		hb.putUserHead(userHead);
	}

	/**
	 * 插入群信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 */
	public void addGroup(Group group) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroup(group);
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
		MainView mainView = this.appContext.getSingleView(MainView.class);
		String userId = userCategoryMember.getMemberUserId();
		String userCategoryId = userCategoryMember.getUserCategoryId();

		UserData userData = ub.getUserData(userId);
		if (null != userData) {
			mainView.addOrUpdateUser(userCategoryId, userData);
		}
	}

	/**
	 * 插入群分组信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param groupCategoryMember
	 */
	public void addGroupCategoryMember(GroupCategoryMember groupCategoryMember) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroupCategoryMember(groupCategoryMember);

		String groupId = groupCategoryMember.getGroupId();
		String groupCategoryId = groupCategoryMember.getGroupCategoryId();

		if (null == gb.getGroupCategory(groupCategoryId)) {
			GroupCategorySender gcs = appContext.getSender(GroupCategorySender.class);
			gcs.getGroupCategory(groupCategoryId);
		} else {
			Group group = gb.getGroup(groupId);
			if (null != group) {
				MainView mainView = this.appContext.getSingleView(MainView.class);
				mainView.addOrUpdateGroup(groupCategoryId, group);
			}
		}
	}

	/**
	 * 插入一个用户到好友分组列表
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param userData
	 * @param userCategoryMember
	 */
	public void add(UserData userData, UserCategoryMember userCategoryMember) {
		addUserData(userData);
		addUserCategoryMember(userCategoryMember);
		updateCategoryMemberCount(userCategoryMember.getUserCategoryId());
	}

	/**
	 * 插入一个群到群分组列表
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 * @param groupCategoryMember
	 */
	public void add(Group group, GroupCategoryMember groupCategoryMember) {
		addGroup(group);
		addGroupCategoryMember(groupCategoryMember);
		updateGroupCategoryMemberCount(groupCategoryMember.getGroupCategoryId());
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
	 * 更新所有的群分组的数量信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void updateGroupCategoryMemberCount() {
		List<GroupCategory> list = getGroupCategoryList();
		for (GroupCategory gc : list) {
			updateGroupCategoryMemberCount(gc.getId());
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
				if (!isGray(userData.getStatus())) {
					onlineCount++;
				}
			}
		}
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateUserCategoryMemberCount(userCategoryId, totalCount, onlineCount);
	}

	/**
	 * 更新群的分组的数量
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param groupCategoryId
	 */
	public void updateGroupCategoryMemberCount(String groupCategoryId) {
		List<GroupCategoryMember> userCategoryMemberList = getGroupCategoryMemberList(groupCategoryId);
		int totalCount = userCategoryMemberList.size();
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateGroupCategoryMemberCount(groupCategoryId, totalCount);
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

			MainView mainView = this.appContext.getSingleView(MainView.class);
			mainView.addOrUpdateUser("", userData);
		}
	}

	/**
	 * 执行群信息更新操作
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 */
	public void updateGroup(Group group) {
		if (null != group) {
			GroupBox gb = appContext.getBox(GroupBox.class);
			gb.putGroup(group);
			MainView mainView = this.appContext.getSingleView(MainView.class);
			mainView.addOrUpdateGroup("", group);
		}
	}

	public void refreshUserHead() {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.refreshUserHead();
	}

	public void refreshGroupHead() {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.refreshGroupHead();
	}

	private boolean isGray(String status) {
		boolean gray = true;
		switch (status) {
		case UserData.status_online:
			gray = false;
			break;
		case UserData.status_call_me:
			gray = false;
			break;
		case UserData.status_away:
			gray = false;
			break;
		case UserData.status_busy:
			gray = false;
			break;
		case UserData.status_mute:
			gray = false;
			break;
		case UserData.status_invisible:
			gray = true;
			break;
		case UserData.status_offline:
			gray = true;
			break;
		default:
			gray = true;
			break;
		}
		return gray;
	}

}
