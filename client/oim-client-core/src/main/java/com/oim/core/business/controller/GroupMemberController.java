package com.oim.core.business.controller;

import com.oim.core.business.manager.GroupMemberManager;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractController;

public class GroupMemberController extends AbstractController {

	public GroupMemberController(AppContext appContext) {
		super(appContext);
	}

	public void loadGroupMember(String groupId) {
		GroupMemberManager gmm = appContext.getManager(GroupMemberManager.class);
		gmm.updateGroupMemberList(groupId);
	}
}
