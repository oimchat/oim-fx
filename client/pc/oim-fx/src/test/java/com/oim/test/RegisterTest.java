package com.oim.test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.oim.core.net.http.HttpHandler;
import com.oim.core.net.http.Request;
import com.only.common.util.OnlyDateUtil;
import com.only.common.util.OnlyMD5Util;
import com.only.net.data.action.DataBackActionAdapter;

/**
 * 描述：
 *
 * @author XiaHui
 * @date 2016年1月17日 下午3:08:27
 * @version 0.0.1
 */
public class RegisterTest {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			reg(i);
		}
	}

	private static void reg(int i) {

		int y = new Random().nextInt(40);
		int m = new Random().nextInt(11);
		int d = new Random().nextInt(27);
		y = y + 60;
		m++;
		d++;

		// int ci = new Random().nextInt(11);
		int cb = new Random().nextInt(4);
		// String[] c = (new String[] { "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座",
		// "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" });
		String[] b = (new String[] { "A", "B", "AB", "O", "other" });

		String nickname = "我就是板车" + i + "号";
		String password = "123456";
		String gender = ((i % 2) > 0) ? "1" : "2";
		String birthdate = "19" + OnlyDateUtil.intMonthOrDayToString(y) + "-" + OnlyDateUtil.intMonthOrDayToString(m) + "-" + OnlyDateUtil.intMonthOrDayToString(d);
		String address = "天宫" + i + "号";
		String signature = "板车在，人在！";
		String blood = b[cb];
		// String constellation = c[ci];
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("password", OnlyMD5Util.md5L32(password));
		user.put("nickname", nickname);
		user.put("gender", gender);
		user.put("blood", blood);
		user.put("homeAddress", address);
		user.put("locationAddress", "");
		user.put("mobile", "");
		user.put("email", "");
		user.put("signature", signature);
		user.put("introduce", "");
		try {
			user.put("birthdate", OnlyDateUtil.stringDateToDate(birthdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Request request = new Request();
		request.setController("user");
		request.setMethod("/register.do");
		request.put("userData", user);

		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

		};
		String url = "";
		new HttpHandler().execute(url, request, dataBackAction);

	}
}
