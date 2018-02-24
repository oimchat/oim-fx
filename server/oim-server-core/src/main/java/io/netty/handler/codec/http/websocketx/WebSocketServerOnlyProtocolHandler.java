package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

/**
 * @description:
 * @author: Only
 * @date: 2016年8月15日 下午5:26:19
 */
public class WebSocketServerOnlyProtocolHandler extends WebSocketServerProtocolHandler {

	private final String websocketPath;
	private final String subprotocols;
	private final boolean allowExtensions;
	private final int maxFramePayloadLength;
	private final boolean allowMaskMismatch;
	
	public WebSocketServerOnlyProtocolHandler(String websocketPath) {
		this(websocketPath, null, false);
	}

	public WebSocketServerOnlyProtocolHandler(String websocketPath, String subprotocols) {
		this(websocketPath, subprotocols, false);
	}

	public WebSocketServerOnlyProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
		this(websocketPath, subprotocols, allowExtensions, 65536);
	}

	public WebSocketServerOnlyProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize) {
		this(websocketPath, subprotocols, allowExtensions, maxFrameSize, false);
	}

	public WebSocketServerOnlyProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize, boolean allowMaskMismatch) {
		super(websocketPath, subprotocols, allowExtensions, maxFrameSize, allowMaskMismatch);
		this.websocketPath = websocketPath;
		this.subprotocols = subprotocols;
		this.allowExtensions = allowExtensions;
		this.maxFramePayloadLength = maxFrameSize;
		this.allowMaskMismatch = allowMaskMismatch;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		ChannelPipeline cp = ctx.pipeline();
		String name=ctx.name();
		if (cp.get(WebSocketServerOnlyProtocolHandshakeHandler.class) == null) {
			// Add the WebSocketHandshakeHandler before this one.
			WebSocketServerOnlyProtocolHandshakeHandler ws;
			ws = new WebSocketServerOnlyProtocolHandshakeHandler(websocketPath, subprotocols, allowExtensions, maxFramePayloadLength, allowMaskMismatch);
			ctx.pipeline().addBefore(name, WebSocketServerOnlyProtocolHandshakeHandler.class.getName(), ws);
		}
		if (cp.get(Utf8FrameValidator.class) == null) {
			// Add the UFT8 checking before this one.
			ctx.pipeline().addBefore(ctx.name(), Utf8FrameValidator.class.getName(), new Utf8FrameValidator());
		}
	}
}
