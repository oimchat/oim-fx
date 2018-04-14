package com.oim.core.business.view;

import java.util.List;

import com.onlyxiahui.app.base.view.View;
import com.onlyxiahui.im.bean.Group;
import com.onlyxiahui.im.bean.GroupMember;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.chat.Content;
import com.onlyxiahui.im.message.data.chat.history.GroupChatHistoryData;
import com.onlyxiahui.im.message.data.chat.history.UserChatHistoryData;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2015年3月14日 上午11:14:38
 * @version 0.0.1
 */
public interface ChatListView extends View {

	/********************* user chat start ************************/

	public void show(UserData userData);

	public void updateUserChatInfo(String userId, String text, String time);

	public void setChatUserItemRed(String userId, boolean red, int count);

	public boolean hasUserChat(String userId);

	public boolean isUserChatShowing(String userId);

	public void userChat(boolean isOwn, UserData showUserData, UserData chatUserData, Content content);

	public void showUserChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list);

	public void loadUserChatHistory(UserData userData, PageData page, List<UserChatHistoryData> list);

	/********************* user chat end ************************/

	/********************* group chat start ************************/
	public void show(Group group);

	public boolean hasGroupChat(String groupId);

	public void updateGroupChatInfo(String groupId, String text, String time);

	public void setChatGroupItemRed(String groupId, boolean red, int count);

	public boolean isGroupChatShowing(String groupId);

	public void groupChat(boolean isOwn, Group group, UserData chatUserData, Content content);

	public void showGroupChatHistory(UserData userId, PageData page, List<UserChatHistoryData> list);

	public void loadGroupChatHistory(Group group, PageData page, List<GroupChatHistoryData> contents);

	/********************* group chat end ************************/

	/********************* user chat function start ************************/

	public void doShake(UserData userData);

	public boolean hasRequestRemoteControl(String userId);

	public void showRequestRemoteControl(UserData userData);

	/********************* user chat function end ************************/

	/********************* group member start ************************/

	public void addOrUpdateGroupMember(String groupId, GroupMember groupMember, UserData userData);

	public void removeGroupMember(String groupId, String userId);

	/********************* group member end ************************/

}
