/**
 *
 */
package com.im.server.general.remote.socket.netty.coder.data;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;

/**
 * 说明：编码器
 */
public class BytesDataEncoder extends MessageToByteEncoder<Object> {

	private final Charset charset;

	public BytesDataEncoder() {
		this(Charset.forName("UTF-8"));
	}

	public BytesDataEncoder(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object object, ByteBuf out) throws Exception {
		if (object instanceof BytesData) {
			BytesData bd = (BytesData) object;

			String text = bd.getMessage();
			if (null != text && !".".equals(text)) {
				byte[] textBytes = text.getBytes(charset);
				int textSize = textBytes.length;
				byte[] bytes = bd.getBytes();

				int size = (null == bytes) ? 0 : bytes.length;
				out.writeInt(textSize);
				out.writeInt(size);
				out.writeBytes(textBytes);
				if ((null != bytes)) {
					out.writeBytes(bytes);
				}
			}
		}
	}
}
