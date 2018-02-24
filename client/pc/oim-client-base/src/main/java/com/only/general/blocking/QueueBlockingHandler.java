package com.only.general.blocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaHui
 * @date 2017-11-20 12:04:57
 */
public class QueueBlockingHandler<T> implements BlockingHandler<T> {

	private ConcurrentHashMap<String, BlockingQueue<T>> map = new ConcurrentHashMap<String, BlockingQueue<T>>();
	long timeOut;
	TimeUnit timeUnit;

	@Override
	public T get(String key) {
		return get(key, this.getTimeOut());
	}

	@Override
	public T get(String key, long timeOut) {
		return get(key, timeOut, this.getTimeUnit());
	}

	@Override
	public T get(String key, long timeOut, TimeUnit unit) {
		BlockingQueue<T> q = getQueue(key);
		T t = null;
		try {
			if (timeOut <= 0 || null == unit) {
				t = q.take();
			} else {
				t = q.poll(timeOut, unit);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			map.remove(key);
		}
		return t;
	}

	@Override
	public void put(String key, T data) {
		BlockingQueue<T> q = map.get(key);
		if (null != q) {
			q.add(data);
		}
	}

	private BlockingQueue<T> getQueue(String key) {
		BlockingQueue<T> q = map.get(key);
		// synchronized(q) {
		// }
		if (null == q) {
			q = new ArrayBlockingQueue<T>(1);
			map.put(key, q);
		}
		return q;
	}

	public void setTimeOut(long timeOut, TimeUnit unit) {
		this.timeOut = timeOut;
		this.timeUnit = unit;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
}
