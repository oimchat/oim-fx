package com.oim.core.business.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupCategory;
import com.onlyxiahui.im.bean.GroupCategoryMember;

/**
 * @author: XiaHui
 * @date: 2016年9月28日 下午3:18:16
 */
public class GroupBox extends AbstractBox {

	public GroupBox(AppContext appContext) {
		super(appContext);
	}

	private Map<String, Group> allGroupMap = new ConcurrentHashMap<String, Group>();
	private Map<String, GroupCategory> allGroupCategoryMap = new ConcurrentHashMap<String, GroupCategory>();

	private Map<String, Map<String, GroupCategoryMember>> categoryMemberListMap = new ConcurrentHashMap<String, Map<String, GroupCategoryMember>>();
	private Map<String, Map<String, GroupCategoryMember>> groupInCategoryMemberMap = new ConcurrentHashMap<String, Map<String, GroupCategoryMember>>();

	public void putGroup(Group group) {
		String groupId = group.getId();
		groupId = groupId == null ? "" : groupId;
		allGroupMap.put(groupId, group);
	}

	public void putGroupCategory(GroupCategory groupCategory) {
		String categoryId = groupCategory.getId();
		categoryId = categoryId == null ? "" : categoryId;
		allGroupCategoryMap.put(groupCategory.getId(), groupCategory);
	}

	/**
	 * 存放分组成员
	 * 
	 * @author: XiaHui
	 * @param groupCategoryMember
	 * @createDate: 2017年8月21日 下午5:34:23
	 * @update: XiaHui
	 * @updateDate: 2017年8月21日 下午5:34:23
	 */
	public void putGroupCategoryMember(GroupCategoryMember groupCategoryMember) {

		String groupId = groupCategoryMember.getGroupId();
		String groupCategoryId = groupCategoryMember.getGroupCategoryId();

		Map<String, GroupCategoryMember> groupCategoryMemberMap = getGroupCategoryMemberMap(groupCategoryId);
		groupCategoryMemberMap.put(groupId, groupCategoryMember);

		Map<String, GroupCategoryMember> groupInCategoryMap = getGroupInCategoryMemberMapByGroupId(groupId);
		groupInCategoryMap.put(groupCategoryId, groupCategoryMember);
	}

	public void clearGroup() {
		allGroupMap.clear();
	}

	public void clearGroupCategory() {
		allGroupCategoryMap.clear();
	}

	public void clearGroupCategoryMember() {
		categoryMemberListMap.clear();
		groupInCategoryMemberMap.clear();
	}

	public void setGroupCategoryList(List<GroupCategory> groupCategoryList) {
		if (null != groupCategoryList) {
			for (GroupCategory groupCategory : groupCategoryList) {
				this.putGroupCategory(groupCategory);
			}
		}
	}

	public void setGroupList(List<Group> groupList) {
		if (null != groupList) {
			for (Group group : groupList) {
				this.putGroup(group);
			}
		}
	}

	public void setGroupCategoryMemberList(List<GroupCategoryMember> groupCategoryMemberList) {
		if (null != groupCategoryMemberList) {
			for (GroupCategoryMember groupCategoryMember : groupCategoryMemberList) {
				this.putGroupCategoryMember(groupCategoryMember);
			}
		}
	}

	public List<GroupCategoryMember> removeGroupCategoryMemberList(String groupId) {
		Map<String, GroupCategoryMember> map = groupInCategoryMemberMap.remove(groupId);
		List<GroupCategoryMember> list = new ArrayList<GroupCategoryMember>();
		if (null != map) {
			list.addAll(map.values());
			for (GroupCategoryMember groupCategoryMember : list) {
				if (null != groupCategoryMember) {
					String categoryId = groupCategoryMember.getGroupCategoryId();
					Map<String, GroupCategoryMember> groupCategoryMemberMap = getGroupCategoryMemberMap(categoryId);
					groupCategoryMemberMap.remove(groupId);
				}
			}
		}
		return list;
	}

	public GroupCategoryMember removeGroupCategoryMember(String groupCategoryId, String groupId) {
		Map<String, GroupCategoryMember> groupCategoryMemberMap = getGroupCategoryMemberMap(groupCategoryId);
		GroupCategoryMember groupCategoryMember = groupCategoryMemberMap.remove(groupId);
		Map<String, GroupCategoryMember> map = groupInCategoryMemberMap.get(groupId);
		if (null != map) {
			map.remove(groupCategoryId);
			if (map.isEmpty()) {
				groupInCategoryMemberMap.remove(groupId);
			}
		}
		return groupCategoryMember;
	}

	public List<GroupCategoryMember> getGroupCategoryMemberList(String groupCategoryId) {
		Map<String, GroupCategoryMember> groupCategoryMemberMap = getGroupCategoryMemberMap(groupCategoryId);
		List<GroupCategoryMember> list = new ArrayList<GroupCategoryMember>();
		list.addAll(groupCategoryMemberMap.values());
		return list;
	}

	public Map<String, GroupCategoryMember> getGroupCategoryMemberMap(String groupCategoryId) {
		Map<String, GroupCategoryMember> groupCategoryMemberList = categoryMemberListMap.get(groupCategoryId);
		if (null == groupCategoryMemberList) {
			groupCategoryMemberList = new HashMap<String, GroupCategoryMember>();
			categoryMemberListMap.put(groupCategoryId, groupCategoryMemberList);
		}
		return groupCategoryMemberList;
	}

	public int getGroupCategoryMemberSize(String groupCategoryId) {
		Map<String, GroupCategoryMember> groupCategoryMemberList = categoryMemberListMap.get(groupCategoryId);
		return null == groupCategoryMemberList ? 0 : groupCategoryMemberList.size();
	}

	public GroupCategory getGroupCategory(String id) {
		return allGroupCategoryMap.get(id);
	}

	public List<GroupCategory> getGroupCategoryList() {
		return new ArrayList<GroupCategory>(allGroupCategoryMap.values());
	}

	public List<GroupCategoryMember> getGroupInCategoryMemberListByGroupId(String groupId) {
		List<GroupCategoryMember> list = new ArrayList<GroupCategoryMember>();
		Map<String, GroupCategoryMember> map = groupInCategoryMemberMap.get(groupId);
		if (null != map) {
			list.addAll(map.values());
		}
		return list;
	}

	public Map<String, GroupCategoryMember> getGroupInCategoryMemberMapByGroupId(String groupId) {
		Map<String, GroupCategoryMember> map = groupInCategoryMemberMap.get(groupId);
		if (null == map) {
			map = new HashMap<String, GroupCategoryMember>();
			groupInCategoryMemberMap.put(groupId, map);
		}
		return map;
	}

	public Group getGroup(String id) {
		return allGroupMap.get(id);
	}

	public boolean hasGroup(String groupId) {
		return allGroupMap.containsKey(groupId);
	}

	private List<Group> getGroupList() {
		return new ArrayList<Group>(allGroupMap.values());
	}

	public List<Group> findGroupList(String text) {
		List<Group> list = new ArrayList<Group>();
		List<Group> allList = getGroupList();
		// int allSize = allList.size();
		int size = 0;
		for (Group g : allList) {

			String name = g.getName();
			String number = g.getNumber() + "";

			boolean mark = false;

			if (null != name && !mark) {
				mark = (name.indexOf(text) != -1);
			}
			if (null != number && !mark) {
				mark = (number.indexOf(text) != -1);
			}
			if (mark) {
				list.add(g);
				size++;
			}

			if (size > 20) {
				break;
			}
		}
		return list;
	}

}
