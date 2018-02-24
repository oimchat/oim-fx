package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.Group;
import com.im.server.general.common.bean.GroupCategoryMember;
import com.only.query.hibernate.QueryOption;
import com.only.query.hibernate.QueryOptionType;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.util.QueryUtil;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.query.GroupQuery;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月14日 下午4:17:01
 * @version 0.0.1
 */
@Repository
public class GroupDAO extends BaseDAO {

	public Group get(String id) {
		return this.readDAO.get(Group.class, id);
	}

	public String getGroupIdByNumber(int number) {
		Group g = getGroupByNumber(number);
		return g == null ? null : g.getId();
	}

	/**
	 * 根据群号码获取群信息
	 * 
	 * @param number
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Group getGroupByNumber(int number) {
		List<Group> list = (List<Group>) this.find("from " + Group.class.getName() + " where number=" + number);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	/**
	 * 根据用户id，获取用户已经加入或者拥有的群
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Group> getGroupCategoryMemberGroupListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select g from ");
		hql.append(Group.class.getName());
		hql.append(" g where g.id in ");
		hql.append("(select gcm.groupId from ");
		hql.append(GroupCategoryMember.class.getName());
		hql.append(" gcm where userId='");
		hql.append(userId);
		hql.append("')");
		List<Group> list = (List<Group>) this.find(hql.toString());
		return list;
	}

	public List<Group> queryGroupList(GroupQuery groupQuery, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> optionList = new ArrayList<QueryOption>();
		optionList.add(new QueryOption("queryText", QueryOptionType.likeAll));// 设置查询条件为like
		QueryUtil.getQueryWrapperType(groupQuery, queryWrapper, optionList);
		List<Group> list = this.queryPageListByName("group.queryGroupList", queryWrapper, Group.class);
		return list;
	}
	
	
	
	public List<Group> getGroupList(List<String> ids) {
		if (null == ids || ids.isEmpty()) {
			return new ArrayList<Group>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_group where id in ( :ids )");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("ids", ids);
		List<Group> list = this.readDAO.queryList(sb.toString(), queryWrapper, Group.class, null);
		return list;
	}
}
