package com.im.server.general.common.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.server.general.business.thread.SessionServerHandler;

/**
 * @author Only
 * @date 2016年5月20日 上午11:45:04
 */
@Service
public class InfoService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	SessionServerHandler dataHandler;
}
