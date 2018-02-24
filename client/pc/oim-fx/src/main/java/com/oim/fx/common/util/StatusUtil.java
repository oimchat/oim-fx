package com.oim.fx.common.util;

import com.onlyxiahui.im.bean.UserData;

/**
 * @author: XiaHui
 * @date: 2017年4月10日 下午4:40:36
 */
public class StatusUtil {

	public static String getStatusName(String status){
		String name="离线";
		switch (status) {
		case UserData.status_online:
			name="在线";
			break;
		case UserData.status_call_me:
			name="Call我";
			break;
		case UserData.status_away:
			name="离开";
			break;
		case UserData.status_busy:
			name="忙碌";
			break;
		case UserData.status_mute:
			name="勿扰";
			break;
		case UserData.status_invisible:
			name="离线";
			break;
		case UserData.status_offline:
			name="离线";
			break;
		default:
			name="";
			break;
		}
		return name;
	}
}
