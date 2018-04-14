package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.FileData;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class FileSender extends BaseSender {

	public FileSender(AppContext appContext) {
		super(appContext);
	}

	public void getFileServer(DataBackAction dataBackAction) {

		Message message = new Message();
		Head head = new Head();
		head.setAction("1.505");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message, dataBackAction);
	}

	public void sendOfflineFile(String receiveUserId, String sendUserId, FileData fileData) {

		Message message = new Message();
		message.put("receiveUserId", receiveUserId);// 接受信息的用户id
		message.put("sendUserId", sendUserId);// 发送人的id
		message.put("fileData", fileData);//

		Head head = new Head();
		head.setAction("1.505");
		head.setMethod("1.1.0006");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message);
	}
}
