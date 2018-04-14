package com.onlyxiahui.im.message.server;

import java.util.HashMap;
import java.util.Map;

import com.only.common.result.Info;
import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Head;

/**
 * @author Only
 * @date 2016-05-19 04:12:15
 */
public class ResultMessage extends AbstractMessage {

	private Head head;
	private Info info = new Info();
	private Map<String, Object> body = new HashMap<String, Object>();

	public static final String code_fail = "0";
	public static final String code_success = "1";

	public ResultMessage() {
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

	public void setInfo(Info info) {
		this.info = info;
	}

	public Info getInfo() {
		return info;
	}

	public void put(String key, Object value) {
		body.put(key, value);
	}

	public Object get(String key) {
		return body.get(key);
	}

	public void setSuccess(boolean success) {
		info.setSuccess(success);
	}
	
	public void addError(String code, String text) {
		info.addError(code, text);
	}

	public void addWarning(String code, String text) {
		info.addWarning(code, text);
	}
}
