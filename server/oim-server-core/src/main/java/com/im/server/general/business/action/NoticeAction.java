package com.im.server.general.business.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.common.bean.TextNotice;
import com.im.server.general.common.service.TextNoticeService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.only.query.page.DefaultPage;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 
 * @author XiaHui
 *
 */
@Component
@ActionMapping(value = "1.700")
public class NoticeAction {

	@Resource
	TextNoticeService textNoticeService;

	@MethodMapping(value = "1.1.0003")
	public ResultMessage getTextNoticeList(
			SocketSession socketSession,
			@Define("page") PageData pageData) {
		String userId=socketSession.getKey();
		DefaultPage page = new DefaultPage();
		page.setPageSize(pageData.getPageSize());
		page.setPageNumber(pageData.getPageNumber());
		
		List<TextNotice> list = textNoticeService.getTextNoticeList(userId,page);
		ResultMessage message = new ResultMessage();
		message.put("list", list);
		
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("totalPage", page.getTotalPage());
		pageMap.put("pageNumber", page.getPageNumber());
		pageMap.put("pageSize", pageData.getPageSize());
		pageMap.put("startResult", page.getStartResult());
		pageMap.put("endResult", page.getEndResult());
		pageMap.put("totalCount", page.getTotalCount());

		message.put("page", page);
		return message;
	}
}
