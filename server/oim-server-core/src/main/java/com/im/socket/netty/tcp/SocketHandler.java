package com.im.socket.netty.tcp;

import com.im.server.handler.ServerHandler;
import com.im.socket.netty.session.ChannelSession;
import com.only.common.spring.util.SpringUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SocketHandler extends SimpleChannelInboundHandler<Object> {
	
	ServerHandler serverHandler = SpringUtil.getBean(ServerHandler.class);
	
	ChannelSession channelSession;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object frame) throws Exception {
    	if (frame instanceof String) {
			String message = ((String) frame);
			serverHandler.onMessage(message, channelSession);
		}
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	channelSession = new ChannelSession(ctx);
    	System.out.println(channelSession.getRemoteAddress()+":"+channelSession.getRemotePort());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved");
        serverHandler.onClose(channelSession);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
       // ctx.close();
    }
}
