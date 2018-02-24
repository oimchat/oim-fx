package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.server.general.business.push.GroupPush;
import com.im.server.general.common.bean.Group;
import com.im.server.general.common.bean.GroupCategory;
import com.im.server.general.common.bean.GroupCategoryMember;
import com.im.server.general.common.bean.GroupMember;
import com.im.server.general.common.bean.GroupNumber;
import com.im.server.general.common.dao.GroupCategoryDAO;
import com.im.server.general.common.dao.GroupCategoryMemberDAO;
import com.im.server.general.common.dao.GroupDAO;
import com.im.server.general.common.dao.GroupMemberDAO;
import com.im.server.general.common.dao.NumberDAO;
import com.only.common.result.Info;
import com.only.common.util.OnlyDateUtil;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.query.GroupQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年3月31日 上午11:45:15 version 0.0.1
 */
@Service
@Transactional
public class GroupService {

	@Resource
	private GroupDAO groupDAO;
	@Resource
	private GroupMemberDAO groupMemberDAO;
	@Resource
	private GroupCategoryDAO groupCategoryDAO;
	@Resource
	private GroupCategoryMemberDAO groupCategoryMemberDAO;
	@Resource
	private NumberDAO numberDAO;
	@Resource
	private GroupPush groupPush;

	public Group getGroupById(String id) {
		Group group = groupDAO.get(Group.class, id);
		return group;
	}

	public Group getGroupByNumber(int number) {
		Group group = groupDAO.getGroupByNumber(number);
		return group;
	}

	public List<GroupCategory> getGroupCategoryListByUserId(String userId) {
		List<GroupCategory> list = groupCategoryDAO.getListByUserId(userId);
		return list;
	}

	public List<GroupCategoryMember> getGroupCategoryMemberListByUserId(String userId) {
		List<GroupCategoryMember> list = groupCategoryMemberDAO.getListByUserId(userId);
		return list;
	}

	public List<Group> getGroupCategoryMemberGroupListByUserId(String userId) {
		List<Group> list = groupDAO.getGroupCategoryMemberGroupListByUserId(userId);
		return list;
	}

	public List<GroupMember> getGroupMemberListByGroupId(String groupId) {
		List<GroupMember> list = groupMemberDAO.getListByGroupId(groupId);
		return list;
	}

	public List<Group> queryGroupList(GroupQuery groupQuery, QueryPage queryPage) {
		List<Group> groupList = groupDAO.queryGroupList(groupQuery, queryPage);
		return groupList;
	}

	public ResultMessage add(String key, String userId, Group group, GroupCategoryMember groupCategoryMember) {

		GroupNumber groupNumber = new GroupNumber();// 生成数子账号
		groupNumber.setCreateTime(OnlyDateUtil.getNowDate());

		numberDAO.save(groupNumber);

		group.setNumber(groupNumber.getId());
		group.setCreateTime(OnlyDateUtil.getNowDate());
		if (null == group.getHead() || "".equals(group.getHead())) {
			int i = new Random().nextInt(3);
			i = i + 1;
			group.setHead(i + "");
		}
		groupDAO.save(group);

		if (null == groupCategoryMember) {
			groupCategoryMember = new GroupCategoryMember();
		}
		groupCategoryMember.setUserId(userId);
		groupCategoryMember.setGroupId(group.getId());
		if (null == groupCategoryMember.getGroupCategoryId() || "".equals(groupCategoryMember.getGroupCategoryId())) {
			GroupCategory groupCategory = getOrAddDefaultGroupCategory(userId);
			groupCategoryMember.setGroupCategoryId(groupCategory.getId());
		}

		groupCategoryMemberDAO.save(groupCategoryMember);

		GroupMember groupMember = new GroupMember();
		groupMember.setUserId(userId);
		groupMember.setGroupId(group.getId());
		groupMember.setPosition(GroupMember.position_owner);
		groupMemberDAO.save(groupMember);

		ResultMessage message = new ResultMessage();
		message.put("group", group);
		message.put("groupCategoryMember", groupCategoryMember);
		message.put("groupMember", groupMember);
		
		List<String> userIdList = new ArrayList<String>();
		userIdList.add(userId);
		groupPush.addGroup(key, group, groupCategoryMember, groupMember, userIdList);
		return message;
	}

	public Info update(Group group) {
		Info info = new Info();
		groupDAO.update(group);
		return info;
	}

	private GroupCategory getOrAddDefaultGroupCategory(String userId) {
		GroupCategory groupCategory = groupCategoryDAO.getDefaultGroupCategory(userId);
		if (null == groupCategory) {
			groupCategory = new GroupCategory();// 生成默认分组信息
			groupCategory.setUserId(userId);
			groupCategory.setName("我的聊天群");
			groupCategory.setSort(GroupCategory.sort_default);
			groupCategoryDAO.save(groupCategory);
		}
		return groupCategory;
	}

	
	/**
	 * 获取用户在各个群中的权限
	 * @author XiaHui
	 * @date 2017年6月8日 下午3:32:39
	 * @param userId
	 * @return
	 */
	public List<GroupMember> getUserInGroupMemberList(String userId) {
		List<GroupMember> list = groupMemberDAO.getListByUserId(userId);
		return list;
	}
}
