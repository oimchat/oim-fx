package com.im.server.general.remote.socket.netty.coder.data;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 说明：解码器
 */
public class BytesDataDecoder extends ByteToMessageDecoder {

	private final Charset charset;

	private int HEAD_LENGTH = 8;

	public BytesDataDecoder() {
		this(Charset.forName("UTF-8"));
	}

	public BytesDataDecoder(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if (in.readableBytes() < HEAD_LENGTH) { // 这个HEAD_LENGTH是我们用于表示头长度的字节数。
			return;
		}

		in.markReaderIndex(); // 我们标记一下当前的readIndex的位置
		int textSize = in.readInt(); // 读取传送过来的消息的长度。ByteBuf
		int bytesSize = in.readInt();

		int totalSize = (textSize + bytesSize);

		if (totalSize < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
			ctx.close();
		}

		if (in.readableBytes() < totalSize) { // 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
			in.resetReaderIndex();
			return;
		}

		BytesData bd = new BytesData();

		byte[] textBytes = new byte[textSize]; // 嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
		in.readBytes(textBytes); //

		byte[] bytes = new byte[bytesSize];
		in.readBytes(bytes); //

		String text = new String(textBytes, charset); // 将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择

		bd.setMessage(text);
		bd.setBytes(bytes);
		out.add(bd);
	}
}
