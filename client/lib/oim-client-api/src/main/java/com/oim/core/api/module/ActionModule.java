package com.oim.core.api.module;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.only.common.action.ActionDispatch;
import com.only.net.action.ActionMap;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;

public class ActionModule extends AbstractComponent implements ActionMap {

	ActionDispatch ad = new ActionDispatch();
	private Map<Class<?>, Object> map = new ConcurrentHashMap<Class<?>, Object>();

	public ActionModule(AppContext appContext) {
		super(appContext);
	}

	@Override
	public Object getAction(String path) {
		Object object = null;
		Class<?> classType = ad.getClass(path);
		if (null != classType) {
			object = getObject(classType);
		}
		return object;
	}

	@Override
	public Method getMethod(String path) {
		Method method = ad.getMethod(path);
		return method;
	}

	public void cover(Class<?> classType) {
		ad.cover(classType);
	}

	public <T extends AbstractAction> T getAction(Class<T> classType) {
		if (!map.containsKey(classType)) {
			ad.cover(classType);
		}
		return getObject(classType);
	}

	@SuppressWarnings("unchecked")
	private <T> T getObject(Class<T> classType) {
		Object object = null;

		if (null != classType) {
			object = map.get(classType);
			if (null == object) {
				try {
					object = appContext.getObject(classType);
					map.put(classType, object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return (T) object;
	}
}
