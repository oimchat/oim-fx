package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.RoomChatContent;
import com.im.server.general.common.bean.RoomChatItem;
import com.im.server.general.common.data.RoomChatLogQuery;
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
public class RoomChatDAO extends BaseDAO {

	String space = "roomChat";

	public List<RoomChatContent> queryRoomChatContentList(RoomChatLogQuery roomChatQuery, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		queryOption.add(new QueryOption("userNickname", QueryOptionType.likeAll));
		QueryUtil.getQueryWrapperType(roomChatQuery, queryWrapper, queryOption);

		List<RoomChatContent> list = this.queryPageListByName(space + ".queryRoomChatContentList", queryWrapper, RoomChatContent.class);
		return list;
	}

	public List<RoomChatItem> queryRoomChatItemList(String text, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		queryWrapper.put("text", text);
		List<RoomChatItem> list = this.queryPageListByName(space + ".queryRoomChatItemList", queryWrapper, RoomChatItem.class);
		return list;
	}

	public List<RoomChatItem> getRoomChatItemListByMessageIds(List<String> messageIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("messageIds", messageIds);
		List<RoomChatItem> list = this.queryPageListByName(space + ".queryRoomChatItemList", queryWrapper, RoomChatItem.class);
		return list;
	}
	
	public List<RoomChatItem> getRoomChatItemListByContentIds(List<String> roomChatContentIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("roomChatContentIds", roomChatContentIds);
		List<RoomChatItem> list = this.queryPageListByName(space + ".queryRoomChatItemList", queryWrapper, RoomChatItem.class);
		return list;
	}
}
