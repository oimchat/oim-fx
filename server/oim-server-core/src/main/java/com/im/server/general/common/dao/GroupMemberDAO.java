package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.GroupMember;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月15日 上午11:01:42
 * @version 0.0.1
 */
@Repository
public class GroupMemberDAO extends BaseDAO {

	/**
	 * 根据群id获取群成员
	 * 
	 * @param groupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupMember> getListByGroupId(String groupId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select gm from ");
		hql.append(GroupMember.class.getName());
		hql.append(" gm where gm.groupId= '");
		hql.append(groupId);
		hql.append("'");
		List<GroupMember> list = (List<GroupMember>) this.find(hql.toString());
		return list;
	}

	/**
	 * 获取用户在群中的权限
	 * 
	 * @author XiaHui
	 * @date 2017年6月8日 下午3:29:42
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupMember> getListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select gm from ");
		hql.append(GroupMember.class.getName());
		hql.append(" gm where gm.userId= '");
		hql.append(userId);
		hql.append("'");
		List<GroupMember> list = (List<GroupMember>) this.find(hql.toString());
		return list;
	}
}
