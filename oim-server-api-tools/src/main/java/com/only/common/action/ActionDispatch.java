package com.only.common.action;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;

/**
 * action分配调度模块
 * 
 * @author XiaHui
 * 
 */

public class ActionDispatch {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, Class<?>> actionMap = new HashMap<String, Class<?>>();// 缓存所有action的简单类名
	private Map<String, Method> methodMap = new HashMap<String, Method>();// 缓存所有的action的方法

	public ActionDispatch() {
	}

	/**
	 * 为了更快获取要执行的action，将要执行的action加入Map进行缓存
	 * 
	 * @param classType
	 * @throws MappingException
	 */
	public void add(Class<?> classType) throws MappingException {
		Annotation[] as = classType.getAnnotations();
		for (Annotation annotation : as) {
			if (annotation instanceof ActionMapping) {
				ActionMapping am = ((ActionMapping) annotation);
				String value = am.value();
				saveMethod(value, classType);
				break;
			}
		}
	}

	/**
	 * 获取要执行的action的方法，并且缓存
	 * 
	 * @param classType
	 * @return
	 * @throws MappingException
	 */
	private void saveMethod(String code, Class<?> classType) throws MappingException {
		Method[] methods = classType.getMethods();
		if (null != methods && methods.length > 0) {
			for (Method method : methods) {
				Annotation[] as = method.getAnnotations();
				for (Annotation a : as) {
					if (a instanceof MethodMapping) {
						String value = ((MethodMapping) a).value();
						String key = code + "/" + value;
						if (methodMap.containsKey(key)) {
							StringBuilder sb = new StringBuilder();
							sb.append(classType.getName());
							sb.append("中存在重复的MethodMapping：");
							sb.append(value);
							throw new MappingException(sb.toString());
						} else {
							methodMap.put(key, method);
							actionMap.put(key, classType);
						}
						break;
					}
				}
			}
		}
	}

	public void cover(Class<?> classType) {
		Annotation[] as = classType.getAnnotations();
		for (Annotation annotation : as) {
			if (annotation instanceof ActionMapping) {
				ActionMapping am = ((ActionMapping) annotation);
				String value = am.value();
				cover(value, classType);
				break;
			}
		}
	}

	public void cover(String code, Class<?> classType) {
		Method[] methods = classType.getMethods();
		if (null != methods && methods.length > 0) {
			for (Method method : methods) {
				Annotation[] as = method.getAnnotations();
				for (Annotation a : as) {
					if (a instanceof MethodMapping) {
						String value = ((MethodMapping) a).value();
						String key = code + "/" + value;
						methodMap.put(key, method);
						actionMap.put(key, classType);
						break;
					}
				}
			}
		}
	}

	/**
	 * 截取不包含包名，和小写首字母的类名
	 * 
	 * @param classType
	 * @return
	 */
	public String getSimpleNameAsProperty(Class<?> classType) {
		String valueName = classType.getSimpleName();
		return valueName = valueName.substring(0, 1).toLowerCase() + valueName.substring(1);
	}

	public Class<?> getClass(String path) {
		return actionMap.get(path);
	}

	public Method getMethod(String path) {
		Method method = methodMap.get(path);
		return method;
	}

	public Method getMethod(String classCode, String methodCode) {
		String path = classCode + "/" + methodCode;
		return getMethod(path);
	}
}
