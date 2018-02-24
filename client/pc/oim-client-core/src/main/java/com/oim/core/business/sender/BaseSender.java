package com.oim.core.business.sender;

import com.oim.core.business.module.NetModule;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractSender;
import com.onlyxiahui.im.message.Data;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.ClientHead;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2017年9月21日 下午2:25:20
 */
public class BaseSender extends AbstractSender {

	public BaseSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 发送信息
	 * 
	 * @param data
	 *            :信息
	 */
	public void write(Data data) {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.write(data);
	}

	/**
	 * 发送信息
	 * 
	 * @param data
	 *            :信息
	 * @param dataBackAction
	 *            :回调Action
	 */
	public void write(Data data, DataBackAction dataBackAction) {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.write(data, dataBackAction);
	}

	public void write(Head head, DataBackAction dataBackAction) {
		Data data = new Message();
		data.setHead(head);
		NetModule nm = appContext.getModule(NetModule.class);
		nm.write(data, dataBackAction);
	}
	
	public Head getHead() {
        ClientHead head = new ClientHead();
        head.setClientType("1");
        head.setClientVersion("0.1");
        return head;
    }
}
