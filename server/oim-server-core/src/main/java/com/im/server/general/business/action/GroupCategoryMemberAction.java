package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.common.bean.GroupCategoryMember;
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
@ActionMapping(value = "1.203")
public class GroupCategoryMemberAction {
	@Resource
	private GroupCategoryService groupCategorySerivce;

	/**
	 * 加入群
	 * @author: XiaHui
	 * @return
	 * @createDate: 2017年9月4日 下午5:00:56
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午5:00:56
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage addGroupCategoryMember(SocketSession socketSession, @Define("groupCategoryMember") GroupCategoryMember groupCategoryMember) {
		if (null == groupCategoryMember.getUserId() || "".equals(groupCategoryMember.getUserId())) {
			groupCategoryMember.setUserId(socketSession.getKey());
		}
		return groupCategorySerivce.addGroupCategoryMember(groupCategoryMember);
	}

	/**
     * 移动群
     * @author XiaHui
     * @date 2017年9月4日 下午5:00:34
     * @param groupCategoryId
     * @param groupId
     * @param action
     */
	@MethodMapping(value = "1.1.0003")
	public ResultMessage moveGroupToCategory(
			SocketSession socketSession,
			@Define("groupId") String groupId,
			@Define("groupCategoryId") String groupCategoryId) {
		String userId = socketSession.getKey();
		return groupCategorySerivce.moveGroupToCategory(userId, groupId, groupCategoryId);
	}

	/**
     * 退出群
     * @author XiaHui
     * @date 2017年9月4日 下午5:00:10
     * @param groupId
     * @param action
     */
	@MethodMapping(value = "1.1.0004")
	public ResultMessage quitGroup(
			SocketSession socketSession,
			@Define("groupId") String groupId) {
		String userId = socketSession.getKey();
		return groupCategorySerivce.deleteGroupCategoryMember(userId, groupId);
	}
}
