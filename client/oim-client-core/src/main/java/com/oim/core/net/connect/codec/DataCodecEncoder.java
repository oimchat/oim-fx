/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.codec;

import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 
 * @author only
 */
public class DataCodecEncoder implements ProtocolEncoder {

	protected static final Logger logger = LoggerFactory.getLogger(DataCodecEncoder.class);
	private Charset charset = Charset.forName("UTF-8");// 字符编码类型

	public DataCodecEncoder() {
	}

	public DataCodecEncoder(Charset charset) {
		if (null != charset) {
			this.charset = charset;
		}
	}

	@Override
	public void encode(IoSession session, Object object, ProtocolEncoderOutput out) throws Exception {
		try {
			if (null != object) {
				String xml = object.toString();
				byte[] xmlByte = xml.getBytes(charset);
				IoBuffer io = IoBuffer.allocate(xmlByte.length + 4).setAutoExpand(true);
				io.putInt(xmlByte.length);
				io.put(xmlByte);
				io.flip();
				out.write(io);
			}
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		session = null;
	}
}
