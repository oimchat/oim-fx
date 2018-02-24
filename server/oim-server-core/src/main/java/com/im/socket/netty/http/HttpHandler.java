
package com.im.socket.netty.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.http.MethodNotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

/**
 * Outputs index page content.
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	// private static final AsciiString CONTENT_TYPE = new
	// AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	// private static final AsciiString CONNECTION = new
	// AsciiString("Connection");
	// private static final AsciiString KEEP_ALIVE = new
	// AsciiString("keep-alive");
	DispatcherServlet dispatcherServlet;

	private final ServletContext servletContext;

	HttpHandler(DispatcherServlet dispatcherServlet) {
		this.dispatcherServlet = dispatcherServlet;
		this.servletContext = dispatcherServlet.getServletConfig().getServletContext();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object o) {
		if (o instanceof DefaultFullHttpRequest) {
		}
		if (o instanceof FullHttpRequest) {

			FullHttpRequest request = (FullHttpRequest) o;

			if (!request.decoderResult().isSuccess()) {
				sendError(ctx, HttpResponseStatus.BAD_REQUEST);
				return;
			}
			if (HttpUtil.is100ContinueExpected(request)) {
				ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
			}

			MockHttpServletRequest servletRequest = createServletRequest(ctx, request);
			MockHttpServletResponse servletResponse = new MockHttpServletResponse();

			try {
				this.dispatcherServlet.service(servletRequest, servletResponse);
			} catch (ServletException ex) {
				logger.error("", ex);
			} catch (IOException ex) {
				logger.error("", ex);
			}
			// String characterEncoding =servletResponse.getCharacterEncoding();
			HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
			ByteBuf contentByte = Unpooled.wrappedBuffer("".getBytes());
			if (null == servletResponse.getContentAsByteArray() || servletResponse.getContentAsByteArray().length == 0) {
				contentByte = Unpooled.wrappedBuffer(Integer.toString(status.code()).getBytes());
			} else {
				contentByte = Unpooled.wrappedBuffer(servletResponse.getContentAsByteArray());
			}
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, contentByte);

			for (String name : servletResponse.getHeaderNames()) {
				for (Object value : servletResponse.getHeaderValues(name)) {
					response.headers().add(name, value);
				}
			}
			response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
			ChannelFuture future = ctx.write(response);
			future.addListener(ChannelFutureListener.CLOSE);

			// boolean keepAlive = HttpUtil.isKeepAlive(request);
			// response.headers().set(CONTENT_TYPE, "text/plain");
			// response.headers().setInt(CONTENT_LENGTH,
			// response.content().readableBytes());

			// ChannelFuture future = ctx.write(response);
			// future.addListener(ChannelFutureListener.CLOSE);
			// if (!keepAlive || response.status().code() != 200) {
			// ChannelFuture future = ctx.write(response);
			// future.addListener(ChannelFutureListener.CLOSE);
			// } else {
			// response.headers().set(CONNECTION, KEEP_ALIVE);
			// ctx.write(response);
			// }
			// Write the initial line and the header.
			// ctx.write(response);
			// InputStream contentStream = new
			// ByteArrayInputStream(servletResponse.getContentAsByteArray());
			// ChannelFuture writeFuture = ctx.write(new
			// ChunkedStream(contentStream)); // Write the content.
			// writeFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private MockHttpServletRequest createServletRequest(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
		Channel channel = ctx.channel();
		SocketAddress socketAddress = channel.localAddress();
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(httpRequest.uri()).build();

		MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
		servletRequest.setRequestURI(uriComponents.getPath());
		servletRequest.setPathInfo(uriComponents.getPath());
		servletRequest.setMethod(httpRequest.method().name());

		if (uriComponents.getScheme() != null) {
			servletRequest.setScheme(uriComponents.getScheme());
		}
		if (uriComponents.getHost() != null) {
			servletRequest.setServerName(uriComponents.getHost());
		}
		if (uriComponents.getPort() != -1) {
			servletRequest.setServerPort(uriComponents.getPort());
		} else {
			if (socketAddress instanceof InetSocketAddress) {
				InetSocketAddress isa = (InetSocketAddress) socketAddress;
				servletRequest.setServerPort(isa.getPort());
				servletRequest.setLocalPort(isa.getPort());
			}
		}

		for (String name : httpRequest.headers().names()) {
			for (String value : httpRequest.headers().getAll(name)) {
				servletRequest.addHeader(name, value);
			}
		}
		ByteBuf content = httpRequest.content();
		if (content.hasArray()) {
			byte[] array = content.array();
			servletRequest.setContent(array);
		}

		try {
			if (uriComponents.getQuery() != null) {
				String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
				servletRequest.setQueryString(query);
			}

			for (Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
				for (String value : entry.getValue()) {
					servletRequest.addParameter(UriUtils.decode(entry.getKey(), "UTF-8"), UriUtils.decode(value, "UTF-8"));
				}
			}
			HttpMethod method = httpRequest.method();

			if (HttpMethod.POST == method) {// 是POST请求

				HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(httpRequest);
				decoder.offer(httpRequest);
				List<InterfaceHttpData> list = decoder.getBodyHttpDatas();

				for (InterfaceHttpData httpData : list) {
					Attribute data = (Attribute) httpData;
					servletRequest.addParameter(data.getName(), data.getValue());
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}

		return servletRequest;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		
	}

	public void sendRedirect(ChannelHandlerContext ctx, String newUri) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaderNames.LOCATION, newUri);
		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer("Failure: " + status + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	class RequestParser {
		private FullHttpRequest request;

		/**
		 * 构造一个解析器
		 * 
		 * @param req
		 */
		public RequestParser(FullHttpRequest request) {
			this.request = request;
		}

		/**
		 * 解析请求参数
		 * 
		 * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
		 *
		 * @throws BaseCheckedException
		 * @throws IOException
		 * @throws MethodNotSupportedException
		 */
		public Map<String, String> parse() throws IOException, MethodNotSupportedException {
			HttpMethod method = request.method();

			Map<String, String> parmMap = new HashMap<>();

			if (HttpMethod.GET == method) { // 是GET请求
				QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
				decoder.parameters().entrySet().forEach(entry -> { // entry.getValue()是一个List, 只取第一个元素
					parmMap.put(entry.getKey(), entry.getValue().get(0));
				});
			} else if (HttpMethod.POST == method) { // 是POST请求
				HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
				decoder.offer(request);
				List<InterfaceHttpData> parmList = decoder.getBodyHttpDatas();
				for (InterfaceHttpData parm : parmList) {
					Attribute data = (Attribute) parm;
					parmMap.put(data.getName(), data.getValue());
				}
			} else {// 不支持其它方法
				throw new MethodNotSupportedException(""); // 这是个自定义的异常, 可删掉这一行
			}
			return parmMap;
		}
	}
}
