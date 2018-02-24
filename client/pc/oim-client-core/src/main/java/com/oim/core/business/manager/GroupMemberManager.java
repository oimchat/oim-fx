package com.oim.core.business.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oim.core.business.sender.GroupSender;
import com.oim.core.business.view.ChatListView;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;

/**
 * 
 * @author: XiaHui
 * @date 2018-01-18 16:19:58
 */
public class GroupMemberManager extends AbstractManager {

	public GroupMemberManager(AppContext appContext) {
		super(appContext);
	}

	public void updateGroupMemberList(final String groupId){
		DataBackAction dataBackAction = new DataBackActionAdapter() {

			@Back
			public void back(@Define("userDataList") List<UserData> userDataList, @Define("groupMemberList") List<GroupMember> groupMemberList) {
				setGroupUserList(groupId, userDataList, groupMemberList);
			}
		};
		GroupSender gh = this.appContext.getSender(GroupSender.class);
		gh.getGroupMemberListWithUserDataList(groupId, dataBackAction);
	}
	
	
	public void setGroupUserList(String groupId,List<UserData> userDataList, List<GroupMember> groupMemberList){
		
		if(null!=userDataList){
			Map<String,GroupMember> map=new HashMap<String,GroupMember>();
			if(null!=groupMemberList){
				for(GroupMember gm:groupMemberList){
					map.put(gm.getUserId(), gm);
				}
			}
			for(UserData userData: userDataList){
				String userId=userData.getId();
				GroupMember groupMember=map.get(userId);
				ChatListView chatListView = appContext.getSingleView(ChatListView.class);
				chatListView.addOrUpdateGroupMember(groupId, groupMember, userData);
			}
		}
	}
}
