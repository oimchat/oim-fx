package com.oim.core.common.app.dao;

import com.oim.core.common.app.AbstractFactory;
import com.onlyxiahui.app.base.AppContext;

/**
 * 
 * @author XiaHui
 * @date 2015年3月16日 下午2:24:31
 */
public class DAOFactory extends AbstractFactory {

	public DAOFactory(AppContext appContext) {
		super(appContext);
	}

	public <T> T getDAO(Class<? extends DataDAO> classType) {
		return super.getObject(classType);
	}
}
