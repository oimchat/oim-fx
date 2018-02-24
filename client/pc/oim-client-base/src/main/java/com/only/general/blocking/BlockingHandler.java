package com.only.general.blocking;

import java.util.concurrent.TimeUnit;

/**
 * 阻塞异步转同步操作
 * 
 * @author XiaHui
 * @date 2017-11-20 11:59:42
 */
public interface BlockingHandler<T> {

	T get(String key);

	T get(String key, long timeOut);

	T get(String key, long timeOut, TimeUnit unit);

	void put(String key, T data);
}
