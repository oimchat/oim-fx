package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.User;
import com.im.server.general.common.bean.UserCategoryMember;
import com.only.query.hibernate.QueryWrapper;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月15日 上午11:01:42
 * @version 0.0.1
 */
@Repository
public class UserCategoryMemberDAO extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<UserCategoryMember> getListByUserNumber(int userNumber) {
		StringBuilder hql = new StringBuilder();
		hql.append("select ucm from ");
		hql.append(UserCategoryMember.class.getName());
		hql.append(" ucm where ucm.ownUserId= ");
		hql.append("( select u.id from ");
		hql.append(User.class.getName());
		hql.append(" u where u.number = ");
		hql.append(userNumber);
		hql.append(" )");
		List<UserCategoryMember> list = (List<UserCategoryMember>) this.find(hql.toString());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<UserCategoryMember> getListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select ucm from ");
		hql.append(UserCategoryMember.class.getName());
		hql.append(" ucm where ucm.ownUserId= '");
		hql.append(userId);
		hql.append("'");
		List<UserCategoryMember> list = (List<UserCategoryMember>) this.find(hql.toString());
		return list;
	}

	/**
	 * 获取用户在别人的好友列表的集合
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserCategoryMember> getInListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select ucm from ");
		hql.append(UserCategoryMember.class.getName());
		hql.append(" ucm where ucm.memberUserId= '");
		hql.append(userId);
		hql.append("'");
		List<UserCategoryMember> list = (List<UserCategoryMember>) this.find(hql.toString());
		return list;
	}

	/**
	 * 修改备注
	 * @author: XiaHui
	 * @param ownUserId
	 * @param memberUserId
	 * @param remark
	 * @return
	 * @createDate: 2017年9月4日 下午4:07:42
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午4:07:42
	 */
	public int updateRemark(String ownUserId, String memberUserId, String remark) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("ownUserId", ownUserId);
		queryWrapper.put("memberUserId", memberUserId);
		queryWrapper.put("remark", remark);

		StringBuilder sql = new StringBuilder();
		sql.append(" update im_user_category_member set ");
		sql.append(" remark=:remark ");
		sql.append(" where ");
		sql.append(" ownUserId=:ownUserId ");
		sql.append(" and memberUserId=:memberUserId ");
		int count = this.executeSQL(sql.toString(), queryWrapper);
		return count;
	}
	
	/**
	 * 移动用户所在分组
	 * @author: XiaHui
	 * @param ownUserId
	 * @param memberUserId
	 * @param userCategoryId
	 * @return
	 * @createDate: 2017年9月4日 下午4:11:57
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午4:11:57
	 */
	public int updateUserCategoryId(String ownUserId, String memberUserId, String userCategoryId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("ownUserId", ownUserId);
		queryWrapper.put("memberUserId", memberUserId);
		queryWrapper.put("userCategoryId", userCategoryId);

		StringBuilder sql = new StringBuilder();
		sql.append(" update im_user_category_member set ");
		sql.append(" userCategoryId=:userCategoryId ");
		sql.append(" where ");
		sql.append(" ownUserId=:ownUserId ");
		sql.append(" and memberUserId=:memberUserId ");
		int count = this.executeSQL(sql.toString(), queryWrapper);
		return count;
	}
	
	/**
	 * 删除好友
	 * @author: XiaHui
	 * @param ownUserId
	 * @param memberUserId
	 * @return
	 * @createDate: 2017年9月4日 下午4:14:03
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午4:14:03
	 */
	public int deleteUserCategoryMember(String ownUserId, String memberUserId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("ownUserId", ownUserId);
		queryWrapper.put("memberUserId", memberUserId);

		StringBuilder sql = new StringBuilder();
		sql.append(" delete from im_user_category_member ");
		sql.append(" where ");
		sql.append(" ownUserId=:ownUserId ");
		sql.append(" and memberUserId=:memberUserId ");
		int count = this.executeSQL(sql.toString(), queryWrapper);
		return count;
	}
}
