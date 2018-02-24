package com.im.server.general.remote.business.server.handler;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.im.server.general.remote.business.server.mq.RemoteMessageQueueWriteHandler;
import com.im.server.general.remote.socket.netty.coder.data.BytesData;
import com.only.net.session.SocketSession;

import net.sf.json.util.JSONUtils;

@Component
public class RemoteServerHandler {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	private ApplicationContext applicationContext = null;
	@Resource
	private RemoteSessionHandler remoteSessionHandler;
	@Resource
	private RemoteMessageQueueWriteHandler remoteMessageQueueWriteHandler;
	JsonParser jp = new JsonParser();

	public RemoteServerHandler() {
		init();
	}

	private void init() {
	}

	public void onMessage(BytesData bytesData, SocketSession socketSession) {
		String message = bytesData.getMessage();
		if (JSONUtils.mayBeJSON(message)) {
			JsonObject jo = jp.parse(message).getAsJsonObject();
			boolean hasAction = jo.has("action");
			if (hasAction) {
				String action = jo.get("action").getAsString();
				if ("0001".equals(action)) {
					String userId = jo.get("userId").getAsString();
					socketSession.setKey(userId);
					remoteSessionHandler.put(socketSession);
					write(socketSession, null);
				}
				if ("0002".equals(action)) {
					if (jo.has("receiveId")) {
						String receiveId = jo.get("receiveId").getAsString();
						remoteMessageQueueWriteHandler.push(receiveId, bytesData);
					}
				}
				if ("0003".equals(action)) {
					if (jo.has("receiveId")) {
						String receiveId = jo.get("receiveId").getAsString();
						remoteMessageQueueWriteHandler.push(receiveId, bytesData);
					}
				}
			}
		}
	}

	private void write(SocketSession socketSession, Object object) {
		if (null != object) {
			// BytesData bd = new BytesData();
			// socketSession.write(bd);
		}
	}

	public void onClose(SocketSession socketSession) {
		remoteSessionHandler.removeSession(socketSession);
	}
}
