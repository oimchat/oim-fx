package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.PageData;
import com.onlyxiahui.im.message.data.device.DeviceStatus;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class DeviceSender extends BaseSender {

	public DeviceSender(AppContext appContext) {
		super(appContext);
	}

	public void queryDeviceList(PageData page, DataBackAction dataBackAction) {
		Message message = new Message();

		message.put("page", page == null ? (new PageData()) : page);

		Head head = new Head();
		head.setAction("1.800");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	public void sendDeviceStatus(DeviceStatus deviceStatus) {
		Message message = new Message();

		message.put("deviceStatus", deviceStatus);

		Head head = new Head();
		head.setAction("1.800");
		head.setMethod("1.1.0002");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

}
