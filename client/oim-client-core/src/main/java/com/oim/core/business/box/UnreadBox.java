package com.oim.core.business.box;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.common.util.MapUtil;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;

/**
 * @author XiaHui
 * @date 2018-01-06 21:18:50
 */
public class UnreadBox extends AbstractBox {

	private Map<String, Integer> groupUnreadCountMap = new ConcurrentHashMap<String, Integer>();
	private Map<String, Integer> userUnreadCountMap = new ConcurrentHashMap<String, Integer>();

	private volatile int totalUnreadCount = 0;

	public UnreadBox(AppContext appContext) {
		super(appContext);
	}

	public void plusUserUnread(String userId) {
		int count = MapUtil.getOrDefault(userUnreadCountMap, userId, 0);// 获取聊天对象未读消息数量
		count++;
		userUnreadCountMap.put(userId, count);
		totalUnread();
	}

	public void setUserUnreadCount(String userId, int count) {
		int oldCount = MapUtil.getOrDefault(userUnreadCountMap, userId, 0);
		userUnreadCountMap.put(userId, count);
		int newTotalCount = totalUnreadCount - oldCount;
		if (newTotalCount >= 0) {
			totalUnreadCount = newTotalCount;
		}
	}

	public int getUserUnreadCount(String userId) {
		int count = MapUtil.getOrDefault(userUnreadCountMap, userId, 0);
		return count;
	}

	/************************* group ***************************/

	public void plusGroupUnread(String groupId) {
		int count = MapUtil.getOrDefault(groupUnreadCountMap, groupId, 0);// 获取聊天对象未读消息数量
		count++;
		groupUnreadCountMap.put(groupId, count);
		totalUnread();
	}

	public void setGroupUnreadCount(String groupId, int count) {
		int oldCount = MapUtil.getOrDefault(groupUnreadCountMap, groupId, 0);
		groupUnreadCountMap.put(groupId, count);
		int newTotalCount = totalUnreadCount - oldCount;
		if (newTotalCount >= 0) {
			totalUnreadCount = newTotalCount;
		}
	}

	public int getGroupUnreadCount(String groupId) {
		int count = MapUtil.getOrDefault(groupUnreadCountMap, groupId, 0);
		return count;
	}

	public int getTotalUnreadCount() {
		return totalUnreadCount;
	}

	private void totalUnread() {
		totalUnreadCount++;
	}
}
