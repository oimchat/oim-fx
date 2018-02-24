package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.bean.GroupChatContent;
import com.im.server.general.common.bean.GroupChatItem;
import com.im.server.general.common.dao.GroupChatDAO;
import com.im.server.general.common.data.GroupChatLogQuery;
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
public class GroupChatLogService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	GroupChatDAO groupChatDAO;

	public ResultMessage queryGroupChatContentList(String groupId, ChatQuery chatQuery, QueryPage page) {

		GroupChatLogQuery groupChatLogQuery = new GroupChatLogQuery();
		groupChatLogQuery.setGroupId(groupId);
		groupChatLogQuery.setText(chatQuery.getText());
		groupChatLogQuery.setStartDate(chatQuery.getStartDate());
		groupChatLogQuery.setEndDate(chatQuery.getEndDate());

		boolean hasGroupId = (null == groupId || "".equals(groupId));
		List<Map<String, Object>> contents = hasGroupId ? new ArrayList<Map<String, Object>>() : queryGroupChatContentList(groupChatLogQuery, page);

		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("totalPage", page.getTotalPage());
		pageMap.put("pageNumber", page.getPageNumber());

		ResultMessage m = new ResultMessage();
		m.put("groupId", groupId);
		m.put("page", pageMap);
		m.put("contents", contents);
		return m;
	}

	public List<Map<String, Object>> queryGroupChatContentList(GroupChatLogQuery groupChatLogQuery, QueryPage page) {

		List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();

		List<GroupChatContent> chatContentList = groupChatDAO.queryGroupChatContentList(groupChatLogQuery, page);
		List<String> contentIds = new ArrayList<String>();
		for (GroupChatContent rcc : chatContentList) {
			contentIds.add(rcc.getId());
		}
		Map<String, List<GroupChatItem>> chatItemMap = new HashMap<String, List<GroupChatItem>>();
		if (!contentIds.isEmpty()) {
			List<GroupChatItem> chatItemList = groupChatDAO.getGroupChatItemListByContentIds(contentIds);
			for (GroupChatItem rci : chatItemList) {
				List<GroupChatItem> list = chatItemMap.get(rci.getMessageId());
				if (null == list) {
					list = new ArrayList<GroupChatItem>();
					chatItemMap.put(rci.getGroupChatContentId(), list);
				}
				list.add(rci);
			}
		}

		for (GroupChatContent rcc : chatContentList) {

			Map<String, Object> map = new HashMap<String, Object>();
			Content content = new Content();
			UserData userData = new UserData();

			userData.setHead(rcc.getUserHead());
			userData.setId(rcc.getUserId());
			userData.setName(rcc.getUserName());
			userData.setNickname(rcc.getUserNickname());
			//userData.setRole(rcc.getUserRole());
			long timestamp = rcc.getTimestamp();
			List<GroupChatItem> list = chatItemMap.get(rcc.getId());
			List<Section> sections = new ArrayList<Section>();
			if (null != list && !list.isEmpty()) {
				int index = -1;
				Section section;
				List<Item> items = null;
				for (GroupChatItem rci : list) {
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
