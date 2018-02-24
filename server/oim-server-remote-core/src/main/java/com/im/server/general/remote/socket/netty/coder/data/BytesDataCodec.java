package com.im.server.general.remote.socket.netty.coder.data;

import io.netty.channel.CombinedChannelDuplexHandler;

public class BytesDataCodec extends CombinedChannelDuplexHandler<BytesDataDecoder, BytesDataEncoder> {
	public BytesDataCodec() {
		super(new BytesDataDecoder(), new BytesDataEncoder());
	}
}
