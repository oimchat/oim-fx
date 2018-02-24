package com.im.common.initialize;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.im.server.general.business.thread.SocketThread;

/**
 *
 * 容器启动后要初始化执行的操作
 *
 */
@Component
public class AppInitializer implements ApplicationListener<ContextRefreshedEvent> {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	private SocketThread socketThread;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {
		if (e.getApplicationContext().getParent() == null) {
			if (logger.isInfoEnabled()) {
				logger.info(this.getClass() + "：系统初始化。");
			}
			socketThread.start();
		}
	}
}
