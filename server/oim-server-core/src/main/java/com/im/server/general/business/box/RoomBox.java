package com.im.server.general.business.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Only
 * @date 2016年5月24日 上午9:28:08
 */
public class RoomBox {
	// 存放用户加入聊天室的key
	private static Map<String, CopyOnWriteArraySet<String>> userJoinRoomMap = new ConcurrentSkipListMap<String, CopyOnWriteArraySet<String>>();
	// 存放聊天室中的用户key
	private static Map<String, ConcurrentSkipListSet<String>> roomUserKeyMap = new ConcurrentSkipListMap<String, ConcurrentSkipListSet<String>>();

	public static void remove(String roomId, String userId) {
		ConcurrentSkipListSet<String> userSet = roomUserKeyMap.get(roomId);
		if (null != userSet) {
			userSet.remove(userId);
		}
		CopyOnWriteArraySet<String> roomSet = userJoinRoomMap.get(userId);
		if (null != roomSet) {
			roomSet.remove(roomId);
		}
	}

	public static void put(String roomId, String userId) {
		ConcurrentSkipListSet<String> set = roomUserKeyMap.get(roomId);
		if (null == set) {
			set = new ConcurrentSkipListSet<String>();
			roomUserKeyMap.put(roomId, set);
		}
		set.add(userId);
		putUserJoinRoom(userId, roomId);
	}

	public static List<String> getUserIdList(String roomId) {
		List<String> list = new ArrayList<String>();
		ConcurrentSkipListSet<String> set = roomUserKeyMap.get(roomId);
		if (null != set) {
			list.addAll(set);
		}
		return list;
	}

	public static ConcurrentSkipListSet<String> getUserIdSet(String roomId) {
		ConcurrentSkipListSet<String> set = roomUserKeyMap.get(roomId);
		return set;
	}

	private static void putUserJoinRoom(String userId, String roomId) {
		CopyOnWriteArraySet<String> set = userJoinRoomMap.get(userId);
		if (null == set) {
			set = new CopyOnWriteArraySet<String>();
			userJoinRoomMap.put(userId, set);
		}
		set.add(roomId);
	}

	public static List<String> removeUser(String userId) {
		List<String> list = new ArrayList<String>();
		CopyOnWriteArraySet<String> roomKeySet = userJoinRoomMap.remove(userId);
		if (null != roomKeySet) {
			for (String roomId : roomKeySet) {
				ConcurrentSkipListSet<String> userKeySet = roomUserKeyMap.get(roomId);
				if (null != userKeySet) {
					userKeySet.remove(userId);
				}
				list.add(roomId);
			}
		}
		return list;
	}

	public static List<String> getUserJoinRoomIdList(String userId) {
		List<String> list = new ArrayList<String>();
		CopyOnWriteArraySet<String> roomKeySet = userJoinRoomMap.get(userId);
		if (null != roomKeySet) {
			for (String roomId : roomKeySet) {
				list.add(roomId);
			}
		}
		return list;
	}
}
