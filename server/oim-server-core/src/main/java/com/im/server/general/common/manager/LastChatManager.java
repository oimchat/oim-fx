package com.im.server.general.common.manager;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.UserLastChat;
import com.im.server.general.common.dao.UserLastChatDAO;

/**
 * @author: XiaHui
 * @date: 2017年4月26日 下午4:44:17
 */
@Service
public class LastChatManager {

	@Resource
	UserLastChatDAO userLastChatDAO;

	public void saveOrUpdate(UserLastChat userLastChat) {
		UserLastChat bean = userLastChatDAO.getUserLastChat(userLastChat.getUserId(), userLastChat.getChatId(), userLastChat.getType());
		if (null != bean) {
			long timestamp = System.currentTimeMillis();
			bean.setMessageId(userLastChat.getMessageId());
			bean.setTime(new Timestamp(timestamp));
			bean.setTimestamp(timestamp);
			bean.setContentId(userLastChat.getContentId());
			userLastChatDAO.update(bean);
		} else {
			userLastChatDAO.save(userLastChat);
		}
	}
}
