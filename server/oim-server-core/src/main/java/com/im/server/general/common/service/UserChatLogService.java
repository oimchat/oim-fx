package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.UserChatContent;
import com.im.server.general.common.dao.UserChatDAO;
import com.im.server.general.common.dao.UserLastChatDAO;
import com.im.server.general.common.data.ChatData;
import com.im.server.general.common.data.UserChatLogQuery;
import com.im.server.general.common.manager.ChatLogManager;
import com.only.query.page.DefaultPage;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.query.ChatQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午11:20:14
 */
@Service
public class UserChatLogService {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	UserChatDAO userChatDAO;
	@Resource
	UserLastChatDAO userLastChatDAO;
	@Resource
	ChatLogManager chatLogManager;
	
	/**
	 * 根据记录表id查询用户聊天记录（上翻，下拉方式的）
	 * 
	 * @param sendUserId
	 * @param receiveUserId
	 * @param startId
	 * @param direction
	 * @param count
	 * @return
	 */
	public ResultMessage getUserChatLogListByStartId(String sendUserId, String receiveUserId, String startId, String direction, int count) {
		boolean latest = !"history".equals(direction);
		DefaultPage queryPage = new DefaultPage();
		queryPage.setPageSize(count);

		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		if ((null != sendUserId && !"".equals(sendUserId)) || (null != receiveUserId && !"".equals(receiveUserId))) {

			UserChatContent ucc = null;
			if (null != startId && !"".equals(startId)) {
				ucc = userChatDAO.get(UserChatContent.class, startId);
			}

			long times = 0;
			if (null != ucc && ucc.getTimestamp() > 0) {
				times = ucc.getTimestamp();
			}

			List<UserChatContent> chatContentList = userChatDAO.getUserChatContentList(receiveUserId, sendUserId, startId, "", latest, times, queryPage);
			contents = chatLogManager.getUserChatLogListMap(chatContentList,true);
		}

		Map<String, Object> page = new HashMap<String, Object>();
		page.put("direction", latest ? "latest" : "history");

		ResultMessage m = new ResultMessage();
		m.put("sendUserId", sendUserId);
		m.put("receiveUserId", receiveUserId);
		m.put("contents", contents);
		m.put("page", page);
		return m;
	}

	/**
	 * 根据消息id查询用户聊天记录（上翻，下拉方式的）
	 * 
	 * @param sendUserId
	 * @param receiveUserId
	 * @param startMessageKey
	 * @param direction
	 * @param count
	 * @return
	 */
	public ResultMessage getUserChatLogListByStartMessageId(String sendUserId, String receiveUserId, String startMessageKey, String direction, int count) {
		boolean latest = !"history".equals(direction);
		DefaultPage queryPage = new DefaultPage();
		queryPage.setPageSize(count);

		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		if ((null != sendUserId && !"".equals(sendUserId)) || (null != receiveUserId && !"".equals(receiveUserId))) {

			UserChatContent ucc = null;
			if (null != startMessageKey && !"".equals(startMessageKey)) {
				ucc = userChatDAO.getUserChatContentByMessageId(receiveUserId, sendUserId, startMessageKey);
			}

			long times = 0;
			if (null != ucc && ucc.getTimestamp() > 0) {
				times = ucc.getTimestamp();
			}

			List<UserChatContent> chatContentList = userChatDAO.getUserChatContentList(receiveUserId, sendUserId, "", startMessageKey, latest, times, queryPage);
			contents = chatLogManager.getUserChatLogListMap(chatContentList,true);
		}

		Map<String, Object> page = new HashMap<String, Object>();

		page.put("direction", latest ? "latest" : "history");

		ResultMessage m = new ResultMessage();
		m.put("sendUserId", sendUserId);
		m.put("receiveUserId", receiveUserId);
		m.put("contents", contents);
		m.put("page", page);
		return m;
	}

	/**
	 * 分页查询聊天记录
	 * 
	 * @param sendUserId
	 * @param receiveUserId
	 * @param chatQuery
	 * @param queryPage
	 * @return
	 */
	public ResultMessage queryUserChatLogList(String sendUserId, String receiveUserId, ChatQuery chatQuery, QueryPage queryPage) {
		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		if ((null != sendUserId && !"".equals(sendUserId)) || (null != receiveUserId && !"".equals(receiveUserId))) {

			UserChatLogQuery userChatLogQuery = new UserChatLogQuery();

			userChatLogQuery.setChatReceiveUserId(receiveUserId);
			userChatLogQuery.setChatSendUserId(sendUserId);

			contents = queryUserChatContentList(userChatLogQuery, queryPage);
		}

		ResultMessage m = new ResultMessage();
		m.put("sendUserId", sendUserId);
		m.put("receiveUserId", receiveUserId);
		m.put("contents", contents);

		return m;
	}

	public List<Map<String, Object>> queryUserChatContentList(UserChatLogQuery userChatLogQuery, QueryPage queryPage) {
		List<UserChatContent> chatContentList = userChatDAO.getUserChatContentList(userChatLogQuery, queryPage);
		List<Map<String, Object>> contents = chatLogManager.getUserChatLogListMap(chatContentList,true);
		return contents;
	}
	
	public List<ChatData> queryChatDataList(UserChatLogQuery userChatLogQuery, QueryPage queryPage){
		List<ChatData> list = userChatDAO.queryChatDataList(userChatLogQuery, queryPage);
		return list;
	}
	
	/**
	 * 获取离线消息信息
	 * @author: XiaHui
	 * @createDate: 2017年4月17日 上午10:21:42
	 * @update: XiaHui
	 * @updateDate: 2017年4月17日 上午10:21:42
	 */
	public List<String> getOfflineUserIdList(String receiveUserId) {
		List<String> list = userChatDAO.getOfflineUserIdList(receiveUserId);
		return list;
	}

	/**
	 * 更新消息为已读
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月17日 上午10:21:09
	 * @update: XiaHui
	 * @updateDate: 2017年4月17日 上午10:21:09
	 */
	public void updateUserOfflineMessageToRead(String receiveUserId, String sendUserId) {
		userChatDAO.updateToRead(receiveUserId,sendUserId);
	}
	
	
	/**
	 * 获取离线消息组合数量<br>
	 * 数据结构：[{"sendUserId":"110","count":100},{"sendUserId":"120","count":12},{
	 * "sendUserId":"119","count":19}]
	 * 
	 * @author: XiaHui
	 * @param receiveUserId
	 * @param count
	 * @return
	 * @createDate: 2017年4月26日 下午2:24:18
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午2:24:18
	 */
	public List<Map<String, Object>> getOfflineCountList(String receiveUserId, int count) {
		List<Map<String, Object>> list = userChatDAO.getOfflineCountList(receiveUserId, count);
		return list;
	}
	
	/**
	 * 获取指定相送离线消息的数量组合数量<br>
	 * 数据结构：[{"sendUserId":"110","count":100},{"sendUserId":"120","count":12},{
	 * "sendUserId":"119","count":19}]
	 * 
	 * @author: XiaHui
	 * @param receiveUserId
	 * @param count
	 * @return
	 * @createDate: 2017年4月26日 下午2:24:18
	 * @update: XiaHui
	 * @updateDate: 2017年4月26日 下午2:24:18
	 */
	public List<Map<String, Object>> getOfflineCountListByIds(String receiveUserId, List<String> ids) {
		List<Map<String, Object>> list = userChatDAO.getOfflineCountListByIds(receiveUserId, ids);
		return list;
	}
}
