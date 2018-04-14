/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.remote.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午10:03:05
 * @version 0.0.1
 */
public class BytesDataDecoder extends CumulativeProtocolDecoder {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Charset charset = Charset.forName("UTF-8");// 字符编码类型

	public BytesDataDecoder() {
	}

	public BytesDataDecoder(Charset charset) {
		if (null != charset) {
			this.charset = charset;
		}
	}

	// server
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		try {
			if (in.prefixedDataAvailable(4, Integer.MAX_VALUE)) {
				in.mark();// 标记当前位置，以便reset

				int textSize = in.getInt(); // 读取传送过来的消息的长度。ByteBuf
				int bytesSize = in.getInt();
				int totalSize = (textSize + bytesSize);
				if ((totalSize) > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
					in.reset();
					return false;// 接收新数据，以拼凑成完整数据
				}

				BytesData bd = new BytesData();

				byte[] textBytes = new byte[textSize]; // 嗯，这时候，我们读到的长度，满足我们的要求了，把传送过来的数据，取出来吧~~
				in.get(textBytes); //

				byte[] bytes = new byte[bytesSize];
				in.get(bytes); //

				String text = new String(textBytes, charset); // 将byte数据转化为我们需要的对象。伪代码，用什么序列化，自行选择

				bd.setMessage(text);
				bd.setBytes(bytes);
				out.write(bd);
				return true;// 接收新数据，以拼凑成完整数据
			}
		} catch (Exception e) {
			in.sweep();
			logger.error("", e);
		}
		return false;
	}

}
