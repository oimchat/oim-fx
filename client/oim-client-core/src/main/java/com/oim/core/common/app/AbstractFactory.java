package com.oim.core.common.app;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月6日 下午7:45:12
 * @version 0.0.1
 */
public abstract class AbstractFactory{

	private Map<Class<?>, Object> map = new ConcurrentHashMap<Class<?>, Object>();

	protected AppContext appContext;

	public AbstractFactory(AppContext appContext) {
		this.appContext = appContext;
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<?> classType) {
		Object object = null;

		if (null != classType) {
			object = map.get(classType);
			if (null == object) {
				try {
					Class<?>[] types = { AppContext.class };
					Constructor<?> constructor = classType.getConstructor(types);
					Object[] objects = { appContext };
					object = constructor.newInstance(objects);
					map.put(classType, object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return (T) object;
	}
}
