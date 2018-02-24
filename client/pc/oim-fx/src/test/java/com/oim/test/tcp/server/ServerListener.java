package com.oim.test.tcp.server;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ServerListener implements IoServiceListener {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void serviceActivated(IoService service) throws Exception {
		logger.debug("service Ativated.............");
	}

	@Override
	public void serviceDeactivated(IoService service) throws Exception {
		logger.debug("service deactivated.............");
	}

	@Override
	public void serviceIdle(IoService service, IdleStatus idle) throws Exception {
		logger.debug("session idle............." + idle.toString());
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("session created.............");
	}

	@Override
	public void sessionDestroyed(IoSession session) throws Exception {
		logger.debug("session destroyed.............");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
