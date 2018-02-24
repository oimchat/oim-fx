package com.oim.test.app;

import java.io.File;

/**
 * @author XiaHui
 * @date 2017-12-08 21:06:37
 */
public class AppPathTest {
	 public static final String RESOURCES_DIR = System.getProperty("app.dir",  System.getProperty("user.dir")) + File.separator + "resources" + File.separator;

	public static void main(String[] args) {
		System.out.println(RESOURCES_DIR);
		String path = System.getProperty("user.dir");
		System.out.println(path);
		String appPath = path + File.separator + "resources";
		System.out.println(appPath);
		File file = new File(appPath);
		if (file.exists()) {
			System.setProperty("app.dir", new File(path).getAbsolutePath());
		} else {
			// 去掉了最后一个main目录
			path = path.substring(0, path.lastIndexOf(File.separator));
			System.setProperty("app.dir", new File(path).getAbsolutePath());
		}
		String app=System.getProperty("app.dir");
		System.out.println(app);
		System.out.println(RESOURCES_DIR);
	}
}
