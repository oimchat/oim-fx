package io.netty.handler.codec.http.websocketx;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月15日 下午5:57:26
 */
public class WebSocketServerOnlyProtocolHandshakeHandler extends WebSocketServerProtocolHandshakeHandler {
	private final String websocketPath;
	private final String subprotocols;
	private final boolean allowExtensions;
	private final int maxFramePayloadSize;
	private final boolean allowMaskMismatch;

	public WebSocketServerOnlyProtocolHandshakeHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
		super(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch);
		this.websocketPath = websocketPath;
		this.subprotocols = subprotocols;
		this.allowExtensions = allowExtensions;
		this.maxFramePayloadSize = maxFrameSize;
		this.allowMaskMismatch = allowMaskMismatch;
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		FullHttpRequest req = (FullHttpRequest) msg;
		String url = req.uri();

		if (!websocketPath.equals(url)) {
			ctx.fireChannelRead(msg);
			return;
		}

		try {

			if (req.method() != GET) {
				sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
				return;
			}

			String remoteAddress = (String) req.headers().get("X-Forwarded-For");

			AttributeKey<String> remoteAddressKey = AttributeKey.valueOf("remote_address_key");

			Attribute<String> attribute = ctx.channel().attr(remoteAddressKey);
			String value = attribute.get();
			if (null == value) {
				attribute.set(remoteAddress);
			}
			String webSocketLocation = getWebSocketLocation(ctx.pipeline(), req, websocketPath);

			final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(webSocketLocation, subprotocols, allowExtensions, maxFramePayloadSize, allowMaskMismatch);
			final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
			if (handshaker == null) {
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				final ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
				handshakeFuture.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						if (!future.isSuccess()) {
							ctx.fireExceptionCaught(future.cause());
						} else {
							ctx.fireUserEventTriggered(WebSocketServerOnlyProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
						}
					}
				});
				WebSocketServerOnlyProtocolHandler.setHandshaker(ctx.channel(), handshaker);
				ctx.pipeline().replace(this, "WS403Responder", WebSocketServerOnlyProtocolHandler.forbiddenHttpRequestResponder());
			}
		} finally {
			req.release();
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
		String protocol = "ws";
		if (cp.get(SslHandler.class) != null) {// SSL in use so use Secure
												// WebSockets
			protocol = "wss";
		}
		return protocol + "://" + req.headers().get(HttpHeaderNames.HOST) + path;
	}
}
