package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.UserHead;
import com.only.query.hibernate.QueryWrapper;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月4日 下午9:48:59
 * @version 0.0.1
 */
@Repository
public class UserHeadDAO extends BaseDAO {

	/**
	 * 
	 * @author XiaHui
	 * @date 2017年6月18日 下午5:00:12
	 * @param userId
	 * @param headId
	 * @param type
	 * @return
	 */
	public UserHead get(String userId, String headId, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user_head where userId=:userId ");
		sb.append(" and headId=:headId ");
		sb.append(" and type=:type ");
		sb.append(" desc limit 0,1 ");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		queryWrapper.addParameter("headId", headId);
		queryWrapper.addParameter("type", type);
		UserHead bean = this.readDAO.queryUniqueResult(sb.toString(), queryWrapper, UserHead.class, null);
		return bean;
	}

	/**
	 * 获取用户的头像
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年6月6日 上午11:55:23
	 * @update: XiaHui
	 * @updateDate: 2017年6月6日 上午11:55:23
	 */
	public UserHead getLastByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user_head where userId=:userId");
		sb.append(" order by createTime desc limit 0,1");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		UserHead bean = this.readDAO.queryUniqueResult(sb.toString(), queryWrapper, UserHead.class, null);
		return bean;
	}

	/**
	 * 获取好友的头像
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年6月6日 上午11:55:36
	 * @update: XiaHui
	 * @updateDate: 2017年6月6日 上午11:55:36
	 */
	public List<UserHead> getUserCategoryMemberUserHeadListByUserId(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select uh.* from ");
		sql.append(" im_user_head ");
		sql.append(" uh where uh.userId in ");
		sql.append("(select ccm.memberUserId from ");
		sql.append(" im_user_category_member ");
		sql.append(" ccm where ownUserId=:userId ");
		sql.append(" )");
		sql.append(" and not exists ( select 1 from im_user_head b where b.userId = uh.userId and b.createTime > uh.createTime )");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<UserHead> userList = this.readDAO.queryList(sql.toString(), queryWrapper, UserHead.class, null);
		return userList;
	}

	public List<UserHead> getUserHeadListByUserIds(List<String> ids) {
		if (null == ids || ids.isEmpty()) {
			return new ArrayList<UserHead>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select uh.* from ");
		sql.append(" im_user_head ");
		sql.append(" uh where uh.userId in ");
		sql.append("( :ids )");
		sql.append(" and not exists ( select 1 from im_user_head b where b.userId = uh.userId and b.createTime > uh.createTime )");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("ids", ids);
		List<UserHead> userList = this.readDAO.queryList(sql.toString(), queryWrapper, UserHead.class, null);
		return userList;
	}
}
