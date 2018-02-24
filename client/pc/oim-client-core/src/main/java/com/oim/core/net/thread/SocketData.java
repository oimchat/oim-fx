package com.oim.core.net.thread;

import java.net.InetAddress;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月5日 下午11:51:36
 * @version 0.0.1
 */
public class SocketData {

	private InetAddress inetAddress;
	private int port;
	private byte[] bytes;
	private int length;

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
