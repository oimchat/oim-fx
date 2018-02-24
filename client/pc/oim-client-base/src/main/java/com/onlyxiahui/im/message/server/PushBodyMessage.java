package com.onlyxiahui.im.message.server;

import com.onlyxiahui.im.message.AbstractMessage;
import com.onlyxiahui.im.message.Head;

/**
 * 服务器推送消息，其中body自定义对象
 * @author: XiaHui
 * @date: 2016-08-22 05:03:51
 */
public class PushBodyMessage extends AbstractMessage {

	private Head head;
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
}
