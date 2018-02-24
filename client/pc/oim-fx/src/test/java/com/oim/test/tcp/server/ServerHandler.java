package com.oim.test.tcp.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends IoHandlerAdapter {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ServerHandler() {
		init();
	}

	private void init() {

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable throwable) throws Exception {
		if (null != throwable) {

		} else {
			logger.error("服务器发送信息异常！！！", throwable);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object object) {
		try {
			System.out.println(object);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 发送消息成功之后，关闭session，即关闭与客户端的连接，短连接方式
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.debug("服务器发送信息成功。");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("服务端与客户端创建连接。");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("服务器进入空闲状态。");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("服务端与客户端连接打开。");
		System.out.println("getRemoteAddress:"+session.getRemoteAddress());
		System.out.println("getLocalAddress:"+session.getLocalAddress());
		System.out.println("getServiceAddress:"+session.getServiceAddress());
	}
}
