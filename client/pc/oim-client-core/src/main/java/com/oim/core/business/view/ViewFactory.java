package com.oim.core.business.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oim.core.common.app.AbstractFactory;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.view.AbstractView;
import com.onlyxiahui.app.base.view.View;

/**
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午2:24:31
 */
public class ViewFactory extends AbstractFactory {

	private Map<Class<?>, Class<?>> map = new ConcurrentHashMap<Class<?>, Class<?>>();
	private Map<Class<?>, Object> objectMap = new ConcurrentHashMap<Class<?>, Object>();

	public ViewFactory(AppContext appContext) {
		super(appContext);
	}

	public void register(Class<? extends View> classType, Class<? extends AbstractView> view) {
		map.put(classType, view);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSingleView(Class<? extends View> classType) {
		Object o = null;
		Class<?> view = map.get(classType);
		if (null != view) {
			o = objectMap.get(view);
			if (null == o) {
				o = super.getObject(view);
				objectMap.put(view, o);
			}
		}
		return (T) o;
	}

	@SuppressWarnings("unchecked")
	public <T> T getNewView(Class<? extends View> classType) {
		Object o = null;
		Class<?> view = map.get(classType);
		if (null != view) {
			o = super.getObject(view);
		}
		return (T) o;
	}
}
