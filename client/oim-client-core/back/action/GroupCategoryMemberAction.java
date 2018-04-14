package com.oim.core.business.action;

import com.oim.core.business.manager.ChatManage;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */

@ActionMapping(value = "1.203")
public class GroupCategoryMemberAction extends AbstractAction {

	public GroupCategoryMemberAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.2.0002")
	public void updateGroupUserList(@Define("groupId") String groupId, @Define("userId") String userId) {
		ChatManage chatManage = appContext.getManager(ChatManage.class);
		chatManage.updateGroupUserList(groupId);
	}
}
