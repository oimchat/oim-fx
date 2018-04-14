package com.oim.core.business.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oim.core.common.util.AppCommonUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserCategoryMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.common.box.DataBeanBox;

/**
 * @author: XiaHui
 * @date: 2016年9月28日 下午3:18:16
 */
public class UserDataBox extends AbstractBox {

	public UserDataBox(AppContext appContext) {
		super(appContext);
	}
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

	public List<UserData> getUserDataList() {
		return box.getDataBeanList();
	}
	
	public List<UserData> findUserDataList(String text) {
		List<UserData> list = new ArrayList<UserData>();
		List<UserData> allList = getUserDataList();
		//int allSize = allList.size();
		int size = 0;
		for (UserData ud : allList) {
			String remarkName = ud.getRemarkName();
			String account = ud.getAccount();
			String email = ud.getEmail();
			String mobile = ud.getMobile();
			String name = ud.getName();
			String nickname = ud.getNickname();
			String number=ud.getNumber()+"";
			
			boolean mark = false;

			if (null != account && !mark) {
				mark = (account.indexOf(text) != -1);
			}
			if (null != email && !mark) {
				mark = (email.indexOf(text) != -1);
			}
			if (null != mobile && !mark) {
				mark = (mobile.indexOf(text) != -1);
			}
			if (null != name && !mark) {
				mark = (name.indexOf(text) != -1);
			}
			if (null != nickname && !mark) {
				mark = (nickname.indexOf(text) != -1);
			}
			if (null != remarkName && !mark) {
				mark = (remarkName.indexOf(text) != -1);
			}
			if (null != number && !mark) {
				mark = (number.indexOf(text) != -1);
			}
			if (mark) {
				list.add(ud);
				size++;
			}

			if (size > 20) {
				break;
			}
		}
		return list;
	}
}
