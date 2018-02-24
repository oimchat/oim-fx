/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.server;

import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oim.core.common.util.HereStringUtil;
import com.oim.core.net.connect.MessageHandler;
import com.onlyxiahui.im.message.Data;
import com.onlyxiahui.im.message.Head;

/**
 * date 2012-9-14 17:26:16
 * 
 * @author XiaHui
 */
public class Handler extends IoHandlerAdapter {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	Set<MessageHandler> messageHandlerSet = new HashSet<MessageHandler>();

	public void addMessageHandler(MessageHandler messageHandler) {
		messageHandlerSet.add(messageHandler);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("客户端与服务端连接打开.....");
		// System.out.println("sessionOpened:"+session.getLocalAddress());
	}

	@Override
	public void sessionClosed(IoSession session) {
		session = null;
		logger.debug("客户端与服务端断开连接.....");
	}

	@Override
	public void messageSent(IoSession session, Object object) throws Exception {
		logger.debug("客户端已经向服务器发送了消息.....");
		logger.debug(object + "");
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
		Object o = session.getAttribute(Data.class);
		if (o instanceof Data) {
			Data data = (Data) o;
			Head head = data.getHead();
			if (null != head && null != head.getKey()) {
				addExceptionData(head.getKey());
			}
		}

		if (null != throwable) {
			String ioException = HereStringUtil.exceptionToString(throwable);
			String exception1 = "org.apache.mina.transport.socket.nio.NioProcessor.read";
			String exception2 = "org.apache.mina.core.polling.AbstractPollingIoProcessor.read";
			String exception3 = "org.apache.mina.core.polling.AbstractPollingIoProcessor.process";
			if (-1 == ioException.indexOf(exception1) && -1 == ioException.indexOf(exception2) && -1 == ioException.indexOf(exception3)) {
				logger.error("客户端发送信息异常", throwable);
			} else {
				String message = "服务器断开！！！";
				logger.error(message);
			}
		} else {
			logger.error("客户端发送信息异常", throwable);
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("客户端与服务端创建连接.....");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("", status);
	}

	// 客户端接收到的消息为：
	@Override
	public void messageReceived(IoSession session, Object data) throws Exception {
		try {
			logger.debug(data + "");
			back(data);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	void back(Object data) {
		for (MessageHandler messageHandler : messageHandlerSet) {
			messageHandler.setBackTime(System.currentTimeMillis());
			messageHandler.back(data);
		}
	}

	void addExceptionData(String key) {
		for (MessageHandler messageHandler : messageHandlerSet) {
			messageHandler.addExceptionData(key);
		}
	}
}
