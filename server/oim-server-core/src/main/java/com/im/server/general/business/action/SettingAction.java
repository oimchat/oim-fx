package com.im.server.general.business.action;

import org.springframework.stereotype.Component;

import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.onlyxiahui.im.message.data.setting.ChatSetting;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:31:55
 * @version 0.0.1
 */
@Component
@ActionMapping(value = "1.001")
public class SettingAction {

	@MethodMapping(value = "1.1.0001")
	public Object getSetting() {
		ResultMessage message = new ResultMessage();
		message.put("chatSetting", new ChatSetting());
		return message;
	}
}
