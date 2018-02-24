package com.onlyxiahui.im.message.data;

/**
 * @author: XiaHui
 * @date: 2017年6月5日 下午2:43:43
 */
public class ServerAddress {

	private AddressData mainServerAddressData;
	private AddressData remoteServerAddressData;
	private String fileOfflineServerURL;
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

	public String getFileOfflineServerURL() {
		return fileOfflineServerURL;
	}

	public void setFileOfflineServerURL(String fileOfflineServerURL) {
		this.fileOfflineServerURL = fileOfflineServerURL;
	}

	public String getUserServerURL() {
		return userServerURL;
	}

	public void setUserServerURL(String userServerURL) {
		this.userServerURL = userServerURL;
	}

}
