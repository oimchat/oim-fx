/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.common.util;

import java.util.UUID;

/**
 * 
 * @author XiaHui
 */
public class KeyUtil {
	
	static long i = 0;

	public static String getKey() {
		return UUID.randomUUID().toString();
	}

	public static long getNumber() {
		i++;
		return System.currentTimeMillis() + i;
	}
}
