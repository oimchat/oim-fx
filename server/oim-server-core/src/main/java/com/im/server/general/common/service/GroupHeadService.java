package com.im.server.general.common.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.server.general.common.bean.GroupHead;
import com.im.server.general.common.dao.GroupHeadDAO;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-29 14:40:44
 */
@Service
@Transactional
public class GroupHeadService {

	@Resource
	private GroupHeadDAO groupHeadDAO;

	public ResultMessage uploadHead(GroupHead groupHead) {
		ResultMessage message = new ResultMessage();
		try {
			groupHead.setCreateTime(new Date());
			groupHeadDAO.save(groupHead);
			message.put("groupHead", groupHead);
		} catch (Exception e) {
			message.addWarning("000000", "上传失败！");
		}
		return message;
	}

	public GroupHead getGroupHeadByGroupId(String groupId) {
		return groupHeadDAO.getLastByGroupId(groupId);
	}

	public List<GroupHead> getGroupCategoryMemberGroupHeadListByUserId(String userId) {
		return groupHeadDAO.getGroupCategoryMemberGroupHeadListByUserId(userId);
	}

	public List<GroupHead> getGroupHeadListByGroupIds(List<String> ids) {
		return groupHeadDAO.getGroupHeadListByGroupIds(ids);
	}
}
