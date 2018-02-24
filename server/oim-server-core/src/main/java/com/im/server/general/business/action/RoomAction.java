package com.im.server.general.business.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.im.message.Head;

@Component
@ActionMapping(value = "1.300")
public class RoomAction {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	

	/**
	 * 用户加入聊天室
	 * @author: XiaHui
	 * @createDate: 2016年8月26日 上午10:15:17
	 * @update: XiaHui
	 * @updateDate: 2016年8月26日 上午10:15:17
	 */
	@MethodMapping(value = "1.1.0001")
	public void joinRoom(Head head,
			@Define("userId") String userId,
			@Define("roomId") String roomId) {
		roomId = (null == roomId || "".equals(roomId)) ? "default" : roomId;
	}

	/**
	 * 获取聊天室用户列表
	 * @author: XiaHui
	 * @createDate: 2016年8月26日 上午10:15:35
	 * @update: XiaHui
	 * @updateDate: 2016年8月26日 上午10:15:35
	 */
	@MethodMapping(value = "1.1.0002")
	public void getRoomUserList(Head head, @Define("roomId") String roomId) {
	}
}
