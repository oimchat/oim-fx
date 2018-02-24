package com.im.socket.netty.session;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.only.common.lib.util.OnlyJsonUtil;
import com.only.net.session.AbstractSession;

public class WebSocketSession extends AbstractSession {

	ChannelHandlerContext context;

	public WebSocketSession(ChannelHandlerContext context) {
		this.context = context;
	}

	@Override
	public void write(Object object) {
		if (null != object && null != context) {
			Channel channel = context.channel();
			if (channel.isOpen()) {
				channel.writeAndFlush(new TextWebSocketFrame(OnlyJsonUtil.objectToJson(object)));
			}
		}
	}

	@Override
	public void close() {
		ChannelFuture cf = context.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
		cf.addListener(ChannelFutureListener.CLOSE);
		// context.close();
	}

	@Override
	public String getRemoteAddress() {
		Channel channel = context.channel();
		String address = "";

		AttributeKey<String> remoteAddressKey = AttributeKey.valueOf("remote_address_key");

		Attribute<String> attribute = channel.attr(remoteAddressKey);
		String value = attribute.get();
		if (null != value) {
			address = value;
		} else {
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
		}
		return address;
	}
}
