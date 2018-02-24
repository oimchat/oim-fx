package com.im.server.general.business.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.business.push.GroupPush;
import com.im.server.general.common.bean.Group;
import com.im.server.general.common.bean.GroupCategory;
import com.im.server.general.common.bean.GroupCategoryMember;
import com.im.server.general.common.bean.GroupHead;
import com.im.server.general.common.bean.GroupMember;
import com.im.server.general.common.service.GroupHeadService;
import com.im.server.general.common.service.GroupService;
import com.im.server.general.common.service.UserService;
import com.only.common.result.Info;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.only.query.page.DefaultPage;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.query.GroupQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@Component
@ActionMapping(value = "1.201")
public class GroupAction {

	@Resource
	private GroupService groupService;
	@Resource
	private UserService userService;
	@Resource
	private GroupHeadService groupHeadService;
	@Resource
	GroupPush groupPush;

	@MethodMapping(value = "1.1.0001")
	public ResultMessage getGrouCategoryList(SocketSession socketSession) {
		ResultMessage message = new ResultMessage();
		String userId = socketSession.getKey();
		List<GroupCategory> groupCategoryList = groupService.getGroupCategoryListByUserId(userId);
		message.put("groupCategoryList", groupCategoryList);
		return message;
	}

	@MethodMapping(value = "1.1.0002")
	public ResultMessage getGroupCategoryMemberGroupList(SocketSession socketSession) {
		ResultMessage message = new ResultMessage();
		String userId = socketSession.getKey();
		List<Group> groupList = groupService.getGroupCategoryMemberGroupListByUserId(userId);
		message.put("groupList", groupList);
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public ResultMessage getGroupCategoryMemberList(SocketSession socketSession) {
		ResultMessage message = new ResultMessage();
		String userId = socketSession.getKey();
		List<GroupCategoryMember> groupCategoryMemberList = groupService.getGroupCategoryMemberListByUserId(userId);
		message.put("groupCategoryMemberList", groupCategoryMemberList);
		return message;
	}

	@MethodMapping(value = "1.1.0004")
	public ResultMessage getGroupCategoryWithGroupList(SocketSession socketSession) {
		ResultMessage message = new ResultMessage();
		String userId = socketSession.getKey();
		List<GroupCategory> groupCategoryList = groupService.getGroupCategoryListByUserId(userId);
		List<GroupCategoryMember> groupCategoryMemberList = groupService.getGroupCategoryMemberListByUserId(userId);
		List<Group> groupList = groupService.getGroupCategoryMemberGroupListByUserId(userId);

		message.put("groupCategoryMemberList", groupCategoryMemberList);
		message.put("groupCategoryList", groupCategoryList);
		message.put("groupList", groupList);
		return message;
	}

	/**
	 * 
	 * @param groupMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0005")
	public ResultMessage getGroupById(SocketSession socketSession,
			@Define("groupId") String groupId) {
		ResultMessage message = new ResultMessage();
		Group group = groupService.getGroupById(groupId);
		GroupHead groupHead=groupHeadService.getGroupHeadByGroupId(groupId);
		message.put("group", group);
		message.put("groupHead", groupHead);
		return message;
	}

	/**
	 * 条件查询用户
	 * 
	 * @param groupMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0006")
	public ResultMessage queryGroupDataList(
			@Define("groupQuery") GroupQuery groupQuery,
			@Define("page") PageData page) {
		ResultMessage message = new ResultMessage();
		DefaultPage defaultPage = new DefaultPage();
		defaultPage.setPageNumber(page.getPageNumber());
		defaultPage.setPageSize(page.getPageSize());

		List<Group> groupList = groupService.queryGroupList(groupQuery, defaultPage);

		List<String> ids=new ArrayList<String>();
		
		for(Group d:groupList){
			ids.add(d.getId());
		}
		List<GroupHead> groupHeadList=groupHeadService.getGroupHeadListByGroupIds(ids);
		
		page.setTotalCount(defaultPage.getTotalCount());
		page.setTotalPage(defaultPage.getTotalPage());

		message.put("groupHeadList", groupHeadList);
		message.put("groupList", groupList);
		message.put("page", page);
		return message;
	}

	@MethodMapping(value = "1.1.0008")
	public Object addGroup(Head head, SocketSession socketSession,
			@Define("group") Group group,
			@Define("groupCategoryMember") GroupCategoryMember groupCategoryMember) {
		String userId = socketSession.getKey();
		ResultMessage message  = groupService.add(head.getKey(), userId, group, groupCategoryMember);
		return message;
	}

	@MethodMapping(value = "1.1.0009")
	public Object update(
			Head head,
			SocketSession socketSession,
			@Define("group") Group group) {
		Info info = groupService.update(group);
		List<GroupMember> list = groupService.getGroupMemberListByGroupId(group.getId());
		List<String> ids = new ArrayList<String>();
		for (GroupMember gm : list) {
			ids.add(gm.getUserId());
		}
		groupPush.pushUpdateGroup(head.getKey(), group, ids);
		ResultMessage message = new ResultMessage();
		message.setInfo(info);
		message.put("group", group);
		return message;
	}

	@MethodMapping(value = "1.1.0010")
	public ResultMessage getGroupMemberListWithUserDataList(@Define("groupId") String groupId) {
		List<GroupMember> groupMemberList = groupService.getGroupMemberListByGroupId(groupId);
		List<UserData> userDataList = userService.getGroupMemberUserDataListByGroupId(groupId);
		userService.setUserStatus(userDataList);

		ResultMessage message = new ResultMessage();
		message.put("groupMemberList", groupMemberList);
		message.put("userDataList", userDataList);
		return message;
	}

	@MethodMapping(value = "1.1.0011")
	public Object uploadHead(@Define("groupHead") GroupHead groupHead) {
		return groupHeadService.uploadHead(groupHead);
	}

	@MethodMapping(value = "1.1.0012")
	public ResultMessage getGroupHead(@Define("groupId") String groupId) {
		GroupHead groupHead = groupHeadService.getGroupHeadByGroupId(groupId);
		ResultMessage message = new ResultMessage();
		message.put("groupHead", groupHead);
		return message;
	}

	@MethodMapping(value = "1.1.0013")
	public ResultMessage getGroupHeadList(@Define("userId") String userId) {
		List<GroupHead> headList = groupHeadService.getGroupCategoryMemberGroupHeadListByUserId(userId);
		ResultMessage message = new ResultMessage();
		message.put("headList", headList);
		return message;
	}

	@MethodMapping(value = "1.1.0014")
	public ResultMessage getGroupMemberList(@Define("groupId") String groupId) {
		List<GroupMember> groupMemberList = groupService.getGroupMemberListByGroupId(groupId);
		ResultMessage message = new ResultMessage();
		message.put("groupMemberList", groupMemberList);
		return message;
	}

	@MethodMapping(value = "1.1.0015")
	public ResultMessage getGroupUserDataList(@Define("groupId") String groupId) {
		List<UserData> userDataList = userService.getGroupMemberUserDataListByGroupId(groupId);
		userService.setUserStatus(userDataList);
		ResultMessage message = new ResultMessage();
		message.put("userDataList", userDataList);
		return message;
	}

	/**
	 * 获取用户在各个群的权限
	 * 
	 * @author XiaHui
	 * @date 2017年6月8日 下午3:31:25
	 * @param socketSession
	 * @param userId
	 * @return
	 */
	@MethodMapping(value = "1.1.0016")
	public ResultMessage getUserInGroupMemberList(
			SocketSession socketSession,
			@Define("userId") String userId) {
		userId = socketSession.getKey();
		List<GroupMember> groupMemberList = groupService.getUserInGroupMemberList(userId);
		ResultMessage message = new ResultMessage();
		message.put("groupMemberList", groupMemberList);
		return message;
	}
}
