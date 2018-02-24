package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.server.general.business.push.VideoPush;
import com.im.server.general.business.thread.SocketThread;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.AddressData;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 
 * @author XiaHui
 *
 */
@Component
@ActionMapping(value = "1.502")
public class VideoAction {

	@Resource
	private SocketThread socketThread;
	@Resource
	VideoPush videoPush;

	/**
	 * 获取视频服务地址
	 * 
	 * @return
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage getVideoServerPort() {
		AddressData videoAddress = new AddressData();
		int port = socketThread.getPort();
		videoAddress.setPort(port);
		ResultMessage message = new ResultMessage();
		message.put("videoAddress", videoAddress);
		return message;
	}

	/**
	 * 获取用户的视频地址
	 * 
	 * @param userId
	 * @return
	 */
	@MethodMapping(value = "1.1.0003")
	public ResultMessage getUserVideoAddress(@Define("userId") String userId) {
		AddressData videoAddress = socketThread.getVideoAddress(userId);
		ResultMessage message = new ResultMessage();
		message.put("videoAddress", videoAddress);
		return message;
	}

	/**
	 * 向用户发生视频请求
	 * @param head
	 * @param receiveUserId
	 * @param sendUserId
	 */
	@MethodMapping(value = "1.1.0004")
	public void videoRequest(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		videoPush.pushRequestVideo(head.getKey(), receiveUserId, sendUserId);
	}

	/**
	 * 响应用户的视频请求
	 * @param head
	 * @param receiveUserId
	 * @param sendUserId
	 * @param actionType
	 */
	@MethodMapping(value = "1.1.0005")
	public void videoResponse(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType) {
		videoPush.pushResponseVideo(head.getKey(), sendUserId, receiveUserId, actionType);
	}
}
