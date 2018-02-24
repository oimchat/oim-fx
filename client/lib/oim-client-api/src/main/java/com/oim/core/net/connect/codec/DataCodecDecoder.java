/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.codec;

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
public class DataCodecDecoder extends CumulativeProtocolDecoder {

	protected static final Logger logger = LoggerFactory.getLogger(DataCodecDecoder.class);

	private Charset charset = Charset.forName("UTF-8");// 字符编码类型

	public DataCodecDecoder() {
	}

	public DataCodecDecoder(Charset charset) {
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
				int size = in.getInt();
				if ((size) > in.remaining()) {// 如果消息内容不够，则重置，相当于不读取size
					in.reset();
					return false;// 接收新数据，以拼凑成完整数据
				}
				byte[] bodyByte = new byte[size];
				in.get(bodyByte, 0, size);
				String xml = new String(bodyByte, charset);
				out.write(xml);
				return true;// 接收新数据，以拼凑成完整数据
			}
		} catch (Exception e) {
			in.sweep();
			logger.error("", e);
		}
		return false;
	}

	public static int bytesToInt(byte[] bytes) {
		int value = 0;
		for (int i = 0; i < bytes.length; i++) {
			value += (bytes[i] & 0XFF) << (8 * (3 - i));
		}
		return value;
	}

	public static byte[] intToBytes(int value) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; i++) {
			bytes[i] = (byte) (value >> (24 - i * 8));
		}
		return bytes;
	}

	public static void main(String[] s) {
		int i = 290;
		byte[] b = intToBytes(i);
		int j = bytesToInt(b);
		System.out.println(j);
	}
}
