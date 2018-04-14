package com.onlyxiahui.im.common.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oim.core.common.util.AppCommonUtil;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;

/**
 * @author XiaHui
 * @date 2017-11-10 08:58:03
 */
public class UserDataBox {

	DataBeanBox<UserData, UserCategory, UserCategoryMember> box = new DataBeanBox<UserData, UserCategory, UserCategoryMember>();

	/**
	 * 存放用户
	 * 
	 * @author: XiaHui
	 * @param userData
	 * @createDate: 2017年8月21日 下午5:34:02
	 * @update: XiaHui
	 * @updateDate: 2017年8月21日 下午5:34:02
	 */
	public void putUserData(UserData userData) {
		box.putDataBean(userData);
	}

	/**
	 * 存放好友分组
	 * 
	 * @author: XiaHui
	 * @param userCategory
	 * @createDate: 2017年8月21日 下午5:34:13
	 * @update: XiaHui
	 * @updateDate: 2017年8月21日 下午5:34:13
	 */
	public void putUserCategory(UserCategory userCategory) {
		box.putCategory(userCategory);
	}

	/**
	 * 存放分组成员
	 * 
	 * @author: XiaHui
	 * @param userCategoryMember
	 * @createDate: 2017年8月21日 下午5:34:23
	 * @update: XiaHui
	 * @updateDate: 2017年8月21日 下午5:34:23
	 */
	public void putUserCategoryMember(UserCategoryMember userCategoryMember) {
		box.putCategoryMember(userCategoryMember);
	}

	public void clearUserCategory() {
		box.clearCategory();
	}

	public void clearUserCategoryMember() {
		box.clearCategoryMember();
	}

	public void setUserCategoryList(List<UserCategory> userCategoryList) {
		box.setCategoryList(userCategoryList);
	}

	public void setUserDataList(List<UserData> userDataList) {
		box.setDataBeanList(userDataList);
	}

	public void setUserCategoryMemberList(List<UserCategoryMember> userCategoryMemberList) {
		box.setCategoryMemberList(userCategoryMemberList);
	}

	public List<UserCategoryMember> removeUserCategoryMemberList(String userId) {
		return box.removeCategoryMemberList(userId);
	}

	/**
	 * 移除分组中的好友
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:01:44
	 * @param userId
	 * @return
	 */
	public UserCategoryMember removeUserCategoryMember(String userCategoryId, String userId) {
		return box.removeCategoryMember(userCategoryId, userId);
	}

	/**
	 * 获取分组成员列表
	 * 
	 * @author: XiaHui
	 * @param userCategoryId
	 * @return
	 * @createDate: 2017年8月21日 下午5:36:30
	 * @update: XiaHui
	 * @updateDate: 2017年8月21日 下午5:36:30
	 */
	public List<UserCategoryMember> getUserCategoryMemberList(String userCategoryId) {
		return box.getCategoryMemberList(userCategoryId);
	}

	public Map<String, UserCategoryMember> getUserCategoryMemberMap(String userCategoryId) {
		return box.getCategoryMemberMap(userCategoryId);
	}

	public int getUserCategoryMemberSize(String userCategoryId) {
		return box.getCategoryMemberSize(userCategoryId);
	}

	public UserCategory getUserCategory(String id) {
		return box.getCategory(id);
	}

	public List<UserCategory> getUserCategoryList() {
		return box.getCategoryList();
	}

	public List<UserCategoryMember> getUserInCategoryMemberListByUserId(String userId) {
		Map<String, UserCategoryMember> map = getUserInCategoryMemberMapByUserId(userId);
		List<UserCategoryMember> list = new ArrayList<UserCategoryMember>();
		list.addAll(map.values());
		return list;
	}

	public Map<String, UserCategoryMember> getUserInCategoryMemberMapByUserId(String userId) {
		return box.getDataBeanInCategoryMemberMapByDataBeanId(userId);
	}

	/**
	 * 是否在好友列表
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年8月18日 上午10:05:10
	 * @update: XiaHui
	 * @updateDate: 2017年8月18日 上午10:05:10
	 */
	public boolean inMemberList(String userId) {
		boolean has = box.inMemberList(userId);
		return has;
	}

	public UserData getUserData(String id) {
		UserData ud = box.getDataBean(id);
		return ud;
	}

	public String getUserStatus(String id) {
		UserData ud = getUserData(id);
		return (null == ud) ? UserData.status_offline : ud.getStatus();
	}

	public boolean isOnline(String id) {
		String status = getUserStatus(id);
		boolean mark = AppCommonUtil.isOffline(status);
		return !mark;
	}
}
