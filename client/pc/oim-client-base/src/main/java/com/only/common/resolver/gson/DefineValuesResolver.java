package com.only.common.resolver.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.only.common.resolver.BeanBox;
import com.only.common.util.OnlyClassUtil;
import com.only.common.util.OnlyPropertyUtil;
import com.only.general.annotation.parameter.Define;
import com.only.general.data.Body;

/**
 * 反射调用方法，并传值
 * @author: XiaHui
 * @date: 2016-09-28 10:44:17
 */
public class DefineValuesResolver {

	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public Object[] resolver(JsonObject jsonObject, Method method, BeanBox beanBox) {
		Type[] types = method.getGenericParameterTypes();
		Class<?>[] classArray = method.getParameterTypes();
		Annotation[][] annotations = method.getParameterAnnotations();
		Object[] datas = getParameterValues(jsonObject, types, classArray, annotations, beanBox);
		return datas;
	}

//	public Object[] resolver(JsonObject jsonObject, Parameter[] parameters, BeanBox beanBox) {
//		Object[] data = null;
//		if (null != parameters) {
//			int length = parameters.length;
//			data = new Object[length];
//			for (int i = 0; i < length; i++) {
//				Object value = null;
//				Parameter p = parameters[i];
//				Type type = p.getParameterizedType();
//				Define define = p.getAnnotation(Define.class);
//				if (null != define) {
//					String name = define.value();
//					value = getParameterValue(name, jsonObject, type);
//				} else if (type instanceof ParameterizedType) {
//
//				} else if (type instanceof GenericArrayType) {
//
//				} else if (type instanceof Class) {
//					Class<?> genericClass = (Class<?>) type;
//
//					if (Body.class.isAssignableFrom(genericClass)) {
//						value = gson.fromJson(jsonObject, type);
//					} else if (JsonElement.class.isAssignableFrom(genericClass)) {
//
//					} else if (JsonObject.class.isAssignableFrom(genericClass)) {
//						value = jsonObject;
//					} else if (OnlyPropertyUtil.isString(genericClass)) {
//
//					} else if (OnlyPropertyUtil.isPrimitive(genericClass)) {
//
//					} else {
//						value = beanBox.getBean(genericClass);
//					}
//					if (null == value) {
//						if (OnlyClassUtil.isCanInstance(genericClass)) {
//							try {
//								value = genericClass.newInstance();
//							} catch (InstantiationException e) {
//								e.printStackTrace();
//							} catch (IllegalAccessException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					if (null == value) {
//						value = OnlyPropertyUtil.getDefaultValue(genericClass);
//					}
//				}
//				data[i] = value;
//			}
//		}
//		return data;
//	}

	private Object[] getParameterValues(JsonObject jsonObject, Type[] types, Class<?>[] classArray, Annotation[][] annotations, BeanBox beanBox) {
		Object[] data = null;
		if (null != types) {
			int length = types.length;
			data = new Object[length];
			for (int i = 0; i < length; i++) {
				Object value = null;
				Type type = types[i];
				Annotation[] annotationArray = annotations[i];
				Define define = null;
				if (null != annotationArray && annotationArray.length > 0) {
					for (Annotation a : annotationArray) {
						if (a instanceof Define) {
							define = (Define) a;
							break;
						}
					}
				}

				if (null != define) {
					String name = define.value();
					value = getParameterValue(name, jsonObject, type);
				} else if (type instanceof ParameterizedType) {

				} else if (type instanceof GenericArrayType) {

				} else if (type instanceof Class) {
					Class<?> genericClass = (Class<?>) type;

					if (Body.class.isAssignableFrom(genericClass)) {
						value = gson.fromJson(jsonObject, type);
					} else if (JsonElement.class.isAssignableFrom(genericClass)) {

					} else if (JsonObject.class.isAssignableFrom(genericClass)) {
						value = jsonObject;
					} else if (OnlyPropertyUtil.isString(genericClass)) {

					} else if (OnlyPropertyUtil.isPrimitive(genericClass)) {

					} else {
						value = beanBox.getBean(genericClass);
					}
					if (null == value) {
						if (OnlyClassUtil.isCanInstance(genericClass)) {
							try {
								value = genericClass.newInstance();
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
					if (null == value) {
						value = OnlyPropertyUtil.getDefaultValue(genericClass);
					}
				}
				data[i] = value;
			}
		}
		return data;
	}

	private Object getParameterValue(String name, JsonObject bodyObject, Type type) {
		Object o = null;
		if (null != bodyObject) {
			JsonElement je = bodyObject.get(name);
			if (null != je) {
				o = gson.fromJson(je, type);
			}
		}
		return o;
	}
}
