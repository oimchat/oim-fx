/**
 *
 */
package com.im.socket.netty.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;

/**
 * 说明：编码器
 */
public class StringToBytesEncoder extends MessageToByteEncoder<Object> {

    private final Charset charset;

    public StringToBytesEncoder() {
        this(Charset.forName("UTF-8"));
    }

    public StringToBytesEncoder(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object object, ByteBuf out) throws Exception {
        if (null != object) {
            String text = object.toString();
            byte[] bytes = text.getBytes(charset);
            int size = bytes.length;
            out.writeInt(size);
            out.writeBytes(bytes);
        }
    }
}
