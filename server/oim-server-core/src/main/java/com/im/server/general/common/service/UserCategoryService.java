package com.im.server.general.common.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.constant.ErrorConstants;
import com.im.server.general.common.bean.UserCategory;
import com.im.server.general.common.bean.UserCategoryMember;
import com.im.server.general.common.dao.NumberDAO;
import com.im.server.general.common.dao.UserCategoryDAO;
import com.im.server.general.common.dao.UserCategoryMemberDAO;
import com.im.server.general.common.dao.UserDAO;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月8日 下午8:12:31
 * @version 0.0.1
 */
@Service
@Transactional
public class UserCategoryService {

	@Resource
	private UserDAO userDAO;
	@Resource
	private NumberDAO numberDAO;
	@Resource
	private UserCategoryDAO userCategoryDAO;
	@Resource
	private UserCategoryMemberDAO userCategoryMemberDAO;

	/**
	 * 新增好友分组
	 * 
	 * @author: XiaHui
	 * @param userCategory
	 * @return
	 * @createDate: 2017年9月4日 下午3:18:38
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:18:38
	 */
	public ResultMessage addUserCategory(UserCategory userCategory) {
		ResultMessage message = new ResultMessage();
		if (null != userCategory) {
			try {

				int userCategoryMaxCount = 30;
				String userId = userCategory.getUserId();
				long count = userCategoryDAO.getUserCategoryCount(userId);
				if (count >= userCategoryMaxCount) {
					message.addWarning(ErrorConstants.warning_business + ".0001", "分组已经达到最大上限！");
				} else {
					userCategory.setSort(UserCategory.sort_custom);
					userCategoryDAO.save(userCategory);
					message.put("userCategory", userCategory);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} else {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/**
	 * 修改分组名称
	 * 
	 * @author: XiaHui
	 * @param id
	 * @param name
	 * @return
	 * @createDate: 2017年9月4日 下午2:36:01
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午2:36:01
	 */
	public ResultMessage updateUserCategoryName(String id, String name) {
		ResultMessage message = new ResultMessage();
		try {
			if (StringUtils.isNotBlank(id)) {
				UserCategory userCategory = userCategoryDAO.get(UserCategory.class, id);
				userCategory.setName(name);
				userCategoryDAO.update(userCategory);
				message.put("userCategory", userCategory);
			} else {
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 添加好友
	 * 
	 * @author: XiaHui
	 * @param userCategoryMember
	 * @return
	 * @createDate: 2017年9月4日 下午3:19:04
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:19:04
	 */
	public ResultMessage addUserCategoryMember(UserCategoryMember userCategoryMember) {

		ResultMessage message = new ResultMessage();
		if (null != userCategoryMember && null != userCategoryMember.getMemberUserId()) {
			try {
				userCategoryMemberDAO.save(userCategoryMember);
				message.put("userCategoryMember", userCategoryMember);
			} catch (Exception e) {
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} else {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/**
	 * 移动好友到其它分组
	 * 
	 * @author: XiaHui
	 * @param userCategoryMemberId
	 * @param userCategoryId
	 * @return
	 * @createDate: 2017年9月4日 下午3:30:22
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:30:22
	 */
	public ResultMessage moveUserToCategory(String ownUserId, String memberUserId, String userCategoryId) {
		ResultMessage message = new ResultMessage();
		try {
			if (StringUtils.isNotBlank(memberUserId)) {
				int count = userCategoryMemberDAO.updateUserCategoryId(ownUserId, memberUserId, userCategoryId);
				if (count <= 0) {
					message.addWarning(ErrorConstants.warning_business + ".0001", "移动失败！");
				}
			} else {
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/**
	 * 修改好友备注
	 * 
	 * @author: XiaHui
	 * @param userCategoryMemberId
	 * @param remark
	 * @return
	 * @createDate: 2017年9月4日 下午3:24:53
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:24:53
	 */
	public ResultMessage updateUserCategoryMemberRemark(String ownUserId, String memberUserId, String remark) {

		ResultMessage message = new ResultMessage();
		try {

			if (StringUtils.isNotBlank(memberUserId)) {
				int count = userCategoryMemberDAO.updateRemark(ownUserId, memberUserId, remark);
				if (count <= 0) {
					message.addWarning(ErrorConstants.warning_business + ".0001", "修改失败！");
				}
			} else {
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/**
	 * 删除好友
	 * 
	 * @author: XiaHui
	 * @return
	 * @createDate: 2017年9月4日 下午3:23:37
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:23:37
	 */
	public ResultMessage deleteUserCategoryMember(String ownUserId, String memberUserId) {

		ResultMessage message = new ResultMessage();
		try {
			if (StringUtils.isNotBlank(ownUserId) && StringUtils.isNotBlank(memberUserId)) {
				int count = userCategoryMemberDAO.deleteUserCategoryMember(ownUserId, memberUserId);
				if (count <= 0) {
					message.addWarning(ErrorConstants.warning_business + ".0001", "删除失败！");
				}
			} else {
				message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}
}
