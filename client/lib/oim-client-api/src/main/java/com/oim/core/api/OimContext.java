package com.oim.core.api;

import com.oim.core.api.module.ActionModule;
import com.oim.core.api.module.NetworkModule;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2017-11-20 21:47:20
 */
public class OimContext extends AppContext {

	public OimContext() {
		initContext();
	}

	private void initContext() {
		NetworkModule networkModule = this.getModule(NetworkModule.class);
		networkModule.start();
		initAction();
	}

	public ActionModule getActionModule() {
		ActionModule actionModule = this.getModule(ActionModule.class);
		return actionModule;
	}

	public NetworkModule getNetworkModule() {
		NetworkModule networkModule = this.getModule(NetworkModule.class);
		return networkModule;
	}
	
	private void initAction(){
		
	}
}
