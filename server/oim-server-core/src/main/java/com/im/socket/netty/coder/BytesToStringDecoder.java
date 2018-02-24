package com.im.socket.netty.coder;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 说明：解码器
 */
public class BytesToStringDecoder extends ByteToMessageDecoder {

	private final Charset charset;

	private int HEAD_LENGTH = 4;

	public BytesToStringDecoder() {
		this(Charset.forName("UTF-8"));
	}

	public BytesToStringDecoder(Charset charset) {
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
		int size = in.readInt(); // 读取传送过来的消息的长度。ByteBuf
										// 的readInt()方法会让他的readIndex增加4
		if (size < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
			ctx.close();
		}

		if (in.readableBytes() < size) { // 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
			in.resetReaderIndex();
			return;
		}
		
		byte[] bytes = new byte[size]; // 嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
		in.readBytes(bytes); //
		String text = new String(bytes, charset); // 将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择
		out.add(text);
	}
}
