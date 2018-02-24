package com.im.server.general.common.service.api;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.business.box.RoomBox;

/**
 * @author Only
 * @date 2016年5月24日 上午10:15:52
 */
@Service
public class RoomBaseService {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	public void put(String roomId, String userId) {
		RoomBox.put(roomId, userId);
	}
	
	public void remove(String roomId, String userId) {
		RoomBox.remove(roomId, userId);
	}

	public List<String> getUserIdList(String roomId) {
		return RoomBox.getUserIdList(roomId);
	}

	public ConcurrentSkipListSet<String> getUserIdSet(String roomId) {
		return RoomBox.getUserIdSet(roomId);
	}

	public List<String> removeUser(String userId) {
		return RoomBox.removeUser(userId);
	}

	public List<String> getUserJoinRoomIdList(String userId) {
		return RoomBox.getUserJoinRoomIdList(userId);
	}
}
