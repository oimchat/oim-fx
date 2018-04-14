package com.oim.core.common.config.data;

import com.oim.core.common.AppConstant;
import com.oim.core.common.config.global.DefaultConfigBox;

/**
 * @author: XiaHui
 * @date: 2017-06-28 01:18:20
 */
public class ServerConfig {

	public static final String path = AppConstant.getUserAppPath() + "Config/ServerConfig.xml";

	private String address = DefaultConfigBox.address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
