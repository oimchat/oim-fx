package com.onlyxiahui.im.message.data.address;

import java.util.Date;

/**
 * @author XiaHui
 * @date 2017-11-26 07:39:29
 */
public class ServerAddressConfig {

	private String key;// 服务器的key
	private String type;// 地址类型:1:http 2:tcp 3:udp 4:websocket
	private String address;
	private int port;
	private String remark = "";
	private Date createTime;// 建立时间

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
