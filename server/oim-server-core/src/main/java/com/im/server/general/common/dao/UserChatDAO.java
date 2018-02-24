package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.UserChatContent;
import com.im.server.general.common.bean.UserChatItem;
import com.im.server.general.common.data.ChatData;
import com.im.server.general.common.data.UserChatLogQuery;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.QueryOption;
import com.only.query.hibernate.QueryOptionType;
import com.only.query.page.QueryPage;
import com.only.query.hibernate.util.QueryUtil;

/**
 * @author: XiaHui
 * @date: 2016年8月19日 下午1:56:03
 */
@Repository
public class UserChatDAO extends BaseDAO {

	String space = "userChat";

	public UserChatContent getUserChatContentByMessageId(String chatReceiveUserId, String chatSendUserId, String messageId) {

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("chatReceiveUserId", chatReceiveUserId);
		queryWrapper.put("chatSendUserId", chatSendUserId);
		queryWrapper.put("messageId", messageId);

		UserChatContent bean = this.queryUniqueResultByName(space + ".getUserChatContentByMessageId", queryWrapper, UserChatContent.class);
		return bean;
	}

	public List<UserChatContent> getUserChatContentList(String chatReceiveUserId, String chatSendUserId, String outId, String outMessageId, boolean latest, long timestamp, QueryPage queryPage) {

		String compare = latest ? ">=" : "<=";

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		queryWrapper.put("chatReceiveUserId", chatReceiveUserId);
		queryWrapper.put("chatSendUserId", chatSendUserId);
		queryWrapper.put("outId", outId);
		queryWrapper.put("outMessageId", outMessageId);
		queryWrapper.put("timestamp", timestamp);
		queryWrapper.put("compare", compare);

		List<UserChatContent> list = this.queryPageListByName(space + ".queryUserChatContentList", queryWrapper, UserChatContent.class);
		return list;
	}

	public List<UserChatItem> getUserChatItemList(List<String> userChatContentIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("userChatContentIds", userChatContentIds);
		List<UserChatItem> list = this.queryPageListByName(space + ".getUserChatItemList", queryWrapper, UserChatItem.class);
		return list;
	}

	public List<UserChatContent> getUserChatContentList(UserChatLogQuery userChatLogQuery, QueryPage queryPage) {

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		QueryUtil.getQueryWrapperType(userChatLogQuery, queryWrapper, queryOption);
		List<UserChatContent> list = this.queryPageListByName(space + ".queryUserChatContentList", queryWrapper, UserChatContent.class);
		return list;
	}

	public List<ChatData> queryChatDataList(UserChatLogQuery userChatLogQuery, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		QueryUtil.getQueryWrapperType(userChatLogQuery, queryWrapper, queryOption);
		List<ChatData> list = this.queryPageListByName(space + ".queryUserChatList", queryWrapper, ChatData.class);
		return list;
	}

	/**
	 * 更新消息为已读
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月17日 上午10:09:04
	 * @update: XiaHui
	 * @updateDate: 2017年4月17日 上午10:09:04
	 */
	public void updateToRead(String receiveUserId, String sendUserId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" update im_user_chat_content set isSend='1' where receiveUserId='");
		sb.append(receiveUserId);
		sb.append("'");
		sb.append(" and sendUserId='");
		sb.append(sendUserId);
		sb.append("'");
		this.executeSQL(sb.toString());
	}

	/**
	 * 获取发送离线信息的用户id
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月17日 上午11:13:54
	 * @update: XiaHui
	 * @updateDate: 2017年4月17日 上午11:13:54
	 */
	public List<String> getOfflineUserIdList(String receiveUserId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("receiveUserId", receiveUserId);
		List<String> list = this.queryPageListByName(space + ".getOfflineUserIdList", queryWrapper, String.class);
		return list;
	}

	/**
	 * 获取离线消息数量
	 * 
	 * @author: XiaHui
	 * @param receiveUserId
	 * @return
	 * @createDate: 2017年4月26日 下午2:13:36
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午2:13:36
	 */
	public List<Map<String, Object>> getOfflineCountList(String receiveUserId, int count) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("receiveUserId", receiveUserId);
		queryWrapper.put("count", count);
		List<Map<String, Object>> list = this.queryListByName(space + ".getOfflineCountList", queryWrapper, null);
		return list;
	}
	
	/**
	 * 获取指定相送离线消息的数量
	 * @author: XiaHui
	 * @param receiveUserId
	 * @param ids
	 * @return
	 * @createDate: 2017年4月27日 上午11:27:21
	 * @update: XiaHui
	 * @updateDate: 2017年4月27日 上午11:27:21
	 */
	public List<Map<String, Object>> getOfflineCountListByIds(String receiveUserId, List<String> ids) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.put("receiveUserId", receiveUserId);
		queryWrapper.put("ids", ids);
		List<Map<String, Object>> list = this.queryListByName(space + ".getOfflineCountListByIds", queryWrapper, null);
		return list;
	}

	/**
	 * 根据id集合或者聊天列表
	 * 
	 * @param idList
	 * @return
	 */
	public List<UserChatContent> getListByIdList(List<String> idList) {
		if(null==idList||idList.isEmpty()){
			return new ArrayList<UserChatContent>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" select * from im_user_chat_content where id in( :idList )");
		sb.append(" order by timestamp desc ");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("idList", idList);
		List<UserChatContent> list = this.readDAO.queryList(sb.toString(), queryWrapper, UserChatContent.class, null);
		return list;
	}
}
