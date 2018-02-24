package com.im.server.general.business.push;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.im.server.general.business.mq.MessageQueueWriteHandler;

/**
 * @author: XiaHui
 * @date: 2016年8月26日 上午9:21:41
 */
@Service
public class GroupCategoryPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;
}
