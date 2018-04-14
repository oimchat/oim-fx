package com.oim.core.business.manager;

import java.util.List;

import com.oim.core.business.box.ServerAddressBox;
import com.oim.core.common.config.ConfigManage;
import com.oim.core.common.config.data.ServerConfig;
import com.oim.core.net.http.RequestClient;
import com.only.common.result.Info;
import com.only.general.annotation.parameter.Define;
import com.only.net.action.Back;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractManager;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

/**
 * @author XiaHui
 * @date 2017-11-26 07:11:20
 */
public class ServerAddressManager extends AbstractManager {

	RequestClient requestClient = new RequestClient();

	String api = "/api/v1/oim/server/getAddress.do";

	public ServerAddressManager(AppContext appContext) {
		super(appContext);
	}

	public Info loadServerAddress(String account) {

		ServerConfig sc = (ServerConfig) ConfigManage.get(ServerConfig.path, ServerConfig.class);
		String url = sc.getAddress() + api;
		final Info backInfo = new Info();

		DataBackActionAdapter dataBackAction = new DataBackActionAdapter() {

			@Override
			public void lost() {
				backInfo.addWarning("000", "网络异常！");
			}

			@Back
			public void back(Info info, @Define("list") List<ServerAddressConfig> list) {

				if (info.isSuccess()) {
					ServerAddressBox sab = appContext.getBox(ServerAddressBox.class);
					if (null != list) {
						for (ServerAddressConfig config : list) {
							sab.putAddress(config.getKey(), config);
						}
					}
				} else {
					backInfo.setErrors(info.getErrors());
					backInfo.setWarnings(info.getWarnings());
				}
			}
		};
		Message m = new Message();
		m.put("account", account);
		requestClient.execute(url, m, dataBackAction);
		return backInfo;
	}
}
