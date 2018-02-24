package com.im.server.general.common.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.UserChatContent;
import com.im.server.general.common.bean.UserChatItem;
import com.im.server.general.common.dao.UserChatDAO;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;

/**
 * @author: XiaHui
 * @date: 2017年4月26日 下午4:44:17
 */
@Service
public class ChatLogManager {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	UserChatDAO userChatDAO;

	/**
	 * 拼装用户聊天内容列表
	 * 
	 * @param chatContentList
	 * @return
	 */
	public List<Map<String, Object>> getUserChatLogListMap(List<UserChatContent> chatContentList, boolean inverted) {

		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		if (null != chatContentList && !chatContentList.isEmpty()) {
			List<String> contentIds = new ArrayList<String>();
			for (UserChatContent cc : chatContentList) {
				contentIds.add(cc.getId());
			}
			Map<String, List<UserChatItem>> chatItemMap = new HashMap<String, List<UserChatItem>>();
			if (!contentIds.isEmpty()) {
				List<UserChatItem> chatItemList = userChatDAO.getUserChatItemList(contentIds);

				for (UserChatItem item : chatItemList) {
					List<UserChatItem> list = chatItemMap.get(item.getUserChatContentId());
					if (null == list) {
						list = new ArrayList<UserChatItem>();
						chatItemMap.put(item.getUserChatContentId(), list);
					}
					list.add(item);
				}
			}

			for (UserChatContent cc : chatContentList) {

				Map<String, Object> map = new HashMap<String, Object>();
				String messageId = cc.getMessageId();
				String contentId = cc.getId();
				Content content = new Content();
				UserData receiveUserData = new UserData();
				UserData sendUserData = new UserData();

				receiveUserData.setHead(cc.getReceiveUserHead());
				receiveUserData.setId(cc.getReceiveUserId());
				receiveUserData.setName(cc.getReceiveUserName());
				receiveUserData.setNickname(cc.getReceiveUserNickname());
				//receiveUserData.setRole(cc.getReceiveUserRole());

				sendUserData.setHead(cc.getSendUserHead());
				sendUserData.setId(cc.getSendUserId());
				sendUserData.setName(cc.getSendUserName());
				sendUserData.setNickname(cc.getSendUserNickname());
				//sendUserData.setRole(cc.getSendUserRole());

				long timestamp = cc.getTimestamp();
				List<UserChatItem> list = chatItemMap.get(cc.getId());
				List<Section> sections = new ArrayList<Section>();
				if (null != list && !list.isEmpty()) {
					int index = -1;
					Section section;
					List<Item> items = null;
					for (UserChatItem rci : list) {
						if (index != rci.getSection()) {
							index = rci.getSection();

							items = new ArrayList<Item>();
							section = new Section();
							section.setItems(items);

							sections.add(section);
						}

						Item item = new Item();
						item.setType(rci.getType());
						item.setValue(rci.getFilterValue());
						if (null != items) {
							items.add(item);
						}
					}
					content.setTimestamp(timestamp);
					content.setSections(sections);
					map.put("messageKey", messageId);
					map.put("contentId", contentId);
					map.put("sendUserData", sendUserData);
					map.put("receiveUserData", receiveUserData);
					map.put("content", content);
					if (inverted) {
						contents.add(0, map);
					} else {
						contents.add(map);
					}
				}
			}
		}
		return contents;
	}
}
