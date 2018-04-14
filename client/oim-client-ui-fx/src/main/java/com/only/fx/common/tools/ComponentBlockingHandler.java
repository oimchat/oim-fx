package com.only.fx.common.tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiaHui
 * @date 2017-12-22 13:57:38
 */
public class ComponentBlockingHandler {

	private ConcurrentHashMap<String, BlockingQueue<?>> map = new ConcurrentHashMap<String, BlockingQueue<?>>();

	public <T> T get(String key) {
		BlockingQueue<T> q = getQueue(key);
		T t = null;
		try {
			t = q.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			map.remove(key);
		}
		return t;
	}

	public <T> void put(String key, T data) {
		BlockingQueue<T> q = getQueue(key);
		if (null != q) {
			q.add(data);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> BlockingQueue<T> getQueue(String key) {
		BlockingQueue<T> q = (BlockingQueue<T>) map.get(key);
		// synchronized(q) {
		// }
		if (null == q) {
			q = new ArrayBlockingQueue<T>(1);
			map.put(key, q);
		}
		return q;
	}
}
