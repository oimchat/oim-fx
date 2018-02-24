package com.im.socket.netty.coder;

import io.netty.channel.CombinedChannelDuplexHandler;

public class StringServerCodec extends CombinedChannelDuplexHandler<BytesToStringDecoder, StringToBytesEncoder> {
	public StringServerCodec() {
		super(new BytesToStringDecoder(), new StringToBytesEncoder());
	}
}
