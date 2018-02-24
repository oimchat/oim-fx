package com.im.server.general.business.push;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.server.PushMessage;
import com.im.server.general.business.mq.MessageQueueWriteHandler;

@Service
public class VoicePush {

	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;


	public void pushReceivedVoiceAddress(String userId) {
		Head head = new Head();
		head.setAction("1-503");
		head.setMethod("1.2.0005");
		head.setTime(System.currentTimeMillis());
		PushMessage message = new PushMessage();
		message.setHead(head);
		messageQueueWriteHandler.push(userId, message);
	}
}
