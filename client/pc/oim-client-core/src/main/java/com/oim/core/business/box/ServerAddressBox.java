package com.oim.core.business.box;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.app.base.component.AbstractBox;
import com.onlyxiahui.im.message.data.address.ServerAddressConfig;

/**
 * @author XiaHui
 * @date 2017-11-26 09:57:45
 */
public class ServerAddressBox extends AbstractBox {

	public ServerAddressBox(AppContext appContext) {
		super(appContext);
	}

	private Map<String, ServerAddressConfig> map = new ConcurrentHashMap<String, ServerAddressConfig>();

	public ServerAddressConfig putAddress(String key, ServerAddressConfig serverAddressConfig) {
		return map.put(key, serverAddressConfig);
	}

	public ServerAddressConfig getAddress(String key) {
		return map.get(key);
	}
}
