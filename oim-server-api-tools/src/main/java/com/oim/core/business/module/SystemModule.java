package com.oim.core.business.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2017年9月20日 下午5:49:06
 */
public class SystemModule extends AbstractComponent {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public SystemModule(AppContext appContext) {
		super(appContext);
	}

	public void exit() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.closeConnect();
		System.exit(0);
	}
}
