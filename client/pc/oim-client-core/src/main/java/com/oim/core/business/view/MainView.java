package com.oim.core.business.view;

import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.UserCategory;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月10日 下午10:20:49
 * @version 0.0.1
 */

public interface MainView extends View {

	public void setStatus(String status);

	public void setPersonalData(UserData user);

	///////////////////////////////////////////////////////////////////////////
	/**
	 * 清除好友分组
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:36:24
	 */
	public void clearUserCategory();

	/**
	 * 新增或者修改分组信息
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:36:50
	 * @param userCategory
	 */
	public void addOrUpdateUserCategory(UserCategory userCategory);

	/**
	 * 新增或者修改用户信息
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:37:06
	 * @param userCategoryId
	 * @param userData
	 */
	public void addOrUpdateUser(String userCategoryId, UserData userData);

	/**
	 * 移除用户
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:38:01
	 * @param userId
	 */
	public void removeUserCategoryMember(String userCategoryId, String userId);

	public void clearUserCategoryMember(String userCategoryId);

	public void updateUserCategoryMemberCount(String userCategoryId, int totalCount, int onlineCount);

	///////////////////////////////////////////////////////////////////////////////

	public void clearGroupCategory();

	public void addOrUpdateGroupCategory(GroupCategory groupCategory);

	public void addOrUpdateGroup(String groupCategoryId, Group group);

	public void removeGroupCategoryMember(String groupCategoryId, String groupId);

	public void clearGroupCategoryMember(String groupCategoryId);
	
	public void updateGroupCategoryMemberCount(String groupCategoryId, int totalCount);

	/////////////////////////////////////////////////////////////////////////////////

	public void addOrUpdateLastUser(UserData userData);

	public void showUserHeadPulse(String userId, boolean pulse);

	public void setLastUserItemRed(String userId, boolean red, int count);

	public void updateLastUserChatInfo(String userId, String text, String time);

	///////////////////////////
	/********************************************/
	public void addOrUpdateLastGroup(Group group);

	public void showGroupHeadPulse(String groupId, boolean pulse);

	public void setLastGroupItemRed(String groupId, boolean red, int count);

	public void updateLastGroupChatInfo(String groupId, String text, String time);

	/********************************************/
	public void setUserHead(UserHead userHead);

	public void refreshPersonalHead();

	public void refreshUserHead();

	public void refreshGroupHead();

	public void shwoDifferentLogin();

}
