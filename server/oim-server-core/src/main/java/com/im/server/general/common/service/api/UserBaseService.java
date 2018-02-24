package com.im.server.general.common.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.dao.UserDAO;
import com.onlyxiahui.im.message.data.UserData;
import com.im.server.general.business.box.RoomBox;
import com.im.server.general.business.box.UserDataBox;

/**
 * @author Only
 * @date 2016年5月24日 上午10:15:52
 */
@Service
public class UserBaseService {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	UserDAO userDAO;
	private Map<String, String> userStatusMap = new ConcurrentSkipListMap<String, String>();

	/**
	 * 放入用户信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:21
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:21
	 */
	public void put(String userId, UserData userData) {
		UserDataBox.put(userId, userData);
	}

	/**
	 * 获取用户信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:29
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:29
	 */
	public UserData get(String userId) {
		UserData u = UserDataBox.get(userId);
		if (null == u) {
			u = userDAO.getUserDataById(userId);
		}
		return u;
	}

	/**
	 * 获取所有用户
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:07:38
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:07:38
	 */
	public List<UserData> getAll() {
		return UserDataBox.getAll();
	}

	public UserData remove(String userId) {
		UserData u = UserDataBox.remove(userId);
		userStatusMap.remove(userId);
		return u;
	}

	public UserData getUserData(String userId) {
		UserData u = UserDataBox.get(userId);
		if (null == u) {
			u = userDAO.getUserDataById(userId);
		}
		return u;
	}

	public List<UserData> getUserDataList(List<String> userIdList) {
		List<UserData> list = new ArrayList<UserData>();
		for (String userId : userIdList) {
			UserData u = UserDataBox.get(userId);
			list.add(u);
		}
		return list;
	}

	public List<UserData> getUserDataListByRoomId(String roomId) {
		List<String> userIdList = RoomBox.getUserIdList(roomId);
		List<UserData> list = getUserDataList(userIdList);
		return list;
	}

	public void putUserStatus(String userId, String status) {
		userStatusMap.put(userId, status);
	}

	public String getUserStatus(String userId) {
		return userStatusMap.get(userId);
	}
}
