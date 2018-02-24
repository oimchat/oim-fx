package com.oim.fx.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.business.module.NetModule;
import com.oim.core.business.view.MainView;
import com.oim.core.business.view.impl.MainViewImpl;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author: XiaHui
 * @date: 2017年9月7日 下午8:09:04
 */
public class Startup {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected AppContext appContext = new AppContext();

	public Startup() {
		initBase();
		registerView();
		initPartView();
		showStartView();
		startThread();
		logger.debug("startup");
	}

	protected void initBase() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.start();

	}

	protected void registerView() {
		appContext.register(MainView.class, MainViewImpl.class);
	}

	protected void initPartView() {
	}

	protected void showStartView() {
		MainView mv = appContext.getSingleView(MainView.class);
		mv.setVisible(true);
	}

	protected void startThread() {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.getConnectThread().setAutoConnectCount(10);// 设置自动重试次数
	}

}
