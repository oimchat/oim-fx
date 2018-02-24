package com.im.server.general.remote.socket.netty.tcp;


import com.im.server.general.remote.business.server.handler.RemoteServerHandler;
import com.im.server.general.remote.socket.netty.coder.data.BytesData;
import com.im.server.general.remote.socket.netty.session.RemoteSession;
import com.only.common.spring.util.SpringUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RemoteSocketHandler extends SimpleChannelInboundHandler<Object> {
	
	RemoteServerHandler remoteServerHandler = SpringUtil.getBean(RemoteServerHandler.class);
	
	RemoteSession<BytesData> remoteSession;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object frame) throws Exception {
    	if (frame instanceof BytesData) {
    		BytesData message = ((BytesData) frame);
			remoteServerHandler.onMessage(message, remoteSession);
		}
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	remoteSession = new RemoteSession<BytesData>(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved");
        remoteServerHandler.onClose(remoteSession);
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
