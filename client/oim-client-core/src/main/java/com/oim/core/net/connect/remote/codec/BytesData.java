package com.oim.core.net.connect.remote.codec;

/**
 * @author: XiaHui
 * @date: 2017年4月13日 下午2:00:19
 */
public class BytesData {

	private String message;
	private byte[] bytes;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
