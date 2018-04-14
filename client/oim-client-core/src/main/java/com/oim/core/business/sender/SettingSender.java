package com.oim.core.business.sender;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class SettingSender extends BaseSender {

	public SettingSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 获取系统各项设置
	 * @author: XiaHui
	 * @createDate: 2017年6月7日 下午1:24:47
	 * @update: XiaHui
	 * @updateDate: 2017年6月7日 下午1:24:47
	 */
	public void getSetting() {
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.001");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
