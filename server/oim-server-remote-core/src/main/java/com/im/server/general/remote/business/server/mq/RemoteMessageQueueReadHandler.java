package com.im.server.general.remote.business.server.mq;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.remote.business.server.thread.RemoteSessionServerHandler;
import com.im.server.general.remote.socket.netty.coder.data.BytesData;
import com.only.net.server.DataQueueItem;

@Component
public class RemoteMessageQueueReadHandler {
	
	@Resource
	RemoteSessionServerHandler remoteSessionServerHandler;

	public void push(DataQueueItem dataQueueItem) {
		remoteSessionServerHandler.push(dataQueueItem);
	}

	public void push(BytesData data) {
		remoteSessionServerHandler.push(data);
	}

	public void push(String key, BytesData data) {
		remoteSessionServerHandler.push(key, data);
	}

	public void push(List<String> keyList, BytesData data) {
		remoteSessionServerHandler.push(keyList, data);
	}

	public void pushWithout(String key, BytesData data) {
		remoteSessionServerHandler.pushWithout(key, data);
	}

	public void pushWithout(List<String> keyList, BytesData data) {
		remoteSessionServerHandler.pushWithout(keyList, data);
	}
}
