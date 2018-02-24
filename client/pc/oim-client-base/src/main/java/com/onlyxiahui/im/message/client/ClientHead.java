package com.onlyxiahui.im.message.client;

import com.onlyxiahui.im.message.Head;

public class ClientHead extends Head {

	private String clientVersion;
	private String clientType;
	
	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
}
