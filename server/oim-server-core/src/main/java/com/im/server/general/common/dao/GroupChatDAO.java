package com.im.server.general.common.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.im.base.dao.BaseDAO;
import com.im.server.general.common.bean.GroupChatContent;
import com.im.server.general.common.bean.GroupChatItem;
import com.im.server.general.common.data.GroupChatLogQuery;
import com.only.query.hibernate.QueryOption;
import com.only.query.hibernate.QueryOptionType;
import com.only.query.hibernate.QueryWrapper;
import com.only.query.hibernate.util.QueryUtil;
import com.only.query.page.QueryPage;

/**
 * @author: XiaHui
 * @date: 2016年8月19日 下午1:56:03
 */
@Repository
public class GroupChatDAO extends BaseDAO {

	String space = "groupChat";

	public List<GroupChatContent> queryGroupChatContentList(GroupChatLogQuery groupChatQuery, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		queryOption.add(new QueryOption("userNickname", QueryOptionType.likeAll));
		QueryUtil.getQueryWrapperType(groupChatQuery, queryWrapper, queryOption);

		List<GroupChatContent> list = this.queryPageListByName(space + ".queryGroupChatContentList", queryWrapper, GroupChatContent.class);
		return list;
	}

	public List<GroupChatItem> queryGroupChatItemList(String text, QueryPage queryPage) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.setPage(queryPage);
		List<QueryOption> queryOption = new ArrayList<QueryOption>();
		queryOption.add(new QueryOption("text", QueryOptionType.likeAll));
		queryWrapper.put("text", text);
		List<GroupChatItem> list = this.queryPageListByName(space + ".queryGroupChatItemList", queryWrapper, GroupChatItem.class);
		return list;
	}

	public List<GroupChatItem> getGroupChatItemListByMessageIds(List<String> messageIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("messageIds", messageIds);
		List<GroupChatItem> list = this.queryPageListByName(space + ".queryGroupChatItemList", queryWrapper, GroupChatItem.class);
		return list;
	}
	
	public List<GroupChatItem> getGroupChatItemListByContentIds(List<String> groupChatContentIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.addParameter("groupChatContentIds", groupChatContentIds);
		List<GroupChatItem> list = this.queryPageListByName(space + ".queryGroupChatItemList", queryWrapper, GroupChatItem.class);
		return list;
	}
}
