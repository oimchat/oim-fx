package com.oim.core.business.manager;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.message.data.setting.ChatSetting;

/**
 * 系统一些设置的管理
 * 
 * @author: XiaHui
 *
 */
public class SettingManager extends AbstractManager {

	ChatSetting chatSetting;

	public SettingManager(AppContext appContext) {
		super(appContext);
		init();
	}
	
	private void init(){
		chatSetting=new ChatSetting();
	}

	public ChatSetting getChatSetting() {
		return chatSetting;
	}

	public void setChatSetting(ChatSetting chatSetting) {
		this.chatSetting = chatSetting;
	}

}
