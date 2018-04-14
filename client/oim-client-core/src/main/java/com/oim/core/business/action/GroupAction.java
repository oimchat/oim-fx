package com.oim.core.business.action;

import java.util.List;

import com.oim.core.business.box.PersonalBox;
import com.oim.core.business.service.GroupMemberService;
import com.oim.core.business.service.GroupService;
import com.oim.core.business.service.HeadService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.bean.GroupMember;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 16:22:23
 */
@ActionMapping(value = "1.201")
public class GroupAction extends AbstractAction {

	public GroupAction(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 接受到服务器传来的群分组、列表等群信息
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	@MethodMapping(value = "1.1.0004")
	public void setGroupCategoryWithGroupList(
			@Define("groupCategoryList") List<GroupCategory> groupCategoryList,
			@Define("groupList") List<Group> groupList,
			@Define("groupCategoryMemberList") List<GroupCategoryMember> groupCategoryMemberList) {
		GroupService groupService = appContext.getService(GroupService.class);
		groupService.setGroupCategoryWithGroupList(groupCategoryList, groupList, groupCategoryMemberList);
	}

	@MethodMapping(value = "1.2.0008")
	public void addGroupBack(
			@Define("group") Group group,
			@Define("groupCategoryMember") GroupCategoryMember groupCategoryMember,
			@Define("groupMember") GroupMember groupMember) {

		GroupService groupService = appContext.getService(GroupService.class);
		groupService.add(group, groupCategoryMember, groupMember);
	}

	/**
	 * 接受服务器推送的群信息更新
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	@MethodMapping(value = "1.2.0009")
	public void updateGroup(@Define("group") Group group) {
		GroupService groupService = appContext.getService(GroupService.class);
		groupService.updateGroup(group);
	}

	@MethodMapping(value = "1.2.0010")
	public void updateGroupUserList(@Define("groupId") String groupId, @Define("userId") String userId) {
		GroupMemberService gms = appContext.getService(GroupMemberService.class);
		gms.updateGroupMemberList(groupId);
	}

	@MethodMapping(value = "1.1.0013")
	public void setGroupHeadList(
			@Define("headList") List<GroupHead> headList) {
		HeadService headService = appContext.getService(HeadService.class);
		headService.setGroupHeadList(headList);
	}

	@MethodMapping(value = "1.1.0016")
	public void setUserInGroupMemberList(
			@Define("groupMemberList") List<GroupMember> groupMemberList) {
		PersonalBox pb = appContext.getBox(PersonalBox.class);
		pb.setGroupMemberList(groupMemberList);
	}
}
