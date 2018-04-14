package com.onlyxiahui.im.bean;

import com.onlyxiahui.im.message.data.AddressData;

/**
 * @author: XiaHui
 * @date: 2017年6月5日 下午2:43:43
 */
public class ServerAddress {

	private AddressData mainServerAddressData;
	private AddressData remoteServerAddressData;
	private String offlineFileServerURL;
	private String userServerURL;

	public AddressData getMainServerAddressData() {
		return mainServerAddressData;
	}

	public void setMainServerAddressData(AddressData mainServerAddressData) {
		this.mainServerAddressData = mainServerAddressData;
	}

	public AddressData getRemoteServerAddressData() {
		return remoteServerAddressData;
	}

	public void setRemoteServerAddressData(AddressData remoteServerAddressData) {
		this.remoteServerAddressData = remoteServerAddressData;
	}

	public String getOfflineFileServerURL() {
		return offlineFileServerURL;
	}

	public void setOfflineFileServerURL(String offlineFileServerURL) {
		this.offlineFileServerURL = offlineFileServerURL;
	}

	public String getUserServerURL() {
		return userServerURL;
	}

	public void setUserServerURL(String userServerURL) {
		this.userServerURL = userServerURL;
	}

}
