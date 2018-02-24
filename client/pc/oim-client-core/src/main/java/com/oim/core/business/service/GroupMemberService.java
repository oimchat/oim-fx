package com.oim.core.business.service;

import com.oim.core.business.manager.GroupMemberManager;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractService;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 16:19:29
 */
public class GroupMemberService extends AbstractService {

	public GroupMemberService(AppContext appContext) {
		super(appContext);
	}

	public void updateGroupMemberList(final String groupId) {
		GroupMemberManager gmm = appContext.getManager(GroupMemberManager.class);
		gmm.updateGroupMemberList(groupId);
	}
}
