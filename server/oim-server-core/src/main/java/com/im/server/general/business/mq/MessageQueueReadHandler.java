package com.im.server.general.business.mq;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.business.thread.SessionServerHandler;
import com.only.net.server.DataQueueItem;

@Component
public class MessageQueueReadHandler {
	
	@Resource
	SessionServerHandler sessionServerHandler;

	public void push(DataQueueItem dataQueueItem) {
		sessionServerHandler.push(dataQueueItem);
	}

	public void push(Object data) {
		sessionServerHandler.push(data);
	}

	public void push(String key, Object data) {
		sessionServerHandler.push(key, data);
	}

	public void push(List<String> keyList, Object data) {
		sessionServerHandler.push(keyList, data);
	}

	public void pushWithout(String key, Object data) {
		sessionServerHandler.pushWithout(key, data);
	}

	public void pushWithout(List<String> keyList, Object data) {
		sessionServerHandler.pushWithout(keyList, data);
	}
}
