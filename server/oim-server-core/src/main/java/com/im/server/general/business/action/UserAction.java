package com.im.server.general.business.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.im.server.general.business.mq.MessageQueueWriteHandler;
import com.im.server.general.business.push.UserPush;
import com.im.server.general.common.bean.UserCategory;
import com.im.server.general.common.bean.UserCategoryMember;
import com.im.server.general.common.bean.UserHead;
import com.im.server.general.common.service.UserHeadService;
import com.im.server.general.common.service.UserService;
import com.im.server.general.common.service.api.UserBaseService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.only.query.page.DefaultPage;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.data.query.UserQuery;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@Component
@ActionMapping(value = "1.101")
public class UserAction {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	private UserService userService;
	@Resource
	private MessageQueueWriteHandler messageQueueWriteHandler;
	@Resource
	private UserPush userPush;
	@Resource
	private UserBaseService userBaseService;
	@Resource
	private UserHeadService userHeadService;
	
	/**
	 * 只获取好友分组
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage getUserCategoryList(SocketSession socketSession) {
		String userId = socketSession.getKey();
		List<UserCategory> userCategoryList = userService.getUserCategoryList(userId);
		ResultMessage message = new ResultMessage();
		message.put("userDataList", userCategoryList);
		return message;
	}

	/**
	 * 获取好友分组成员信息（不包含用户详情）
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0002")
	public ResultMessage getUserCategoryMemberUserDataList(SocketSession socketSession) {
		String userId = socketSession.getKey();
		List<UserData> userDataList = userService.getUserCategoryMemberUserDataList(userId);
		userService.setUserStatus(userDataList);
		ResultMessage message = new ResultMessage();
		message.put("userDataList", userDataList);
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public ResultMessage getUserCategoryMemberList(SocketSession socketSession) {
		String userId = socketSession.getKey();
		List<UserCategoryMember> userCategoryMemberList = userService.getUserCategoryMemberList(userId);
		ResultMessage message = new ResultMessage();
		message.put("userCategoryMemberList", userCategoryMemberList);
		return message;
	}

	@MethodMapping(value = "1.1.0004")
	public ResultMessage getUserCategoryWithUserList(SocketSession socketSession) {
		String userId = socketSession.getKey();
		List<UserCategory> userCategoryList = userService.getUserCategoryList(userId);
		List<UserData> userDataList = userService.getUserCategoryMemberUserDataList(userId);
		List<UserCategoryMember> userCategoryMemberList = userService.getUserCategoryMemberList(userId);
		userService.setUserStatus(userDataList);
		ResultMessage message = new ResultMessage();
		message.put("userCategoryList", userCategoryList);
		message.put("userDataList", userDataList);
		message.put("userCategoryMemberList", userCategoryMemberList);
		return message;
	}

	/**
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0005")
	public ResultMessage getUserDataById(@Define("userId") String userId) {
		UserData userData = userService.getUserDataById(userId);
		UserHead userHead = userHeadService.getUserHeadByUserId(userId);
		userService.setUserStatus(userData);
		ResultMessage message = new ResultMessage();
		message.put("userData", userData);
		message.put("userHead", userHead);
		return message;
	}

	/**
	 * 条件查询用户
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0006")
	public ResultMessage queryUserDataList(
			@Define("userQuery") UserQuery userQuery,
			@Define("page") PageData page) {

		DefaultPage defaultPage = new DefaultPage();
		defaultPage.setPageNumber(page.getPageNumber());
		defaultPage.setPageSize(page.getPageSize());

		List<UserData> userDataList = userService.queryUserDataList(userQuery, defaultPage);
		userService.setUserStatus(userDataList);

		List<String> ids=new ArrayList<String>();
		
		for(UserData d:userDataList){
			ids.add(d.getId());
		}
		List<UserHead> userHeadList=userHeadService.getUserHeadListByUserIds(ids);
		
		page.setTotalCount(defaultPage.getTotalCount());
		page.setTotalPage(defaultPage.getTotalPage());

		ResultMessage message = new ResultMessage();
		message.put("userHeadList", userHeadList);
		message.put("userDataList", userDataList);
		message.put("page", page);
		return message;
	}

	/**
	 * 发送状态改变
	 * 
	 * @author XiaHui
	 * @date 2017年6月2日 上午11:39:10
	 * @param socketSession
	 * @param head
	 * @param status
	 */
	@MethodMapping(value = "1.1.0008")
	public void sendUserStatus(SocketSession socketSession, Head head, @Define("status") String status) {
		String userId = socketSession.getKey();
		List<UserCategoryMember> memberList = userService.getInUserCategoryMemberList(userId);
		List<UserData> userDataList = userService.getGroupMemberUserDataListByUserId(userId);

		Map<String, String> map = new HashMap<String, String>();
		for (UserCategoryMember ucm : memberList) {
			map.put(ucm.getOwnUserId(), ucm.getOwnUserId());
		}
		for (UserData ud : userDataList) {
			map.put(ud.getId(), ud.getId());
		}
		List<String> keyList = new ArrayList<String>(map.values());
		userPush.pushUserStatus(head.getKey(), userId, status, keyList);
		userBaseService.putUserStatus(userId, status);
	}

	/**
	 * 用户信息修改后发送给好友（只发id）
	 * 
	 * @author XiaHui
	 * @date 2017年6月2日 上午11:38:23
	 * @param socketSession
	 * @param head
	 */
	@MethodMapping(value = "1.1.0009")
	public void sendUserUpdate(SocketSession socketSession, Head head) {
		String userId = socketSession.getKey();
		List<UserCategoryMember> memberList = userService.getInUserCategoryMemberList(userId);
		List<UserData> userDataList = userService.getGroupMemberUserDataListByUserId(userId);

		Map<String, String> map = new HashMap<String, String>();
		for (UserCategoryMember ucm : memberList) {
			map.put(ucm.getOwnUserId(), ucm.getOwnUserId());
		}
		for (UserData ud : userDataList) {
			map.put(ud.getId(), ud.getId());
		}
		List<String> keyList = new ArrayList<String>(map.values());
		userPush.pushUserUpdate(head, userId, keyList);
	}

	/**
	 * 用户信息修改后，发送给好友（全部信息）
	 * 
	 * @author XiaHui
	 * @date 2017年6月2日 上午11:37:54
	 * @param socketSession
	 * @param head
	 */
	@MethodMapping(value = "1.1.0010")
	public void sendUserDataUpdate(SocketSession socketSession, Head head) {
		String userId = socketSession.getKey();
		List<UserCategoryMember> memberList = userService.getInUserCategoryMemberList(userId);
		List<UserData> userDataList = userService.getGroupMemberUserDataListByUserId(userId);

		Map<String, String> map = new HashMap<String, String>();
		for (UserCategoryMember ucm : memberList) {
			map.put(ucm.getOwnUserId(), ucm.getOwnUserId());
		}
		for (UserData ud : userDataList) {
			map.put(ud.getId(), ud.getId());
		}
		List<String> keyList = new ArrayList<String>(map.values());
		UserData userData = userService.getUserDataById(userId);
		userService.setUserStatus(userData);
		userPush.pushUserUpdate(head, userData, keyList);
	}

	/**
	 * 获取好友状态
	 * 
	 * @author XiaHui
	 * @date 2017年6月2日 上午11:37:42
	 * @param socketSession
	 * @return
	 */
	@MethodMapping(value = "1.1.0011")
	public ResultMessage getUserDataStatusList(SocketSession socketSession) {
		String userId = socketSession.getKey();
		List<UserData> categoryUserDataList = userService.getUserCategoryMemberUserDataList(userId);
		List<UserData> groupUserDataList = userService.getGroupMemberUserDataListByUserId(userId);
		userService.setUserStatus(categoryUserDataList);
		userService.setUserStatus(groupUserDataList);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (UserData userData : categoryUserDataList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", userData.getId());
			map.put("status", userData.getStatus());
			list.add(map);
		}
		for (UserData userData : groupUserDataList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", userData.getId());
			map.put("status", userData.getStatus());
			list.add(map);
		}
		ResultMessage message = new ResultMessage();
		message.put("list", list);
		return message;
	}

	@MethodMapping(value = "1.1.0012")
	public ResultMessage getUserHead(@Define("userId") String userId) {
		UserHead userHead = userHeadService.getUserHeadByUserId(userId);
		ResultMessage message = new ResultMessage();
		message.put("userHead", userHead);
		return message;
	}

	@MethodMapping(value = "1.1.0013")
	public ResultMessage getUserHeadList(@Define("userId") String userId) {
		List<UserHead> headList = userHeadService.getUserCategoryMemberUserHeadListByUserId(userId);
		ResultMessage message = new ResultMessage();
		message.put("headList", headList);
		return message;
	}
	
	/**
	 * 获取用户被加为好的权限设置
	 * @author XiaHui
	 * @date 2017-11-24 11:04:21
	 * @param userId
	 * @return
	 */
	@MethodMapping(value = "1.1.0014")
	public ResultMessage getUserAddRequestType(@Define("userId") String userId) {
		
		ResultMessage message = new ResultMessage();
		message.put("type", "");
		message.put("type", "");
		return message;
	}
	
	/**
	 * 请求添加为好友
	 * @author XiaHui
	 * @date 2017-11-24 11:10:04
	 * @param userId
	 * @return
	 */
	@MethodMapping(value = "1.1.0015")
	public ResultMessage sendUserAddRequest(
			@Define("userId") String userId) {
		
		ResultMessage message = new ResultMessage();
		message.put("type", "");
		return message;
	}
}
