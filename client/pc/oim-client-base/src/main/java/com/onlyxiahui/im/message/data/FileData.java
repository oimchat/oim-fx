package com.onlyxiahui.im.message.data;

/**
 * @author: XiaHui
 * @date: 2017年5月31日 下午5:52:24
 */
public class FileData {
	
	//private String acceptType;// 接受方式：online、offline
	private String id;
	private String name;
	private long size;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
