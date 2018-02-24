package com.im.server.general.remote.business.server.handler;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.im.server.general.remote.business.server.thread.RemoteSessionServerHandler;
import com.only.net.session.SocketSession;

/**
 * @author: XiaHui
 * @date: 2016年8月17日 下午5:47:10
 */
@Component
public class RemoteSessionHandler {
	
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource
	RemoteSessionServerHandler remoteSessionServerHandler;
	
	public void removeSession(SocketSession socketSession){
		remoteSessionServerHandler.remove(socketSession);
	}
	
	public void put(SocketSession socketSession){
		remoteSessionServerHandler.put(socketSession);
	}
}
