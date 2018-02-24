package com.im.server.general.remote.business.server.mq;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.remote.socket.netty.coder.data.BytesData;
import com.only.net.server.DataQueueItem;

@Component
public class RemoteMessageQueueWriteHandler {
	@Resource
	RemoteMessageQueueReadHandler remoteMessageQueueReadHandler;

	public void push(DataQueueItem dataQueueItem) {
		remoteMessageQueueReadHandler.push(dataQueueItem);
	}

	public void push(BytesData data) {
		remoteMessageQueueReadHandler.push(data);
	}

	public void push(String key, BytesData data) {
		remoteMessageQueueReadHandler.push(key, data);
	}

	public void push(List<String> keyList, BytesData data) {
		remoteMessageQueueReadHandler.push(keyList, data);
	}

	public void pushWithout(String key, BytesData data) {
		remoteMessageQueueReadHandler.pushWithout(key, data);
	}

	public void pushWithout(List<String> keyList, BytesData data) {
		remoteMessageQueueReadHandler.pushWithout(keyList, data);
	}
}
