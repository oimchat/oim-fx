package com.im.socket.netty.http;

import org.springframework.web.servlet.DispatcherServlet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * 
 * @author: XiaHui
 *
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

	DispatcherServlet dispatcherServlet;
	boolean isSSL = true;

	HttpServerInitializer(DispatcherServlet dispatcherServlet) {
		this.dispatcherServlet = dispatcherServlet;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());// 解码器,服务器端对request解码
		pipeline.addLast("encoder", new HttpResponseEncoder());// 解码器,服务器端对response编码
		pipeline.addLast("aggregator", new HttpObjectAggregator(1048576));
		pipeline.addLast("deflater", new HttpContentCompressor());// 压缩
		pipeline.addLast("handler", new HttpHandler(dispatcherServlet));
	}
}
