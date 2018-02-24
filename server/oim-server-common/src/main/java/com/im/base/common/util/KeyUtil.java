/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.im.base.common.util;

import java.util.UUID;

/**
 * 
 * @author XiaHui
 */
public class KeyUtil {

	static Integer i = 0;

	public static String getKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static long getNumber(String key) {
		return System.nanoTime();
	}

	public static long getNumber() {
		synchronized (i) {
			i++;
		}
		return System.currentTimeMillis() + i;
	}

	public static void main(String s[]) {
		for (int i = 0; i < 3; i++) {
			new NumberThread().start();
		}
	}

	static class NumberThread extends Thread {
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println(getNumber());
			}
		}
	}
}
