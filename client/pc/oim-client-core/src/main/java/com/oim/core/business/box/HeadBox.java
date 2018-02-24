package com.oim.core.business.box;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.bean.GroupHead;
import com.onlyxiahui.im.bean.UserHead;

/**
 * @author XiaHui
 * @date 2017年6月18日 下午9:32:36
 */
public class HeadBox extends AbstractBox {

	private Map<String, UserHead> userHeadMap = new ConcurrentHashMap<String, UserHead>();
	private Map<String, GroupHead> groupHeadMap = new ConcurrentHashMap<String, GroupHead>();

	private Map<String, String> userHeadPathMap = new ConcurrentHashMap<String, String>();
	private Map<String, String> groupHeadPathMap = new ConcurrentHashMap<String, String>();

	public HeadBox(AppContext appContext) {
		super(appContext);
	}

	public void setUserHeadList(List<UserHead> list) {
		if (null != list) {
			for (UserHead h : list) {
				putUserHead(h);
			}
		}
	}

	public void putUserHead(UserHead userHead) {
		userHeadMap.put(userHead.getUserId(), userHead);
	}

	public UserHead getUserHead(String userId) {
		return userHeadMap.get(userId);
	}

	public void setGroupHeadList(List<GroupHead> list) {
		if (null != list) {
			for (GroupHead h : list) {
				putGroupHead(h);
			}
		}
	}

	public void putGroupHead(GroupHead groupHead) {
		groupHeadMap.put(groupHead.getGroupId(), groupHead);
	}

	public GroupHead getGroupHead(String groupId) {
		return groupHeadMap.get(groupId);
	}

	public void putUserHeadPath(String userId, String absolutePath) {
		userHeadPathMap.put(userId, absolutePath);
	}

	public void putGroupHeadPath(String groupId, String absolutePath) {
		groupHeadPathMap.put(groupId, absolutePath);
	}

	public String getUserHeadPath(String userId) {
		String path = userHeadPathMap.get(userId);
		return path;
	}

	public String getGroupHeadPath(String groupId) {
		String path = groupHeadPathMap.get(groupId);
		return path;
	}
}
