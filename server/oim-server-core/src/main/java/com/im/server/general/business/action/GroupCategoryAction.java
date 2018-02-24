package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.common.bean.GroupCategory;
import com.im.server.general.common.service.GroupCategoryService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月8日 下午8:12:59
 * @version 0.0.1
 */
@Component
@ActionMapping(value = "1.202")
public class GroupCategoryAction {

	@Resource
	private GroupCategoryService groupCategorySerivce;
	/**
	 * 
	 * @param groupMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage addGroupCategory(SocketSession socketSession, @Define("groupCategory") GroupCategory groupCategory) {
		if (null == groupCategory.getUserId() || "".equals(groupCategory.getUserId())) {
			groupCategory.setUserId(socketSession.getKey());
		}
		return groupCategorySerivce.addGroupCategory(groupCategory);
	}

	@MethodMapping(value = "1.1.0002")
	public ResultMessage getGroupCategory(SocketSession socketSession, @Define("groupCategoryId") String groupCategoryId) {
		ResultMessage message = new ResultMessage();
		try {
			GroupCategory groupCategory = groupCategorySerivce.getById(groupCategoryId);
			message.put("groupCategory", groupCategory);
		} catch (Exception e) {
			message.addWarning("0001", "");
		}
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public ResultMessage updateGroupCategoryName(
			SocketSession socketSession,
			@Define("userId") String userId,
			@Define("groupCategoryId") String id,
			@Define("groupCategoryName") String name) {
		return groupCategorySerivce.updateGroupCategoryName(id, name);
	}
}
