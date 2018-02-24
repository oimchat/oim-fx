/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.oim.core.net.connect.codec.DataCodecFactory;
import com.only.common.lib.util.OnlyJsonUtil;
import com.only.net.connect.ConnectData;
import com.only.net.connect.Connector;
import com.onlyxiahui.im.message.Data;

/**
 * date 2012-9-14 17:40:54
 * 
 * @author XiaHui
 */
public class SocketConnector implements Connector {

	private IoSession session;
	private IoConnector ioConnector;
	private IoHandlerAdapter handler;

	public SocketConnector(IoHandlerAdapter handler) {
		this.handler = handler;
		initConnector();
	}

	private void initConnector() {
		ioConnector = new NioSocketConnector();
		ioConnector.getFilterChain().addLast("mis", new ProtocolCodecFilter(new DataCodecFactory()));// 添加过滤器
		ioConnector.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		ioConnector.setHandler(handler);// 添加业务逻辑处理类
	}

	@Override
	public boolean connect(ConnectData connectData) {
		boolean mark = true;
		try {
			ConnectFuture connect = ioConnector.connect(new InetSocketAddress(connectData.getAddress(), connectData.getPort()));// 创建连接
			connect.awaitUninterruptibly(connectData.getTimeOut());// 30000//
			session = connect.getSession();// 获取session
			mark = null != session;
		} catch (Exception e) {
			mark = false;
		}
		return mark;
	}

	public IoSession getSession() {
		return session;
	}

	@Override
	public boolean isConnected() {
		return (null != session && session.isConnected() && !session.isClosing());
	}

	@Override
	public void closeConnect() {
		if (null != session) {
			session.closeNow();
			session = null;
		}
	}

	@Override
	public boolean write(Object o) {
		boolean mark = isConnected();
		if (mark) {
			if (o instanceof String) {
				session.write(o);
				session.setAttribute(String.class, o);
			} else {
				String json = OnlyJsonUtil.objectToJson(o);
				session.write(json);
				session.setAttribute(Data.class, o);
			}
		}
		return mark;
	}
}
