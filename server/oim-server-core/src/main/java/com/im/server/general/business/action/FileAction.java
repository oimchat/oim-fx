package com.im.server.general.business.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.im.base.common.box.ConfigBox;
import com.im.server.general.business.push.FilePush;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.AddressData;
import com.onlyxiahui.im.message.data.FileData;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 
 * @author XiaHui
 *
 */
@Component
@ActionMapping(value = "1.505")
public class FileAction {

	@Resource
	FilePush filePush;

	/**
	 * 服务地址
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 上午9:16:47
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 上午9:16:47
	 */
	@MethodMapping(value = "1.1.0001")
	public ResultMessage getFileServer() {
		String category = "server.path.file";
		String key = "file.server.http.url";
		String value = ConfigBox.get(category, key);

		ResultMessage message = new ResultMessage();
		message.put("fileServerUrl", value);
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
	 * 获取对方支持接受文件方式信息
	 * 
	 * @author: XiaHui
	 * @createDate: 2017年4月14日 上午9:17:15
	 * @update: XiaHui
	 * @updateDate: 2017年4月14日 上午9:17:15
	 */
	@MethodMapping(value = "1.1.0004")
	public void getOppositeFileAcceptInfo(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		filePush.pushGetOppositeFileAcceptInfo(head.getKey(), receiveUserId, sendUserId);
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
	public void returnFileAcceptInfo(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("acceptTypeList") List<String> acceptTypeList) {
		filePush.pushReturnFileAcceptInfo(head.getKey(), sendUserId, receiveUserId, acceptTypeList);
	}

	@MethodMapping(value = "1.1.0006")
	public void sendOfflineFile(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("fileData") FileData fileData) {
		filePush.pushSendOfflineFile(head.getKey(), receiveUserId, sendUserId, fileData);
	}

	@MethodMapping(value = "1.1.0007")
	public void requestOnline(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId,
			@Define("actionType") String actionType,
			@Define("code") String code,
			@Define("message") String message) {
		filePush.pushResponse(head.getKey(), sendUserId, receiveUserId, actionType, code, message);
	}

	@MethodMapping(value = "1.1.0008")
	public void response(
			Head head,
			@Define("receiveUserId") String receiveUserId,
			@Define("sendUserId") String sendUserId) {
		filePush.pushCancel(head.getKey(), receiveUserId, sendUserId);
	}
}
