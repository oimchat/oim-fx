package com.oim.core.business.service;

import java.util.List;

import com.oim.core.business.box.GroupBox;
import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.manager.GroupLastManager;
import com.oim.core.business.manager.GroupListManager;
import com.oim.core.business.sender.GroupCategorySender;
import com.oim.core.business.sender.GroupSender;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;
import com.onlyxiahui.im.bean.GroupMember;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 17:03:15
 */
public class GroupService extends AbstractService {

	public GroupService(AppContext appContext) {
		super(appContext);
	}

	public void setGroupCategoryList(List<GroupCategory> groupCategoryList) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.setGroupCategoryList(groupCategoryList);
	}

	public void setGroupList(List<Group> groupList) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.setGroupList(groupList);
	}

	public void setGroupCategoryMemberList(List<GroupCategoryMember> groupCategoryMemberList) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.setGroupCategoryMemberList(groupCategoryMemberList);
	}

	public void setGroupCategoryWithGroupList(List<GroupCategory> groupCategoryList, List<Group> groupList, List<GroupCategoryMember> groupCategoryMemberList) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.setGroupCategoryWithGroupList(groupCategoryList, groupList, groupCategoryMemberList);
	}

	// public void setGroupHeadList(List<GroupHead> headList) {
	// GroupListManage groupListManage =
	// this.appContext.getManage(GroupListManage.class);
	// groupListManage.setGroupHeadList(headList);
	// }

	/***
	 * 更新用户信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyGroup: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param group
	 */
	public void updateGroup(Group group) {
		GroupLastManager lastManage = this.appContext.getManager(GroupLastManager.class);
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.updateGroup(group);
		lastManage.addOrUpdateLastGroup(group);
	}

	public void updateGroup(String groupId) {
		DataBackAction dataBackAction = new DataBackActionAdapter() {
			@Back
			public void back(@Define("group") Group group) {
				if (null != group) {
					updateGroup(group);
				}
			}
		};
		GroupSender uh = this.appContext.getSender(GroupSender.class);
		uh.getGroupById(groupId, dataBackAction);
	}

	public void updateGroupCategory(GroupCategory groupCategory) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.addOrUpdateGroupCategoryInfo(groupCategory);
	}

	public void addGroupCategory(GroupCategory groupCategory) {
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.addOrUpdateGroupCategory(groupCategory);
	}

	public void add(Group group, GroupCategoryMember groupCategoryMember, GroupMember groupMember) {
		GroupBox gb=appContext.getBox(GroupBox.class);
		
		String groupCategoryId=groupCategoryMember.getGroupCategoryId();
		
		if(gb.getGroupCategory(groupCategoryId)==null) {
			GroupCategorySender gcs=appContext.getSender(GroupCategorySender.class);
			gcs.getGroupCategory(groupCategoryId);
		}
		
		PersonalBox pb=appContext.getBox(PersonalBox.class);
		pb.put(groupMember);
		
		GroupListManager groupListManage = this.appContext.getManager(GroupListManager.class);
		groupListManage.add(group, groupCategoryMember);
	}
}
