package com.oim.core.business.manager;

import java.util.List;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.HeadBox;
import com.oim.core.business.view.MainView;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;
import com.onlyxiahui.im.bean.GroupHead;

/**
 * 描述： 主界面好友列表、群列表的管理
 * 
 * @author XiaHui
 * @date 2015年4月12日 上午10:18:18
 * @version 0.0.1
 */
public class GroupListManager extends AbstractManager {

	public GroupListManager(AppContext appContext) {
		super(appContext);
		initEvent();
	}

	private void initEvent() {
	}

	public List<GroupCategory> getGroupCategoryList() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		return gb.getGroupCategoryList();
	}
	
	public void setGroupCategoryWithGroupList(List<GroupCategory> groupCategoryList, List<Group> groupList, List<GroupCategoryMember> groupCategoryMemberList) {
		setGroupCategoryList(groupCategoryList);
		setGroupList(groupList);
		setGroupCategoryMemberList(groupCategoryMemberList);
	}

	public void setGroupCategoryList(List<GroupCategory> groupCategoryList) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.clearGroupCategory();

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.clearGroupCategory();

		if (null != groupCategoryList) {
			for (GroupCategory groupCategory : groupCategoryList) {
				addOrUpdateGroupCategoryInfo(groupCategory);
			}
			updateGroupCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setGroupList(List<Group> groupList) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.setGroupList(groupList);
		if (null != groupList) {
			updateGroupCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setGroupCategoryMemberList(List<GroupCategoryMember> groupCategoryMemberList) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.clearGroupCategoryMember();
		gb.setGroupCategoryMemberList(groupCategoryMemberList);

		if (null != groupCategoryMemberList) {
			updateGroupCategoryMember();
			updateCategoryMemberCount();
		}
	}

	public void setGroupHeadList(List<GroupHead> headList) {
		HeadBox hb = appContext.getBox(HeadBox.class);
		hb.setGroupHeadList(headList);
	}

	public void addOrUpdateGroupCategory(GroupCategory groupCategory) {
		String groupCategoryId = groupCategory.getId();
		addOrUpdateGroupCategoryInfo(groupCategory);
		updateGroupCategoryMember(groupCategoryId);
		updateCategoryMemberCount(groupCategoryId);
	}

	public void addOrUpdateGroupCategoryInfo(GroupCategory groupCategory) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroupCategory(groupCategory);
		
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.addOrUpdateGroupCategory(groupCategory);
	}

	/**
	 * 重置分组成员
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:50:59
	 */
	public void updateGroupCategoryMember() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		List<GroupCategory> groupCategoryList = gb.getGroupCategoryList();
		for (GroupCategory groupCategory : groupCategoryList) {
			updateGroupCategoryMember(groupCategory.getId());
		}
	}

	/**
	 * 重置分组成员
	 * 
	 * @author XiaHui
	 * @date 2017年9月5日 上午10:50:59
	 */
	public void updateGroupCategoryMember(String groupCategoryId) {

		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.clearGroupCategoryMember(groupCategoryId);

		GroupBox gb = appContext.getBox(GroupBox.class);
		List<GroupCategoryMember> list = gb.getGroupCategoryMemberList(groupCategoryId);
		if (null != list && !list.isEmpty()) {
			for (GroupCategoryMember m : list) {
				Group group = gb.getGroup(m.getGroupId());
				if (null != group) {
					mainView.addOrUpdateGroup(groupCategoryId, group);
				}
			}
		}
	}

	/**
	 * 更新所有的好友分组的在线人数和总人数
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyGroup: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void updateCategoryMemberCount() {
		GroupBox gb = appContext.getBox(GroupBox.class);
		List<GroupCategory> groupCategoryList = gb.getGroupCategoryList();
		for (GroupCategory groupCategory : groupCategoryList) {
			updateCategoryMemberCount(groupCategory.getId());
		}
	}

	/**
	 * 更新好友分组的总数量和在线人数
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyGroup: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param groupCategoryId
	 */
	public void updateCategoryMemberCount(String groupCategoryId) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		int totalCount = gb.getGroupCategoryMemberSize(groupCategoryId);
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.updateGroupCategoryMemberCount(groupCategoryId, totalCount);
	}

	public void add(Group group, GroupCategoryMember groupCategoryMember) {
		addGroup(group);
		addGroupCategoryMember(groupCategoryMember);
	}

	public void addGroup(Group group) {
		String groupId = group.getId();
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroup(group);
		
		List<GroupCategoryMember> list=gb.getGroupInCategoryMemberListByGroupId(groupId);
		if(null!=list){
			for(GroupCategoryMember ucm:list){
				if (null != ucm) {
					String groupCategoryId = ucm.getGroupCategoryId();
					MainView mainView = this.appContext.getSingleView(MainView.class);
					mainView.addOrUpdateGroup(groupCategoryId, group);
					updateCategoryMemberCount(groupCategoryId);
				}
			}
		}
	}

	/***
	 * 插入好友分组信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyGroup: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param groupCategoryMember
	 */
	public void addGroupCategoryMember(GroupCategoryMember groupCategoryMember) {
		GroupBox gb = appContext.getBox(GroupBox.class);
		gb.putGroupCategoryMember(groupCategoryMember);

		String groupId = groupCategoryMember.getGroupId();
		String groupCategoryId = groupCategoryMember.getGroupCategoryId();

		Group group = gb.getGroup(groupId);
		if (null != group) {
			MainView mainView = this.appContext.getSingleView(MainView.class);
			mainView.addOrUpdateGroup(groupCategoryId, group);
			updateCategoryMemberCount(groupCategoryId);
		}
	}

	/**
	 * 执行用户信息更新操作
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyGroup: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 */
	public void updateGroup(Group group) {
		if (null != group) {
			GroupBox gb = appContext.getBox(GroupBox.class);
			gb.putGroup(group);
			
			String groupId = group.getId();
			
			List<GroupCategoryMember> list=gb.getGroupInCategoryMemberListByGroupId(groupId);
			if(null!=list){
				for(GroupCategoryMember ucm:list){
					if (null != ucm) {
						String groupCategoryId = ucm.getGroupCategoryId();
						MainView mainView = this.appContext.getSingleView(MainView.class);
						mainView.addOrUpdateGroup(groupCategoryId, group);
						updateCategoryMemberCount(groupCategoryId);
					}
				}
			}
		}
	}

	public void refreshGroupHead() {
		MainView mainView = this.appContext.getSingleView(MainView.class);
		mainView.refreshGroupHead();
	}

}
