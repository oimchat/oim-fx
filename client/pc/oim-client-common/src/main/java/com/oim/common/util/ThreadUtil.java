package com.oim.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2015年3月14日 下午4:32:27
 * @version 0.0.1
 */
public class ThreadUtil {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(10);

	public static void execute(Runnable runnable) {
		pool.execute(runnable);
	}
}
