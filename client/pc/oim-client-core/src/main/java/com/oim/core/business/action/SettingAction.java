package com.oim.core.business.action;

import com.oim.core.business.manager.SettingManager;
import com.only.common.result.Info;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractAction;
import com.onlyxiahui.im.message.data.setting.ChatSetting;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@ActionMapping(value = "1.001")
public class SettingAction extends AbstractAction {

	public SettingAction(AppContext appContext) {
		super(appContext);
	}

	@MethodMapping(value = "1.1.0001")
	public void settingBack(Info info, 
			@Define("chatSetting") ChatSetting chatSetting) {
		if(null!=chatSetting){
			SettingManager sm = appContext.getManager(SettingManager.class);
			sm.setChatSetting(chatSetting);
		}
	}
}
