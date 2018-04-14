package com.oim.core.business.box;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * @author: XiaHui
 * @date: 2017年6月19日 下午1:48:30
 */
public class PersonalBox extends AbstractBox {

	Map<String, GroupMember> groupMemberMap = new ConcurrentHashMap<String, GroupMember>();

	private UserData userData;
//	private UserHead userHead;
	private LoginData loginData;

	public PersonalBox(AppContext appContext) {
		super(appContext);
	}

	public String getOwnerUserId() {
		UserData userData = getUserData();
		return null == userData ? "" : userData.getId();
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

//	public UserHead getUserHead() {
//		return userHead;
//	}
//
//	public void setUserHead(UserHead userHead) {
//		this.userHead = userHead;
//	}

//	public String getHeadPath() {
//		UserHead h = this.getUserHead();
//		String path = (null != h) ? h.getAbsolutePath() : null;
//		return path;
//	}
//
//	public String getDefaultHead() {
//		UserData ud = getUserData();
//		String head = (null != ud) ? ud.getHead() : "1";
//		return head;
//	}

	public LoginData getLoginData() {
		return loginData;
	}

	public void setLoginData(LoginData loginData) {
		this.loginData = loginData;
	}

	public void put(GroupMember gm) {
		groupMemberMap.put(gm.getGroupId(), gm);
	}

	public void setGroupMemberList(List<GroupMember> list) {
		groupMemberMap.clear();
		if (null != list) {
			for (GroupMember gm : list) {
				put(gm);
			}
		}
	}
	
	public GroupMember removeGroupMember(String groupId) {
		GroupMember gm=groupMemberMap.remove(groupId);
		return gm;
	}

	public String getGroupPosition(String groupId) {
		String position = "3";
		GroupMember gm = groupMemberMap.get(groupId);
		if (null != gm) {
			position = gm.getPosition();
		}
		return position;
	}

	public boolean isOwner(String groupId) {
		String position = getGroupPosition(groupId);
		boolean mark = GroupMember.position_owner.equals(position);
		return mark;
	}

	public boolean isNormal(String groupId) {
		String position = getGroupPosition(groupId);
		boolean mark = !GroupMember.position_owner.equals(position) && !GroupMember.position_admin.equals(position);
		return mark;
	}

	public boolean isAdmin(String groupId) {
		String position = getGroupPosition(groupId);
		boolean mark = GroupMember.position_admin.equals(position);
		return mark;
	}
}
