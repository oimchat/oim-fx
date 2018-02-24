package com.oim.core.business.box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.GroupMember;

/**
 * @author: XiaHui
 * @date: 2016年9月28日 下午3:18:16
 */
public class GroupMemberBox extends AbstractBox {
	
	private Map<String, Map<String, GroupMember>> groupMemberListMap = new ConcurrentHashMap<String, Map<String, GroupMember>>();
	
	public GroupMemberBox(AppContext appContext) {
		super(appContext);
	}
	
	public void putGroupMember(GroupMember groupMember) {
		String groupId = groupMember.getGroupId();
		Map<String, GroupMember> map = groupMemberListMap.get(groupId);
		if (map == null) {
			map = new HashMap<String, GroupMember>();
			groupMemberListMap.put(groupId, map);
		}
		map.put(groupMember.getUserId(), groupMember);
	}

	public void putGroupMemberList(String groupId, List<GroupMember> list) {
		Map<String, GroupMember> map = groupMemberListMap.get(groupId);
		if (map == null) {
			map = new HashMap<String, GroupMember>();
			groupMemberListMap.put(groupId, map);
		}
		map.clear();
		if (null != list) {
			for (GroupMember gm : list) {
				map.put(gm.getUserId(), gm);
			}
		}
	}

	public List<GroupMember> getGroupMemberList(String groupId) {
		List<GroupMember> list = new ArrayList<GroupMember>();
		Map<String, GroupMember> map = groupMemberListMap.get(groupId);
		if (map != null) {
			list.addAll(map.values());
		}
		return list;
	}

	public GroupMember getGroupMember(String groupId, String userId) {
		GroupMember gm = null;
		Map<String, GroupMember> map = groupMemberListMap.get(groupId);
		if (map != null) {
			gm = map.get(userId);
		}
		return gm;
	}

	public String getGroupPosition(String groupId, String userId) {
		String position = "3";
		GroupMember gm = getGroupMember(groupId, userId);
		if (null != gm) {
			position = gm.getPosition();
		}
		return position;
	}

	public boolean isOwner(String groupId, String userId) {
		String position = getGroupPosition(groupId, userId);
		boolean mark = GroupMember.position_owner.equals(position);
		return mark;
	}

	public boolean isNormal(String groupId, String userId) {
		String position = getGroupPosition(groupId, userId);
		boolean mark = !GroupMember.position_owner.equals(position) && !GroupMember.position_admin.equals(position);
		return mark;
	}

	public boolean isAdmin(String groupId, String userId) {
		String position = getGroupPosition(groupId, userId);
		boolean mark = GroupMember.position_admin.equals(position);
		return mark;
	}
}
