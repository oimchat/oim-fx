package com.im.socket.netty.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.im.server.handler.ServerHandler;
import com.im.socket.netty.session.WebSocketSession;
import com.only.common.spring.util.SpringUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	protected final Logger logger = LogManager.getLogger(this.getClass());
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	ServerHandler serverHandler = SpringUtil.getBean(ServerHandler.class);
	WebSocketSession webSocketSession;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		if (frame instanceof TextWebSocketFrame) {
			String message = ((TextWebSocketFrame) frame).text();
			serverHandler.onMessage(message, webSocketSession);
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		webSocketSession=new WebSocketSession(ctx);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		serverHandler.onClose(webSocketSession);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		Head head = new Head();
//		head.setAction("1.000");
//		head.setMethod("1.2.0001");
//		head.setTime(System.currentTimeMillis());
//		ResultMessage message = new ResultMessage();
//		message.setHead(head);
//		if (null != channelSession) {
//			channelSession.write(message);
//		}
		if (logger.isDebugEnabled()) {
			logger.debug((null != webSocketSession) ? webSocketSession.getKey() : "" + "：空闲");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		//ctx.close();
	}
}
