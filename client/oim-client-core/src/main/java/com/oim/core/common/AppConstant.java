/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.common;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * 
 * @author xh
 */
public class AppConstant {

	// ////////////////////////////////////////
	public static String userHome = System.getProperty("user.home");
	public static String fileSeparate = System.getProperty("file.separator");
	public static final boolean debug = false;
	public static final String app_name = "oim";
	public static final String app_home_path = ".oim";
	public static final String version = "0.0.1";

	public static final String charset = "UTF-8";

	public static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screen_width = (int) screensize.getWidth();
	public static int screen_height = (int) screensize.getHeight();
	public static int window_width = screen_width - 100;
	public static int window_height = screen_height - 100;

	
	public static String getUserAppPath(){
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.home"));
		sb.append("/");
		sb.append(".oim");
		sb.append("/");
		return sb.toString();
	}
	
	public static String USER_LAST_CHAT_TYPE_USER = "1";
	public static String USER_LAST_CHAT_TYPE_GROUP = "2";
	public static String USER_LAST_CHAT_TYPE_ROOM = "3";
	public static String USER_LAST_CHAT_TYPE_TEAM = "4";
}
