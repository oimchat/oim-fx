package com.oim.core.business.action;

import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@ActionMapping(value = "1.000")
public class SystemAction extends AbstractAction {

	public SystemAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.1.0001")
	public void beat() {
		
	}
}
