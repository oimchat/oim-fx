/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.remote.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午9:48:29
 * @version 0.0.1
 */

public class BytesDataFactory implements ProtocolCodecFactory {

	private BytesDataEncoder encoder;
	private BytesDataDecoder decoder;

	public BytesDataFactory() {
		this(Charset.forName("UTF-8"));
	}

	public BytesDataFactory(Charset charset) {
		encoder = new BytesDataEncoder(charset);
		decoder = new BytesDataDecoder(charset);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) {
		return decoder;
	}
}
