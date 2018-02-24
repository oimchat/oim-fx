package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.common.dao.DemoDAO;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.Head;

/**
 * 
 * @author XiaHui
 *
 */
@Component
@ActionMapping(value = "1.00")
public class DemoAction {
	@Resource
	DemoDAO demoDAO;
	
	@MethodMapping(value = "1.1.0001")
	public void demo(SocketSession socketSession, Head head) {
		demoDAO.demo();
	}
}
