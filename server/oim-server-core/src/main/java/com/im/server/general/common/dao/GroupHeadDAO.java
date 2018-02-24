package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.GroupHead;
import com.only.query.hibernate.QueryWrapper;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月4日 下午9:48:59
 * @version 0.0.1
 */
@Repository
public class GroupHeadDAO extends BaseDAO {

	/**
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 下午5:02:01
	 * @param groupId
	 * @param headId
	 * @param type
	 * @return
	 */
	public GroupHead get(String groupId, String headId, String type){
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_group_head where groupId=:groupId");
		sb.append(" and headId=:headId ");
		sb.append(" and type=:type ");
		sb.append(" desc limit 0,1 ");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("groupId", groupId);
		queryWrapper.addParameter("headId", headId);
		queryWrapper.addParameter("type", type);
		GroupHead bean = this.readDAO.queryUniqueResult(sb.toString(), queryWrapper, GroupHead.class, null);
		return bean;
	}
	
	/**
	 * 获取群的头像
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年6月6日 上午11:55:23
	 * @update: XiaHui
	 * @updateDate: 2017年6月6日 上午11:55:23
	 */
	public GroupHead getLastByGroupId(String groupId){
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_group_head where groupId=:groupId");
		sb.append(" order by createTime desc limit 0,1");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("groupId", groupId);
		GroupHead bean = this.readDAO.queryUniqueResult(sb.toString(), queryWrapper, GroupHead.class, null);
		return bean;
	}
	
	/**
	 * 获取所在群的头像
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年6月6日 上午11:55:36
	 * @update: XiaHui
	 * @updateDate: 2017年6月6日 上午11:55:36
	 */
	
	public List<GroupHead> getGroupCategoryMemberGroupHeadListByUserId(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select gh.* from ");
		sql.append(" im_group_head ");
		sql.append(" gh where gh.groupId in ");
		sql.append("(select gcm.groupId from ");
		sql.append(" im_group_category_member ");
		sql.append(" gcm where userId=:userId ");
		sql.append(")");
		sql.append(" and not exists ( select 1 from im_group_head b where b.groupId = gh.groupId and b.createTime > gh.createTime )");
		
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<GroupHead> list = this.readDAO.queryList(sql.toString(), queryWrapper, GroupHead.class, null);
		return list;
	}
	
	public List<GroupHead> getGroupHeadListByGroupIds(List<String> ids) {
		
		if (null == ids || ids.isEmpty()) {
			return new ArrayList<GroupHead>();
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("select gh.* from ");
		sql.append(" im_group_head ");
		sql.append(" gh where gh.groupId in ");
		sql.append("( :ids )");
		sql.append(" and not exists ( select 1 from im_group_head b where b.groupId = gh.groupId and b.createTime > gh.createTime )");
		
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("ids", ids);
		List<GroupHead> list = this.readDAO.queryList(sql.toString(), queryWrapper, GroupHead.class, null);
		return list;
	}
}
