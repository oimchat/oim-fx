package com.oim.test;

import java.io.File;

/**
 * @author XiaHui
 * @date 2017年9月21日 下午7:19:55
 */
public class PathSTest {

	public static void main(String[] a) {
		String RESOURCES_DIR = System.getProperty("app.dir", System.getProperty("user.dir")) + File.separator + "resources" + File.separator;
		System.out.println(RESOURCES_DIR);
	}
}
