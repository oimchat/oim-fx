package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.GroupCategory;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月7日 下午8:55:57
 * @version 0.0.1
 */
@Repository
public class GroupCategoryDAO extends BaseDAO {
	/**
	 * 根据用户id，获取用户下群分组信息
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<GroupCategory> getListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select gc from ");
		hql.append(GroupCategory.class.getName());
		hql.append(" gc where gc.userId= '");
		hql.append(userId);
		hql.append("' ");
		List<GroupCategory> list = (List<GroupCategory>) this.find(hql.toString());
		return list;
	}

	/**
	 * 根据用户id，获取用户默认的群分组信息
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public GroupCategory getDefaultGroupCategory(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select gc from ");
		hql.append(GroupCategory.class.getName());
		hql.append(" gc where gc.userId= '");
		hql.append(userId);
		hql.append("' ");
		hql.append(" and gc.sort=");
		hql.append(GroupCategory.sort_default);
		List<GroupCategory> list = (List<GroupCategory>) this.find(hql.toString());
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	public long getGroupCategoryCount(String userId) {
		String sql = "select count(1) from im_group_category where userId='" + userId + "'";
		Long count = this.queryUniqueResult(sql, null, Long.class, null);
		return count == null ? 0 : count;
	}
}
