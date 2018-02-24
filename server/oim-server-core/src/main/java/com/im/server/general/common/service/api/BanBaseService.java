package com.im.server.general.common.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.common.data.BanUserChat;
import com.im.server.general.common.data.BanRoomChat;

/**
 * @author: XiaHui
 * @date: 2016年8月18日 上午10:53:49
 */
@Service
public class BanBaseService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	private Map<String, BanRoomChat> banRoomChatMap = new ConcurrentSkipListMap<String, BanRoomChat>();
	// private Map<String, BanUserChat> banChatMap = new
	// ConcurrentSkipListMap<String, BanUserChat>();
	//
	// private String key_address = "address_";
	// private String key_id = "address_id_";

	private Map<String, Map<String, BanUserChat>> roomBanAddressMap = new ConcurrentSkipListMap<String, Map<String, BanUserChat>>();
	private Map<String, Map<String, BanUserChat>> roomBanIdMap = new ConcurrentSkipListMap<String, Map<String, BanUserChat>>();

	// public void setBanChatAddress(String address, long banTime) {
	// long startTime = System.currentTimeMillis();
	// long endTime = startTime + banTime;
	// BanUserChat bc = new BanUserChat();
	// bc.setEndTime(endTime);
	// bc.setStartTime(startTime);
	// bc.setBanTime(banTime);
	// banChatMap.put(key_address + address, bc);
	//
	// }

	// public void setBanChatId(String id, long banTime) {
	// long startTime = System.currentTimeMillis();
	// long endTime = startTime + banTime;
	// BanUserChat bc = new BanUserChat();
	// bc.setEndTime(endTime);
	// bc.setStartTime(startTime);
	// bc.setBanTime(banTime);
	// banChatMap.put(key_id + id, bc);
	// }

	public BanRoomChat setBanRoomChat(String roomId, List<String> excludeUserIds, List<String> excludeAddresses, long banTime) {
		long startTime = System.currentTimeMillis();
		long endTime = startTime + banTime;

		BanRoomChat brc = new BanRoomChat();
		brc.setBanTime(banTime);
		brc.setStartTime(startTime);
		brc.setEndTime(endTime);

		if (null != excludeUserIds && !excludeUserIds.isEmpty()) {
			for (String userId : excludeUserIds) {
				brc.putExcludeUserId(userId);
			}
		}

		if (null != excludeAddresses && !excludeAddresses.isEmpty()) {
			for (String address : excludeAddresses) {
				brc.putExcludeAddress(address);
			}
		}
		banRoomChatMap.put(roomId, brc);
		return brc;
	}

	public BanRoomChat removeBanRoomChat(String roomId) {
		return banRoomChatMap.remove(roomId);
	}

	public BanRoomChat getBanRoomChat(String roomId) {
		BanRoomChat bc = banRoomChatMap.get(roomId);
		if (null != bc) {
			if (bc.getBanTime() > 0 && bc.getEndTime() < System.currentTimeMillis()) {
				banRoomChatMap.remove(roomId);
				bc = null;
			}
		}
		return bc;
	}

	public BanUserChat getBanChatById(String roomId, String id) {
		BanUserChat bc = null;
		Map<String, BanUserChat> banIdChatMap = roomBanIdMap.get(roomId);
		if (null != banIdChatMap) {
			bc = banIdChatMap.get(id);
			if (null != bc) {
				if (bc.getBanTime() > 0 && bc.getEndTime() <= System.currentTimeMillis()) {
					banIdChatMap.remove(id);
					bc = null;
				}
			}
		}
		return bc;
	}

	public BanUserChat getBanChatByAddress(String roomId, String address) {
		BanUserChat bc = null;
		Map<String, BanUserChat> banAddressChatMap = roomBanAddressMap.get(roomId);
		if (null != banAddressChatMap) {
			bc = banAddressChatMap.get(address);
			if (null != bc) {
				if (bc.getBanTime() > 0 && bc.getEndTime() <= System.currentTimeMillis()) {
					banAddressChatMap.remove(address);
					bc = null;
				}
			}
		}
		return bc;
	}

	public void add(String roomId, String userId, long banTime) {
		if (StringUtils.isNotBlank(roomId) && StringUtils.isNotBlank(userId)) {
			Map<String, BanUserChat> banIdChatMap = roomBanIdMap.get(roomId);
			if (null == banIdChatMap) {
				banIdChatMap = new ConcurrentSkipListMap<String, BanUserChat>();
				roomBanIdMap.put(roomId, banIdChatMap);
			}
			long startTime = System.currentTimeMillis();
			long endTime = startTime + banTime;
			BanUserChat bc = new BanUserChat();
			bc.setEndTime(endTime);
			bc.setStartTime(startTime);
			bc.setBanTime(banTime);
			bc.setUserId(userId);
			bc.setRoomId(roomId);
			banIdChatMap.put(userId, bc);
		}
	}

	public void add(String roomId, String address, String userId, long banTime) {
		if (StringUtils.isNotBlank(roomId) && StringUtils.isNotBlank(userId)) {
			Map<String, BanUserChat> banAddressChatMap = roomBanAddressMap.get(roomId);
			if (null == banAddressChatMap) {
				banAddressChatMap = new ConcurrentSkipListMap<String, BanUserChat>();
				roomBanAddressMap.put(roomId, banAddressChatMap);
			}
			long startTime = System.currentTimeMillis();
			long endTime = startTime + banTime;
			BanUserChat bc = new BanUserChat();
			bc.setEndTime(endTime);
			bc.setStartTime(startTime);
			bc.setBanTime(banTime);
			bc.setUserId(userId);
			bc.setAddress(address);
			bc.setRoomId(roomId);
			banAddressChatMap.put(address, bc);
		}
	}

	public List<BanUserChat> getBanIdList(String roomId) {
		List<BanUserChat> list = new ArrayList<BanUserChat>();
		if (StringUtils.isNotBlank(roomId)) {
			Map<String, BanUserChat> banIdChatMap = roomBanIdMap.get(roomId);
			if (null != banIdChatMap) {
				list.addAll(banIdChatMap.values());
			}
		} else {
			for (Map<String, BanUserChat> banIdChatMap : roomBanIdMap.values()) {
				list.addAll(banIdChatMap.values());
			}
		}
		return list;
	}

	public List<BanUserChat> getBanAddressList(String roomId) {

		List<BanUserChat> list = new ArrayList<BanUserChat>();
		if (StringUtils.isNotBlank(roomId)) {
			Map<String, BanUserChat> banIdChatMap = roomBanAddressMap.get(roomId);
			if (null != banIdChatMap) {
				list.addAll(banIdChatMap.values());
			}
		} else {
			for (Map<String, BanUserChat> banIdChatMap : roomBanAddressMap.values()) {
				list.addAll(banIdChatMap.values());
			}
		}
		return list;
	}
}
