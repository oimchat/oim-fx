package com.oim.core.common.app;

import com.oim.common.util.KeyUtil;
import com.only.fx.common.tools.ComponentSyncBuild;
import com.only.fx.common.tools.SyncBuild;
import com.onlyxiahui.app.base.AbstractComponent;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2018-01-19 08:58:02
 */
public class ViewSyncBuild extends AbstractComponent{

	protected ComponentSyncBuild sb = new ComponentSyncBuild();
	
	public ViewSyncBuild(AppContext appContext) {
		super(appContext);
	}

	public <T> T getComponent(SyncBuild<T> syncBuild) {
		String key=KeyUtil.getKey();
		return sb.getComponent(key, syncBuild);
	} 
}
