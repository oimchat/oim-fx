package com.oim.core.business.sender;

import com.oim.core.business.module.NetModule;
import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractSender;

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
	public void write(String data) {
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
	public void write(String data, DataBackAction dataBackAction) {
		NetModule nm = appContext.getModule(NetModule.class);
		nm.write(data, dataBackAction);
	}
}
