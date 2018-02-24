package com.only.common.lib.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @Author: XiaHui
 * @Date: 2015年12月17日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2015年12月17日
 */
public class OnlyJsonUtil {

	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String json, Class<?> classType) {
		T o = null;
		if (null != json && !"".equals(json)) {
			o = (T) gson.fromJson(json, classType);
		}

		return (T) o;
	}

	public static <T> T jsonToObject(String json, Type type) {
		T o = null;
		if (null != json && !"".equals(json)) {
			o = gson.fromJson(json, type);
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(JsonObject jo, Class<?> classType) {
		T o = null;
		if (null != jo) {
			o = (T) gson.fromJson(jo, classType);
		}
		return (T) o;
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
			json = gson.toJson(o);
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
		return gson.toJson(list);
	}
	
	
	public static Object getParameterValue(String name, JsonObject bodyObject, Type type) {
		Object o = null;
		if (null != bodyObject) {
			JsonElement je = bodyObject.get(name);
			if (null != je) {
				o = gson.fromJson(je, type);
			}
		}
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getParameterValue(String name, JsonObject bodyObject, Class<T> type) {
		Object o = null;
		if (null != bodyObject) {
			JsonElement je = bodyObject.get(name);
			if (null != je) {
				o = gson.fromJson(je, type);
			}
		}
		return ((T) o);
	}
	
	
	
	public static Object getValue(JsonObject bodyObject, Type type) {
		Object o = null;
		if (null != bodyObject) {
			o = gson.fromJson(bodyObject, type);
		}
		return o;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(JsonObject bodyObject, Class<T> type) {
		Object o = null;
		if (null != bodyObject) {
				o = gson.fromJson(bodyObject, type);
		}
		return ((T) o);
	}
	
	 public static boolean mayBeJSON( String string ) {
	      return string != null
	            && ("null".equals( string )
	                  || (string.startsWith( "[" ) && string.endsWith( "]" )) || (string.startsWith( "{" ) && string.endsWith( "}" )));
	   }
}
