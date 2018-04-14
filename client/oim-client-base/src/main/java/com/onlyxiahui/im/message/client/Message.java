package com.onlyxiahui.im.message.client;

import java.util.HashMap;
import java.util.Map;

import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Head;

/**
 * @author: XiaHui
 * @date: 2016年8月22日 下午5:03:51
 */
public class Message extends AbstractMessage {

	private Head head;
	private Map<String, Object> body = new HashMap<String, Object>();

	public static final String code_fail = "0";
	public static final String code_success = "1";

	public Message() {
	}

	@Override
	public Head getHead() {
		return head;
	}

	@Override
	public void setHead(Head head) {
		this.head = head;
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	public void put(String key, Object value) {
		body.put(key, value);
	}

	public Object get(String key) {
		return body.get(key);
	}
}
