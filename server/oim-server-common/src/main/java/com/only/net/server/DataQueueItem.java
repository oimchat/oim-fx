/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.only.net.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author XiaHui
 */
public class DataQueueItem {

	private Type type;
	private List<String> keyList;
	private Object data;

	public DataQueueItem() {
	}

	public DataQueueItem(Type type, String key, Object data) {
		this.type = type;
		this.keyList = new ArrayList<String>();
		this.data = data;
		keyList.add(key);
	}

	public DataQueueItem(Type type, List<String> keyList, Object data) {
		this.type = type;
		this.keyList = keyList;
		this.data = data;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public enum Type {
		one, list, without, all;
	}
}
