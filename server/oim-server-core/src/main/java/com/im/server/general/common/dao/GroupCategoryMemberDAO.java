package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.GroupCategoryMember;
import com.only.query.hibernate.QueryWrapper;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月15日 上午11:01:42
 * @version 0.0.1
 */
@Repository
public class GroupCategoryMemberDAO extends BaseDAO {

	/**
	 * 根据用户id获取所有群和群分组关联的相关信息
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupCategoryMember> getListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select gcm from ");
		hql.append(GroupCategoryMember.class.getName());
		hql.append(" gcm where gcm.userId= '");
		hql.append(userId);
		hql.append("'");
		List<GroupCategoryMember> list = (List<GroupCategoryMember>) this.find(hql.toString());
		return list;
	}

	/**
	 * 移动群到其它分组
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @param groupId
	 * @param groupCategoryId
	 * @return
	 * @createDate: 2017年9月4日 下午4:28:13
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午4:28:13
	 */
	public int updateGroupCategoryId(String userId, String groupId, String groupCategoryId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("userId", userId);
		queryWrapper.put("groupId", groupId);
		queryWrapper.put("groupCategoryId", groupCategoryId);

		StringBuilder sql = new StringBuilder();
		sql.append(" update im_group_category_member set ");
		sql.append(" groupCategoryId=:groupCategoryId ");
		sql.append(" where ");
		sql.append(" userId=:userId ");
		sql.append(" and groupId=:groupId ");
		int count = this.executeSQL(sql.toString(), queryWrapper);
		return count;
	}

	/**
	 * 退出群
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @param groupId
	 * @return
	 * @createDate: 2017年9月4日 下午4:28:29
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午4:28:29
	 */
	public int deleteGroupCategoryMember(String userId, String groupId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("userId", userId);
		queryWrapper.put("groupId", groupId);

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from im_group_category_member ");
		sql.append(" where ");
		sql.append(" userId=:userId ");
		sql.append(" and groupId=:groupId ");
		int count = this.executeSQL(sql.toString(), queryWrapper);
		return count;
	}
}
