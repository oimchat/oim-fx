package com.im.server.general.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.data.BanUserChat;
import com.im.server.general.common.data.BanRoomChat;
import com.im.server.general.common.service.api.BanBaseService;
import com.im.server.general.common.service.api.RoomBaseService;
import com.im.server.general.common.service.api.UserBaseService;
import com.im.server.general.manage.data.BanChatQuery;
import com.im.server.general.business.push.RoomPush;
import com.only.query.page.QueryPage;
import com.onlyxiahui.im.message.data.UserData;

/**
 * @author: XiaHui
 * @date: 2016年8月18日 下午1:55:58
 */
@Service
public class BanService {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	BanBaseService banBaseService;
	@Resource
	RoomBaseService roomBaseService;
	@Resource
	RoomPush roomPush;
	@Resource
	UserBaseService userBaseService;

	public void banChatUserId(String roomId, String userId, String address, long banTime, int type) {
		if (1 == type) {
			banBaseService.add(roomId, userId, banTime);
		} else if (2 == type) {
			banBaseService.add(roomId, address, userId, banTime);
		} else {
			banBaseService.add(roomId, userId, banTime);
			banBaseService.add(roomId, address, userId, banTime);
		}
		roomPush.pushRoomUserBan(roomId, userId, true, banTime);
	}

	public void banRoomChat(String roomId, List<String> excludeUserIds, List<String> excludeAddresses, long banTime) {
		BanRoomChat brc = banBaseService.setBanRoomChat(roomId, excludeUserIds, excludeAddresses, banTime);
		pushRoomBan(roomId, brc, true);
	}

	public void relieveBanRoomChat(String roomId) {
		BanRoomChat brc = banBaseService.removeBanRoomChat(roomId);
		pushRoomBan(roomId, brc, false);
	}

	private void pushRoomBan(String roomId, BanRoomChat banRoomChat, boolean isBan) {
		List<String> userIdList = roomBaseService.getUserIdList(roomId);
		List<String> idList = new ArrayList<String>();
		for (String userId : userIdList) {
			if (null == banRoomChat || !banRoomChat.isExcludeUserId(userId)) {
				idList.add(userId);
			}
		}
		roomPush.pushRoomBan(roomId, isBan, idList);
	}

	public boolean isRoomBan(String roomId) {
		boolean isBan = false;
		BanRoomChat brc = banBaseService.getBanRoomChat(roomId);
		isBan = (null != brc);
		return isBan;
	}

	public boolean isRoomBan(String roomId, String userId, String address) {
		boolean isBan = false;
		BanRoomChat brc = banBaseService.getBanRoomChat(roomId);
		if (null != brc) {
			boolean isExcludeAddress = brc.isExcludeAddress(address);
			boolean isExcludeUserId = brc.isExcludeUserId(userId);
			isBan = (!isExcludeAddress && !isExcludeUserId);// ip和id都没有被排除，那么就被禁言了
		}
		if (isBan) {
			List<String> idList = new ArrayList<String>();
			idList.add(userId);
			roomPush.pushRoomBan(roomId, isBan, idList);
		}
		return isBan;
	}

	public boolean isBan(String roomId, String userId) {
		BanUserChat ibc = banBaseService.getBanChatById(roomId, userId);// id被禁言了
		boolean isBan = ((null != ibc));// 只要其中一个被禁言了就是被禁言了
		return isBan;
	}

	public boolean isBan(String roomId, String userId, String address) {
		boolean isBan = false;

		// long banTime = 0;// 禁止时间长度
		// long startTime;// 开始禁止的时间
		// long endTime = 0;// 到期时间

		BanUserChat abc = banBaseService.getBanChatByAddress(roomId, address);// 地址被禁言了
		BanUserChat ibc = banBaseService.getBanChatById(roomId, userId);// id被禁言了

		isBan = ((null != abc) || (null != ibc));// 只要其中一个被禁言了就是被禁言了
		// if (null != abc && null != ibc) {
		// if (abc.getEndTime() > ibc.getEndTime()) {
		// banTime = abc.getBanTime();// 禁止时间长度
		// // startTime = abc.getStartTime();// 开始禁止的时间
		// endTime = abc.getStartTime();// 到期时间
		// } else {
		// banTime = ibc.getBanTime();// 禁止时间长度
		// // startTime = ibc.getStartTime();// 开始禁止的时间
		// endTime = ibc.getStartTime();// 到期时间
		// }
		// } else if (null != abc) {
		// banTime = abc.getBanTime();// 禁止时间长度
		// // startTime = abc.getStartTime();// 开始禁止的时间
		// endTime = abc.getStartTime();// 到期时间
		// } else if (null != ibc) {
		// banTime = ibc.getBanTime();// 禁止时间长度
		// // startTime = ibc.getStartTime();// 开始禁止的时间
		// endTime = ibc.getStartTime();// 到期时间
		// }

		// if (isBan) {
		// long time = endTime - System.currentTimeMillis();
		// long second = time / 1000;
		// long minute = second / 60;
		//
		// long banSecond = banTime / 1000;
		// long banMinute = banSecond / 60;
		//
		// String text = "您已被禁言";
		// if (banMinute > 0) {
		// text += (banMinute + "分钟！");
		// } else {
		// text += ("！");
		// }
		//
		// if (minute > 0) {
		// text += (minute + "分钟后将会解除禁言。");
		// }
		// }
		return isBan;
	}

	public List<Map<String, Object>> queryRoomBanUserList(int type, BanChatQuery banChatQuery, QueryPage page) {

		String roomId = banChatQuery.getRoomId();
		// String nickname = banChatQuery.getNickname();
		// String userId = banChatQuery.getUserId();

		List<BanUserChat> list;
		if (1 == type) {
			list = banBaseService.getBanIdList(roomId);
		} else {
			list = banBaseService.getBanAddressList(roomId);
		}

		int size = list.size();

		page.setTotalCount(size);

		int start = page.getStartResult();
		int pageSize = page.getPageSize();

		int end = start + pageSize;

		if (start > (size - 1)) {
			if ((size - 1) < 0) {
				start = 0;
			} else {
				start = (size - 1);
			}
		}

		if (end > (size)) {
			end = (size);
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = start; i < end; i++) {
			BanUserChat bc = list.get(i);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String id = bc.getUserId();
			UserData userData = userBaseService.getUserData(id);
			dataMap.put("banData", bc);
			dataMap.put("userData", userData);
			dataList.add(dataMap);
		}
		return dataList;
	}
}
