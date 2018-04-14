package com.oim.core.net.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.only.common.lib.util.HttpClient3Util;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.common.resolver.BeanBox;
import com.only.common.resolver.gson.DefineValuesResolver;
import com.only.common.result.Info;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.im.message.client.Message;

import net.sf.json.util.JSONUtils;

/**
 * @author XiaHui
 * @date 2017-11-26 09:44:37
 */
public class RequestClient {
	
	private Gson gson = new Gson();
	private final DefineValuesResolver pvr = new DefineValuesResolver();

	public void execute(String url, Message data, DataBackAction dataBackAction) {
		try {
			String json = OnlyJsonUtil.objectToJson(data);
			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("data", json);
			String result = HttpClient3Util.post(url.toString(), dataMap);

			if (StringUtils.isNotBlank(result) && null != dataBackAction) {
				if (JSONUtils.mayBeJSON(result)) {
					JsonObject jo = new JsonParser().parse(result).getAsJsonObject();

					Info info;
					if (jo.has("info")) {
						JsonElement infoElement = jo.get("info");
						info = gson.fromJson(infoElement, Info.class);
					} else {
						info = new Info();
						info.addWarning("001", "请求失败！");
					}
					JsonObject bodyObject = null;
					if (jo.has("body")) {
						bodyObject = jo.get("body").getAsJsonObject();
					}

					Method[] methods = dataBackAction.getClass().getMethods();
					if (null != methods && methods.length > 0) {
						for (Method method : methods) {
							Annotation[] as = method.getAnnotations();
							for (Annotation a : as) {
								if (a instanceof Back) {

									try {
										//Parameter[] parameters = method.getParameters();
										BeanBox beanBox = getBeanBox();
										beanBox.register(Info.class, info);
										Object[] dataArray = pvr.resolver(bodyObject, method, beanBox);
										method.setAccessible(true);
										method.invoke(dataBackAction, dataArray);
									} catch (IllegalAccessException e) {
										e.printStackTrace();
										if (null != dataBackAction) {
											dataBackAction.lost();
										}
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
										if (null != dataBackAction) {
											dataBackAction.lost();
										}
									} catch (InvocationTargetException e) {
										e.printStackTrace();
										if (null != dataBackAction) {
											dataBackAction.lost();
										}
									}
									break;
								}
							}
						}
					}
				} else {
					if (null != dataBackAction) {
						dataBackAction.lost();
					}
				}
			} else {
				if (null != dataBackAction) {
					dataBackAction.lost();
				}
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			if (null != dataBackAction) {
				dataBackAction.lost();
			}
		}
	}

	private BeanBox getBeanBox() {
		BeanBox beanBox = new BeanBox() {

			Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

			@Override
			public void register(Class<?> requiredType, Object object) {
				map.put(requiredType, object);
			}

			@SuppressWarnings("unchecked")
			@Override
			public <T> T getBean(Class<T> requiredType) {
				Object value = null;
				Set<Class<?>> keySet = map.keySet();
				for (Class<?> type : keySet) {
					if (requiredType == type || type.isAssignableFrom(requiredType)) {
						value = map.get(type);
						break;
					}
				}
				return (T) value;
			}
		};
		return beanBox;
	}
}
