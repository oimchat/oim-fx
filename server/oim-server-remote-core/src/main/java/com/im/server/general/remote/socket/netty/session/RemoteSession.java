package com.im.server.general.remote.socket.netty.session;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.only.net.session.AbstractSession;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class RemoteSession<E> extends AbstractSession {

	ChannelHandlerContext context;

	public RemoteSession(ChannelHandlerContext context) {
		this.context = context;
	}

	@Override
	public void close() {
		context.close();
	}

	@Override
	public String getRemoteAddress() {
		Channel channel = context.channel();
		String address = "";
		SocketAddress socketAddress = channel.remoteAddress();
		if (socketAddress instanceof InetSocketAddress) {
			InetSocketAddress sa = (InetSocketAddress) socketAddress;
			address = sa.getHostString();
		} else {
			String temp = socketAddress.toString();
			if (temp != null) {
				String[] array = temp.replace("/", "").split(":");
				address = array[0];
			}
		}
		return address;
	}



	@Override
	public void write(Object object) {
		if (null != object && null != context) {
			Channel channel = context.channel();
			channel.writeAndFlush(object);
		}
	}
}
