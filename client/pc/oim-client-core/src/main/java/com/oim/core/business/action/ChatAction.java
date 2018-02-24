package com.oim.core.business.action;

import java.util.List;

import com.oim.core.business.service.LastListService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.chat.LastChat;

/**
 * 描述：负责接受聊天相关业务的控制层
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */

@ActionMapping(value = "1.500")
public class ChatAction extends AbstractAction {

	public ChatAction(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 收到最后聊天列表
	 * 
	 */
	@MethodMapping(value = "1.1.0001")
	public void setLastChatList(
			@Define("userId") String userId,
			@Define("count") int count,
			@Define("lastChatList") List<LastChat> lastChatList) {
		LastListService lls = appContext.getService(LastListService.class);
		lls.setLastChatList(userId, lastChatList);
	}
	
	@MethodMapping(value = "1.1.0002")
	public void getLastChatWithDataList(
			@Define("userId") String userId,
			@Define("count") int count,
			@Define("lastChatList") List<LastChat> lastChatList,
			@Define("userDataList") List<UserData> userDataList,
			@Define("groupList") List<Group> groupList) {
		LastListService lls = appContext.getService(LastListService.class);
		lls.getLastChatWithDataList(userId, lastChatList,userDataList,groupList);
	}
}
