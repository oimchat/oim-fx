package com.oim.common.chat.util;

import java.util.Date;

import com.only.common.util.OnlyDateUtil;

/**
 * @author: XiaHui
 * @date: 2017-12-28 21:06:25
 */
public class ChatUtil {

	public static String getCountText(boolean red, int count) {
		String countText = "";
		if (count > 99) {
			countText = "99";
		} else if (0 < count && count < 99) {
			countText = count + "";
		} else if (0 >= count) {
			countText = "";
		}

		if (!red) {
			countText = "";
		}
		return countText;
	}

	public static String getTimeText(long timestamp) {
		String time = "";
		long now = System.currentTimeMillis();
		long interval = now - timestamp;

		Date date = new Date(timestamp);
		if (interval > (1000 * 60 * 60 * 24)) {// 一天以上显示年月日
			time = OnlyDateUtil.dateToDate(date);
		} else {
			time = OnlyDateUtil.dateToTime(date);
		}
		return time;
	}
}
