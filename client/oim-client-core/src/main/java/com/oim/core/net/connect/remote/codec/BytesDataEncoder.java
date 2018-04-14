/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.remote.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author only
 */
public class BytesDataEncoder implements ProtocolEncoder {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Charset charset = Charset.forName("UTF-8");// 字符编码类型

	public BytesDataEncoder() {
	}

	public BytesDataEncoder(Charset charset) {
		if (null != charset) {
			this.charset = charset;
		}
	}

	@Override
	public void encode(IoSession session, Object object, ProtocolEncoderOutput out) throws Exception {
		try {
			
			if (object instanceof BytesData) {
				BytesData bd = (BytesData) object;

				String text = bd.getMessage();
				if (null != text && !".".equals(text)) {
					byte[] textBytes = text.getBytes(charset);
					int textSize = textBytes.length;
					byte[] bytes = bd.getBytes();

					int size = (null == bytes) ? 0 : bytes.length;
					
					IoBuffer io = IoBuffer.allocate(4+4+textSize+size).setAutoExpand(true);
					
					io.putInt(textSize);
					io.putInt(size);
					io.put(textBytes);
					if ((null != bytes)) {
						io.put(bytes);
					}
					io.flip();
					out.write(io);
				}
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
