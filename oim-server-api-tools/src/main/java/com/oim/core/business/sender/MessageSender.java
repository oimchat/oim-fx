package com.oim.core.business.sender;

import com.oim.core.business.back.DataBackActionImpl;
import com.onlyxiahui.app.base.AppContext;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class MessageSender extends BaseSender {

	public MessageSender(AppContext appContext) {
		super(appContext);
	}

	public void sendMessage(String message,DataBackActionImpl action){
		this.write(message, action);
	}
}
