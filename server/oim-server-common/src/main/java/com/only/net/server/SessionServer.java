package com.only.net.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.only.net.server.DataQueueItem.Type;
import com.only.net.session.SocketSession;

public class SessionServer {

	private final ConcurrentLinkedQueue<DataQueueItem> queue = new ConcurrentLinkedQueue<DataQueueItem>();
	private ExecutorService pool;

	private final Map<String, CopyOnWriteArraySet<SocketSession>> sessionMap = new ConcurrentHashMap<String, CopyOnWriteArraySet<SocketSession>>();
	private final CopyOnWriteArraySet<SocketSession> sessionSet = new CopyOnWriteArraySet<SocketSession>();

	private boolean singleSession = true;
	
	public SessionServer() {
		initialize(3);
	}

	public SessionServer(int count) {
		initialize(count);
	}

	private void initialize(int count) {
		pool = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			pool.execute(new DataHandlerThread(this));
		}
	}

	public boolean isEmpty() {
		return queue.peek() == null;
	}

	public DataQueueItem poll() {
		return queue.poll();
	}

	public boolean isSingleSession() {
		return singleSession;
	}

	public void setSingleSession(boolean singleSession) {
		this.singleSession = singleSession;
	}

	/**
	 * 存放用户连接的Session
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:05:45
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:05:45
	 */
	public void put(String key, SocketSession socketSession) {
		CopyOnWriteArraySet<SocketSession> set = sessionMap.get(key);
		if (set == null) {
			set = new CopyOnWriteArraySet<SocketSession>();
			sessionMap.put(key, set);
		}
		if (isSingleSession() && !set.isEmpty()) {
			for (SocketSession session : set) {
				sessionSet.remove(session);
			}
			set.clear();
		}
		set.add(socketSession);
		sessionSet.add(socketSession);
	}

	public void put(SocketSession socketSession) {
		if (null != socketSession.getKey()) {
			put(socketSession.getKey(), socketSession);
		}
	}
	
	/**
	 * 移除已经下线或者异常的用连接Session
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:06:08
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:06:08
	 */
	public CopyOnWriteArraySet<SocketSession> remove(String key) {
		CopyOnWriteArraySet<SocketSession> set = null;
		if (null != key) {
			set = sessionMap.remove(key);
			if (null != set) {
				for (SocketSession session : set) {
					sessionSet.remove(session);
				}
			}
		}
		return set;
	}

	/***
	 * 移除Session
	 *
	 * @author: XiaHui
	 * @createDate: 2016年8月25日 下午2:06:41
	 * @update: XiaHui
	 * @updateDate: 2016年8月25日 下午2:06:41
	 */
	public void remove(SocketSession socketSession) {
		sessionSet.remove(socketSession);
		String key = socketSession.getKey();
		if (null != key) {
			CopyOnWriteArraySet<SocketSession> set = sessionMap.get(key);
			if (null != set) {
				set.remove(socketSession);
				if (set.isEmpty()) {
					sessionMap.remove(key);
				}
			}
		}
	}

	/**
	 * 判断用户是否还有连接
	 * 
	 * @author: XiaHui
	 * @createDate: 2016年8月17日 下午5:51:44
	 * @update: XiaHui
	 * @updateDate: 2016年8月17日 下午5:51:44
	 */
	public boolean hasSession(String key) {
		boolean has = true;
		CopyOnWriteArraySet<SocketSession> set = sessionMap.get(key);
		has = (null != set && !set.isEmpty());
		return has;
	}

	public CopyOnWriteArraySet<SocketSession> get(String key) {
		return sessionMap.get(key);
	}

	public CopyOnWriteArraySet<SocketSession> getAll() {
		return sessionSet;
	}

	public void push(DataQueueItem messageQueue) {
		queue.add(messageQueue);
	}

	public void push(Object message) {
		queue.add(new DataQueueItem(Type.all, "", message));
	}

	public void push(String key, Object data) {
		queue.add(new DataQueueItem(Type.one, key, data));
	}

	public void push(List<String> keyList, Object data) {
		queue.add(new DataQueueItem(Type.list, keyList, data));
	}

	public void pushWithout(String key, Object data) {
		queue.add(new DataQueueItem(Type.without, key, data));
	}

	public void pushWithout(List<String> keyList, Object data) {
		queue.add(new DataQueueItem(Type.without, keyList, data));
	}

}
