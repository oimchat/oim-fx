package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.RoomChatContent;
import com.im.server.general.common.bean.RoomChatItem;
import com.im.server.general.common.dao.RoomChatDAO;
import com.im.server.general.common.data.RoomChatLogQuery;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.Item;
import com.onlyxiahui.im.message.data.chat.Section;
import com.onlyxiahui.im.message.data.query.ChatQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * @author: XiaHui
 * @date: 2016年8月23日 上午11:20:14
 */
@Service
public class RoomChatLogService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	RoomChatDAO roomChatDAO;

	public ResultMessage queryRoomChatContentList(String roomId, ChatQuery chatQuery, QueryPage page) {

		RoomChatLogQuery roomChatLogQuery = new RoomChatLogQuery();
		roomChatLogQuery.setRoomId(roomId);
		roomChatLogQuery.setText(chatQuery.getText());
		roomChatLogQuery.setStartDate(chatQuery.getStartDate());
		roomChatLogQuery.setEndDate(chatQuery.getEndDate());

		boolean hasRoomId = (null == roomId || "".equals(roomId));
		List<Map<String, Object>> contents = hasRoomId ? new ArrayList<Map<String, Object>>() : queryRoomChatContentList(roomChatLogQuery, page);

		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("totalPage", page.getTotalPage());
		pageMap.put("pageNumber", page.getPageNumber());

		ResultMessage m = new ResultMessage();
		m.put("roomId", roomId);
		m.put("page", pageMap);
		m.put("contents", contents);
		return m;
	}

	public List<Map<String, Object>> queryRoomChatContentList(RoomChatLogQuery roomChatLogQuery, QueryPage page) {

		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		List<RoomChatContent> chatContentList = roomChatDAO.queryRoomChatContentList(roomChatLogQuery, page);
		List<String> contentIds = new ArrayList<String>();
		for (RoomChatContent rcc : chatContentList) {
			contentIds.add(rcc.getId());
		}
		Map<String, List<RoomChatItem>> chatItemMap = new HashMap<String, List<RoomChatItem>>();
		if (!contentIds.isEmpty()) {
			List<RoomChatItem> chatItemList = roomChatDAO.getRoomChatItemListByContentIds(contentIds);
			for (RoomChatItem rci : chatItemList) {
				List<RoomChatItem> list = chatItemMap.get(rci.getMessageId());
				if (null == list) {
					list = new ArrayList<RoomChatItem>();
					chatItemMap.put(rci.getRoomChatContentId(), list);
				}
				list.add(rci);
			}
		}

		for (RoomChatContent rcc : chatContentList) {

			Map<String, Object> map = new HashMap<String, Object>();
			Content content = new Content();
			UserData userData = new UserData();

			userData.setHead(rcc.getUserHead());
			userData.setId(rcc.getUserId());
			userData.setName(rcc.getUserName());
			userData.setNickname(rcc.getUserNickname());
			//userData.setRole(rcc.getUserRole());
			long timestamp = rcc.getTimestamp();
			List<RoomChatItem> list = chatItemMap.get(rcc.getId());
			List<Section> sections = new ArrayList<Section>();
			if (null != list && !list.isEmpty()) {
				int index = -1;
				Section section;
				List<Item> items = null;
				for (RoomChatItem rci : list) {
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
				map.put("userData", userData);
				map.put("content", content);
				contents.add(0, map);
			}
		}
		return contents;
	}
}
