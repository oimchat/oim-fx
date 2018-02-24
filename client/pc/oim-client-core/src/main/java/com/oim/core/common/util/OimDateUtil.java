package com.oim.core.common.util;

import java.util.Date;

import com.only.common.util.OnlyDateUtil;

/**
 * @author XiaHui
 * @date 2017年6月7日 下午2:00:24
 */
public class OimDateUtil {

	/**
	 * 距今多少年
	 * 
	 * @author XiaHui
	 * @date 2017年6月7日 下午2:03:42
	 * @param date
	 * @return
	 */
	public static int beforePresentYearCount(Date date) {
		Date cd = new Date();
		int days = OnlyDateUtil.getBetweenDays(date, cd);
		int year = (days / 365);
		return year;
	}

	public static void main(String[] arg) {
		Date date = OnlyDateUtil.parse("1989-09-24");
		System.out.println(beforePresentYearCount(date));
	}
}
