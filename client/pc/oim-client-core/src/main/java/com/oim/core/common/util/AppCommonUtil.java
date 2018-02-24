package com.oim.core.common.util;

import com.onlyxiahui.im.bean.UserData;

/**
 * @author: XiaHui
 * @date: 2016年10月11日 下午12:50:33
 */
public class AppCommonUtil {

	public static boolean isOffline(String status) {
		boolean gray = true;
		status = (null == status) ? UserData.status_offline : status;
		switch (status) {
		case UserData.status_online:
			gray = false;
			break;
		case UserData.status_call_me:
			gray = false;
			break;
		case UserData.status_away:
			gray = false;
			break;
		case UserData.status_busy:
			gray = false;
			break;
		case UserData.status_mute:
			gray = false;
			break;
		case UserData.status_invisible:
			gray = true;
			break;
		case UserData.status_offline:
			gray = true;
			break;
		default:
			gray = true;
			break;
		}
		return gray;
	}

	public static String getDefaultStatus(String status) {
		String defaultStatus = UserData.status_offline;
		if (null != status && !(UserData.status_invisible.equals(status))) {
			defaultStatus = status;
		}
		return defaultStatus;
	}

	public static String getDefaultShowName(UserData userData) {

		String text = userData.getRemarkName();
		if (null == text || "".equals(text)) {
			text = userData.getName();
		}
		if (null == text || "".equals(text)) {
			text = userData.getNickname();
		}
		if (null == text || "".equals(text)) {
			text = userData.getAccount();
		}
		if (null == text || "".equals(text)) {
			text = userData.getNumber() + "";
		}
		if (null == text || "".equals(text)) {
			text = "";
		}
		return text;
	}

	public static String getDefaultPersonalShowName(UserData userData) {

		String text = userData.getNickname();
		if (null == text || "".equals(text)) {
			text = userData.getName();
		}
		if (null == text || "".equals(text)) {
			text = userData.getAccount();
		}
		if (null == text || "".equals(text)) {
			text = userData.getNumber() + "";
		}
		if (null == text || "".equals(text)) {
			text = "";
		}
		return text;
	}
}
