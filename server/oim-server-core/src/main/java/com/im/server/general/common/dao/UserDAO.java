package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.User;
import com.im.server.general.common.bean.UserCategoryMember;
import com.only.query.hibernate.QueryOption;
import com.only.query.hibernate.QueryOptionType;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.util.QueryUtil;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.query.UserQuery;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月14日 下午4:17:01
 * @version 0.0.1
 */
@Repository
public class UserDAO extends BaseDAO {

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 */
	public User get(String id) {
		return this.readDAO.get(User.class, id);
	}

	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 */
	public void delete(String id) {
		this.deleteById(User.class, id);
	}

	/**
	 * 根据账号获取用户
	 * 
	 * @param account
	 * @return
	 */
	public User getUserByAccount(String account) {
		String sql = "select * from im_user where account=:account limit 0,1";
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("account", account);
		User user = this.queryUniqueResult(sql, queryWrapper, User.class, null);
		return user;
	}

	public User getByAccount(String account) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("account", account);
		User user = this.queryUniqueResultByName("user.getUserByAccount", queryWrapper, User.class);
		return user;
	}

	public UserData getUserDataByAccount(String account) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("account", account);
		UserData user = this.queryUniqueResultByName("user.getUserByAccount", queryWrapper, UserData.class);
		return user;
	}

	public Long getUserAllCount() {
		Long count = this.queryUniqueResultByName("user.getUserAllCount", null, Long.class);
		return count;
	}

	public String getIdByAccount(String account) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("account", account);
		String user = this.queryUniqueResultByName("user.getUserIdByAccount", queryWrapper, String.class);
		return user;
	}

	/**
	 * 根据数字号码获取用户
	 * 
	 * @param number
	 * @return
	 */
	public User getUserByNumber(int number) {
		String sql = "select * from im_user where number=:number limit 0,1";
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("number", number);
		User user = this.queryUniqueResult(sql, queryWrapper, User.class, null);
		return user;
	}

	/**
	 * 根据用户账号获取id
	 * 
	 * @param account
	 * @return
	 */
	public String getUserIdByAccount(String account) {
		User u = this.getUserByAccount(account);
		return null == u ? null : u.getId();
	}

	/**
	 * 根据数字账号获取用户id
	 * 
	 * @param number
	 * @return
	 */
	public String getUserIdByNumber(int number) {
		User u = this.getUserByNumber(number);
		return null == u ? null : u.getId();
	}

	/**
	 * 根据拥有者的用户id获取其所有的用户成员
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUserCategoryMemberUserListByUserId(String userId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select u from ");
		hql.append(User.class.getName());
		hql.append(" u where u.id in ");
		hql.append("(select ccm.memberUserId from ");
		hql.append(UserCategoryMember.class.getName());
		hql.append(" ccm where ownUserId='");
		hql.append(userId);
		hql.append("' ");
		hql.append(" )");
		List<User> userList = (List<User>) this.find(hql.toString());
		return userList;
	}

	public UserData getUserDataById(String id) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("id", id);
		UserData userData = this.queryUniqueResultByName("user.getUserDataById", queryWrapper, UserData.class);
		return userData;
	}

	/**
	 * 条件查询用户列表
	 * 
	 * @param userQuery
	 * @param queryPage
	 * @return
	 */
	public List<UserData> queryUserList(UserQuery userQuery, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> optionList = new ArrayList<QueryOption>();
		optionList.add(new QueryOption("queryText", QueryOptionType.likeAll));// 设置查询条件为like
		optionList.add(new QueryOption("homeAddress", QueryOptionType.likeAll));// 设置查询条件为like
		optionList.add(new QueryOption("locationAddress", QueryOptionType.likeAll));// 设置查询条件为like
		QueryUtil.getQueryWrapperType(userQuery, queryWrapper, optionList);
		List<UserData> userList = this.queryPageListByName("user.queryUserList", queryWrapper, UserData.class);
		return userList;
	}

	public List<UserData> getUserCategoryMemberUserDataList(String userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<UserData> userList = this.queryListByName("user.getUserCategoryMemberUserDataList", queryWrapper, UserData.class);
		return userList;
	}

	/**
	 * 根据群id，获取所有成员
	 * 
	 * @param groupId
	 * @return
	 */
	public List<UserData> getGroupMemberUserDataListByGroupId(String groupId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("groupId", groupId);
		List<UserData> userList = this.queryListByName("user.getGroupMemberUserDataListByGroupId", queryWrapper, UserData.class);
		return userList;
	}

	/**
	 * 根用户id，获取他所在群的所有成员
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserData> getGroupMemberUserDataListByUserId(String userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<UserData> userList = this.queryListByName("user.getGroupMemberUserDataListByUserId", queryWrapper, UserData.class);
		return userList;
	}

	/**
	 * 选中更新不为空的字段
	 * 
	 * @param user
	 * @return
	 */
	public int updateSelective(User user) {
		QueryWrapper queryWrapper = QueryUtil.getQueryWrapper(user);
		return this.executeSQLByName("user.updateSelective", queryWrapper);
	}

	public boolean updatePassword(String id, String password) {
		StringBuilder sql = new StringBuilder();
		sql.append(" update im_user set `password`=:password ");
		sql.append(" where id=:id ");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("id", id);
		queryWrapper.put("password", password);
		int i = this.executeSQL(sql.toString(), queryWrapper);
		return i > 0;
	}

	public List<UserData> getUserDataList(List<String> ids) {
		if (null == ids || ids.isEmpty()) {
			return new ArrayList<UserData>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user where id in ( :ids )");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("ids", ids);
		List<UserData> list = this.readDAO.queryList(sb.toString(), queryWrapper, UserData.class, null);
		return list;
	}
}
