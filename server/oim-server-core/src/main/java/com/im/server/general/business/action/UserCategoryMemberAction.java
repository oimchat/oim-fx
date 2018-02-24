package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.common.bean.UserCategoryMember;
import com.im.server.general.common.service.UserCategoryService;
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
@ActionMapping(value = "1.103")
public class UserCategoryMemberAction {
	@Resource
	private UserCategoryService userCategorySerivce;

	@MethodMapping(value = "1.1.0001")
	public ResultMessage addUserCategoryMember(SocketSession socketSession, @Define("userCategoryMember") UserCategoryMember userCategoryMember) {
		if (null == userCategoryMember.getOwnUserId() || "".equals(userCategoryMember.getOwnUserId())) {
			userCategoryMember.setOwnUserId(socketSession.getKey());
		}
		return userCategorySerivce.addUserCategoryMember(userCategoryMember);
	}

	 /**
     * 修改好友备注
     * @author XiaHui
     * @date 2017年9月4日 下午3:57:12
     * @param userCategoryMemberId
     * @param remark
     * @param action
     */
	@MethodMapping(value = "1.1.0002")
	public ResultMessage updateUserCategoryMemberRemark(
			SocketSession socketSession,
			@Define("memberUserId") String memberUserId,
			@Define("remark") String remark) {
		String ownUserId=socketSession.getKey();
		return userCategorySerivce.updateUserCategoryMemberRemark(ownUserId, memberUserId, remark);
	}

	 /**
     * 移动好友
     * @author XiaHui
     * @date 2017年9月4日 下午3:57:49
     * @param userCategoryMemberId
     * @param userCategoryId
     * @param action
     */
	@MethodMapping(value = "1.1.0003")
	public ResultMessage moveUserToCategory(
			SocketSession socketSession,
			@Define("memberUserId") String memberUserId,
			@Define("userCategoryId") String userCategoryId) {
		String ownUserId=socketSession.getKey();
		return userCategorySerivce.moveUserToCategory(ownUserId, memberUserId, userCategoryId);
	}

	 /**
     * 删除好友
     * @author XiaHui
     * @date 2017年9月4日 下午4:37:46
     * @param memberUserId
     * @param action
     */
	@MethodMapping(value = "1.1.0004")
	public ResultMessage deleteUserCategoryMember(
			SocketSession socketSession,
			@Define("memberUserId") String memberUserId) {
		String ownUserId=socketSession.getKey();
		return userCategorySerivce.deleteUserCategoryMember(ownUserId, memberUserId);
	}
}
