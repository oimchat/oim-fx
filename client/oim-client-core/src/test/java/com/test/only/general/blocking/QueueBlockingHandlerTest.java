package com.test.only.general.blocking;

import java.util.concurrent.TimeUnit;

import com.only.common.util.OnlyDateUtil;
import com.only.general.blocking.QueueBlockingHandler;

/**
 * @author XiaHui
 * @date 2017-11-20 14:33:26
 */
public class QueueBlockingHandlerTest {

	public static void main(String[] args) {
		final QueueBlockingHandler<String> qbh = new QueueBlockingHandler<String>();
		Thread run = new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000L * 5L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				qbh.put("1000", "给你了");
			}
		};
		run.start();
		qbh.setTimeOut(1000L * 5L, TimeUnit.MILLISECONDS);
		System.out.println(OnlyDateUtil.getCurrentDateTime() + ":开始要了");
		String value = qbh.get("1000", 1000L * 3);
		System.out.println(OnlyDateUtil.getCurrentDateTime() + ":" + value);
	}
}
