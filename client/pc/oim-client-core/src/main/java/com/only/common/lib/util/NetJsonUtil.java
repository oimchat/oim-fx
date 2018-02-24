package com.only.common.lib.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @Author: XiaHui
 * @Date: 2015年12月17日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月17日
 */
public class NetJsonUtil {

	private static final JsonConfig config = new JsonConfig();

	static {
		JsonValueProcessor jvp = new JsonValueProcessor() {
			String dateTime = "yyyy-MM-dd HH:mm:ss";
			String timestamp = "yyyy-MM-dd HH:mm:ss.SSS";
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateTime, Locale.CHINA);
			SimpleDateFormat timestampFormat = new SimpleDateFormat(timestamp, Locale.CHINA);

			@Override
			public Object processArrayValue(Object paramObject,
					JsonConfig paramJsonConfig) {
				return process(paramObject);
			}

			@Override
			public Object processObjectValue(String paramString, Object paramObject,
					JsonConfig paramJsonConfig) {
				return process(paramObject);
			}

			private Object process(Object value) {
				if (value instanceof Date) {
					return dateTimeFormat.format(value);
				}
				if (value instanceof Timestamp) {
					return timestampFormat.format(value);
				}
				return value == null ? "" : value.toString();
			}
		};
		config.registerJsonValueProcessor(Date.class, jvp);
		config.registerJsonValueProcessor(Timestamp.class, jvp);
	}

	/**
	 * 对象转json
	 * 
	 * @param o
	 * @return
	 */
	public static String objectToJson(Object o) {
		String json = "";
		if (null != o) {
			json = JSONObject.fromObject(o, config).toString();
		}
		return json;
	}

	/**
	 * 集合转json
	 * 
	 * @param list
	 * @return
	 */
	public static String listToJson(List<?> list) {
		if (null == list) {
			return "";
		}
		return JSONArray.fromObject(list, config).toString();
	}

	public static String getParameterValue(JSONObject jo, String name) {
		String value = jo.containsKey(name) ? jo.getString(name) : "";
		return value;
	}
}
