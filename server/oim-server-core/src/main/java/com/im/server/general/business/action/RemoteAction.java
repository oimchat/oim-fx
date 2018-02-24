package com.im.server.general.business.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.base.common.box.ConfigBox;
import com.im.server.general.business.push.RemotePush;
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
@ActionMapping(value = "1.504")
public class RemoteAction {

	@Resource
	RemotePush remotePush;

	/**
	 * 获取远程协助服务地址
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 上午9:16:47
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 上午9:16:47
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage getRemoteServerPort() {
		String category = "server.path.remote";
		String key = "remote.server.tcp.address";

		String address = ConfigBox.get(category, key);
		key = "remote.server.tcp.port";
		String p = ConfigBox.get(category, key);

		AddressData voiceAddress = new AddressData();
		int port = Integer.parseInt(p);
		voiceAddress.setPort(port);
		voiceAddress.setAddress(address);

		ResultMessage message = new ResultMessage();
		message.put("remoteAddress", voiceAddress);
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public ResultMessage getUserRemoteAddress(@Define("userId") String userId) {
		AddressData voiceAddress = new AddressData();
		ResultMessage message = new ResultMessage();
		message.put("remoteAddress", voiceAddress);
		return message;
	}

	/**
	 * 请求控制对方电脑
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 上午9:17:15
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 上午9:17:15
	 */
	@MethodMapping(value = "1.1.0004")
	public void remoteControlRequest(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		remotePush.pushRequestRemoteControl(head.getKey(), receiveUserId, sendUserId);
	}

	/**
	 * 回应对方请求控制电脑
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 上午9:17:30
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 上午9:17:30
	 */
	@MethodMapping(value = "1.1.0005")
	public void remoteControlResponse(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType,
			@Define("code") String code,
			@Define("message") String message) {
		remotePush.pushResponseRemoteControl(head.getKey(), sendUserId, receiveUserId, actionType, code, message);
	}

	@MethodMapping(value = "1.1.0006")
	public void remoteAssistRequest(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		remotePush.pushRequestRemoteAssist(head.getKey(), receiveUserId, sendUserId);
	}

	@MethodMapping(value = "1.1.0007")
	public void remoteAssistResponse(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType,
			@Define("code") String code,
			@Define("message") String message) {
		remotePush.pushResponseRemoteAssist(head.getKey(), receiveUserId, sendUserId, actionType, code, message);
	}

	@MethodMapping(value = "1.1.0008")
	public void releaseRemoteControl(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		remotePush.pushReleaseRemoteControl(head.getKey(), receiveUserId, sendUserId);
	}
}
