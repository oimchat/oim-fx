package com.oim.core.net.security;

import org.apache.mina.core.session.IoSession;

/**
 * 描述：
 * 
 * @author 夏辉
 * @date 2014年6月14日 下午10:46:03
 * @version 0.0.1
 */
public interface Filter {
	
	public void messageReceived(IoSession session, Object object);
}
