package com.onlyxiahui.im.message.server;

import com.only.common.result.Info;
import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Head;

/**
 * @author Only
 * @date 2016-05-19 04:12:15
 */
public class ResultBodyMessage extends AbstractMessage {

	private Head head;
	private Info info = new Info();
	private Object body;

	@Override
	public Head getHead() {
		return head;
	}

	@Override
	public void setHead(Head head) {
		this.head = head;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
	
	public void setInfo(Info info) {
		this.info = info;
	}

	public Info getInfo() {
		return info;
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
