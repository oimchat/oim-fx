package com.oim.core.common.app.action;

import java.lang.reflect.Method;

import com.oim.core.common.app.AbstractFactory;
import com.only.common.action.ActionDispatch;
import com.only.net.action.ActionMap;
import com.onlyxiahui.app.base.AppContext;

/**
 * 类描述：xxx XiaHui 2014年2月16日
 */
public class ActionFactory extends AbstractFactory implements ActionMap{

	ActionDispatch ad = new ActionDispatch("com.oim.core.business.action");

	public ActionFactory(AppContext appContext) {
		super(appContext);
	}

	@Override
	public Object getAction(String path) {
		Object object = null;
		Class<?> classType = ad.getClass(path);
		if (null != classType) {
			object = super.getObject(classType);
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
}
