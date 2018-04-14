/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oim.core.net.connect.codec;

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

public class DataCodecFactory implements ProtocolCodecFactory {

	private DataCodecEncoder encoder;
	private DataCodecDecoder decoder;

	public DataCodecFactory() {
		this(Charset.forName("UTF-8"));
	}

	public DataCodecFactory(Charset charset) {
		encoder = new DataCodecEncoder(charset);
		decoder = new DataCodecDecoder(charset);
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
