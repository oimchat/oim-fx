package com.im.server.general.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.UserLastChat;
import com.only.query.hibernate.QueryWrapper;

/**
 * @author: XiaHui
 * @date: 2017年4月26日 下午3:53:06
 */
@Repository
public class UserLastChatDAO extends BaseDAO {

	/**
	 * 删除最近聊天记录
	 * @author XiaHui
	 * @date 2017年5月17日 上午11:15:31
	 * @param userId
	 * @param chatId
	 * @param type
	 * @return
	 */
	public boolean deleteBy(String userId, String chatId, String type){
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from im_user_last_chat where userId=:userId");
		sb.append(" and chatId=:chatId");
		sb.append(" and type=:type");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		queryWrapper.addParameter("chatId", chatId);
		queryWrapper.addParameter("type", type);
		int count=this.executeSQL(sb.toString(), queryWrapper);
		return count>0;
	}
	
	/**
	 * 获取用户最近聊天对象记录
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @param chatId
	 * @return
	 * @createDate: 2017年4月26日 下午3:59:28
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午3:59:28
	 */
	public UserLastChat getUserLastChat(String userId, String chatId, String type) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user_last_chat where userId=:userId");
		sb.append(" and chatId=:chatId");
		sb.append(" and type=:type limit 0,1");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		queryWrapper.addParameter("chatId", chatId);
		queryWrapper.addParameter("type", type);
		UserLastChat bean = this.readDAO.queryUniqueResult(sb.toString(), queryWrapper, UserLastChat.class, null);
		return bean;
	}

	/**
	 * 获取用户最后聊天记录
	 * 
	 * @author: XiaHui
	 * @param userId
	 * @return
	 * @createDate: 2017年4月26日 下午4:01:00
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午4:01:00
	 */
	public List<UserLastChat> getUserLastChatList(String userId, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user_last_chat where userId=:userId");
		sb.append(" order by timestamp desc ");
		
		if (count > 0) {
			sb.append(" limit 0,");
			sb.append(count);
		}

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userId", userId);
		List<UserLastChat> list = this.readDAO.queryList(sb.toString(), queryWrapper, UserLastChat.class, null);
		return list;
	}
}
