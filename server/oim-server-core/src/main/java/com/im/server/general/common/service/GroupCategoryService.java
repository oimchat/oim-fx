package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.base.common.constant.ErrorConstants;
import com.im.server.general.business.push.GroupCategoryMemberPush;
import com.im.server.general.common.bean.GroupCategory;
import com.im.server.general.common.bean.GroupCategoryMember;
import com.im.server.general.common.bean.GroupMember;
import com.im.server.general.common.dao.GroupCategoryDAO;
import com.im.server.general.common.dao.GroupCategoryMemberDAO;
import com.im.server.general.common.dao.GroupMemberDAO;
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
public class GroupCategoryService {

	@Resource
	private GroupCategoryDAO groupCategoryDAO;
	@Resource
	private GroupCategoryMemberDAO groupCategoryMemberDAO;
	@Resource
	private GroupMemberDAO groupMemberDAO;
	@Resource
	private GroupCategoryMemberPush groupCategoryMemberPush;

	public GroupCategory getById(String id) {
		return groupCategoryDAO.get(GroupCategory.class, id);
	}

	/**
	 * 添加群分组
	 * 
	 * @author: XiaHui
	 * @param groupCategory
	 * @return
	 * @createDate: 2017年9月4日 下午3:35:48
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:35:48
	 */
	public ResultMessage addGroupCategory(GroupCategory groupCategory) {
		ResultMessage message = new ResultMessage();
		if (null != groupCategory) {
			try {
				String userId = groupCategory.getUserId();
				long count = groupCategoryDAO.getGroupCategoryCount(userId);

				if (count >= 30) {
					message.addWarning(ErrorConstants.warning_business + ".0001", "分组已经达到最大上限！");
				} else {
					groupCategory.setSort(GroupCategory.sort_custom);
					groupCategoryDAO.save(groupCategory);
					message.put("groupCategory", groupCategory);
				}
			} catch (Exception e) {
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
	 * @createDate: 2017年9月4日 下午3:35:59
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:35:59
	 */
	public ResultMessage updateGroupCategoryName(String id, String name) {
		ResultMessage message = new ResultMessage();
		try {
			GroupCategory groupCategory = groupCategoryDAO.get(GroupCategory.class, id);
			groupCategory.setName(name);
			groupCategoryDAO.update(groupCategory);
			message.put("groupCategory", groupCategory);
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 加入群
	 * 
	 * @author: XiaHui
	 * @param groupCategoryMember
	 * @return
	 * @createDate: 2017年9月4日 下午3:36:35
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:36:35
	 */
	public ResultMessage addGroupCategoryMember(GroupCategoryMember groupCategoryMember) {
		ResultMessage m = new ResultMessage();
		if (null != groupCategoryMember && null != groupCategoryMember.getGroupId()) {
			try {
				String groupId = groupCategoryMember.getGroupId();
				String userId = groupCategoryMember.getUserId();
				String groupCategoryId = groupCategoryMember.getGroupCategoryId();
				if (null == groupCategoryId || "".equals(groupCategoryId)) {

					GroupCategory groupCategory = groupCategoryDAO.getDefaultGroupCategory(userId);
					if (groupCategory == null) {
						groupCategory = new GroupCategory();
						groupCategory.setName("我的聊天群");
						groupCategory.setSort(GroupCategory.sort_custom);
						groupCategoryDAO.save(groupCategory);
					}
					groupCategoryMember.setGroupCategoryId(groupCategory.getId());
				}

				groupCategoryMemberDAO.save(groupCategoryMember);

				GroupMember gm = new GroupMember();
				gm.setGroupId(groupCategoryMember.getGroupId());
				gm.setUserId(groupCategoryMember.getUserId());
				gm.setPosition(GroupMember.position_normal);
				groupMemberDAO.save(gm);

				m.put("groupCategoryMember", groupCategoryMember);
				m.put("groupMember", gm);

				List<String> userIdList = new ArrayList<String>();
				List<GroupMember> list = groupMemberDAO.getListByGroupId(groupId);
				for (GroupMember member : list) {
					userIdList.add(member.getUserId());
				}
				if (!userIdList.contains(gm.getUserId())) {
					userIdList.add(gm.getUserId());
				}
				groupCategoryMemberPush.pushAddUser(groupId, userId, userIdList);
			} catch (Exception e) {
				m.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
			}
		} else {
			m.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return m;
	}

	/**
	 * 移动群到其它分组
	 * 
	 * @author: XiaHui
	 * @return
	 * @createDate: 2017年9月4日 下午3:39:55
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:39:55
	 */
	public ResultMessage moveGroupToCategory(String userId,String groupId, String groupCategoryId) {
		ResultMessage message = new ResultMessage();
		try {
			int count = groupCategoryMemberDAO.updateGroupCategoryId(userId, groupId, groupCategoryId);
			if (count <= 0) {
				message.addWarning(ErrorConstants.warning_business + ".0001", "移动失败！");
			}
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}

	/**
	 * 退出群
	 * 
	 * @author: XiaHui
	 * @param groupCategoryMemberId
	 * @return
	 * @createDate: 2017年9月4日 下午3:42:08
	 * @update: XiaHui
	 * @updateDate: 2017年9月4日 下午3:42:08
	 */
	public ResultMessage deleteGroupCategoryMember(String userId,String groupId) {
		ResultMessage message = new ResultMessage();
		try {
			
			int count = groupCategoryMemberDAO.deleteGroupCategoryMember(userId, groupId);
			if (count <= 0) {
				message.addWarning(ErrorConstants.warning_business + ".0001", "退出失败！");
			}
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_system + ".0001", "系统异常！");
		}
		return message;
	}
}
