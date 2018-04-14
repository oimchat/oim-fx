package com.oim.core.business.sender;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class DemoSender extends BaseSender {

	public DemoSender(AppContext appContext) {
		super(appContext);
	}

	public void demo() {
		Message message = new Message();

		Head head = new Head();
		head.setAction("1.00");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
