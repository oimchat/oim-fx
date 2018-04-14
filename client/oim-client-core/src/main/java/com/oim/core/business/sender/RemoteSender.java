package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class RemoteSender extends BaseSender {

	public RemoteSender(AppContext appContext) {
		super(appContext);
	}

	public void getRemoteServerPort(DataBackAction dataBackAction) {

		Message message = new Message();
		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message, dataBackAction);
	}

	public void getUserVideoAddress(String userId, DataBackAction dataBackAction) {
		Message message = new Message();
		message.put("userId", userId);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, dataBackAction);
	}

	public void requestRemoteControl(String sendUserId, String receiveUserId) {

		Message message = new Message();
		message.put("receiveUserId", receiveUserId);
		message.put("sendUserId", sendUserId);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message);
	}

	public void responseRemoteControl(String sendUserId, String receiveUserId, String actionType, String code, String message) {
		Message m = new Message();
		m.put("receiveUserId", receiveUserId);
		m.put("sendUserId", sendUserId);
		m.put("actionType", actionType);
		m.put("code", code);
		m.put("message", message);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0005");
		head.setTime(System.currentTimeMillis());
		m.setHead(head);
		this.write(m);
	}

	public void requestRemoteAssist(String sendUserId, String receiveUserId) {

		Message message = new Message();
		message.put("receiveUserId", receiveUserId);
		message.put("sendUserId", sendUserId);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0006");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message);
	}

	public void responseRemoteAssist(String sendUserId, String receiveUserId, String actionType, String code, String message) {
		Message m = new Message();
		m.put("receiveUserId", receiveUserId);
		m.put("sendUserId", sendUserId);
		m.put("actionType", actionType);
		m.put("code", code);
		m.put("message", message);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0007");
		head.setTime(System.currentTimeMillis());
		m.setHead(head);
		this.write(m);
	}
	
	
	public void releaseRemoteControl(String sendUserId, String receiveUserId) {

		Message message = new Message();
		message.put("receiveUserId", receiveUserId);
		message.put("sendUserId", sendUserId);

		Head head = new Head();
		head.setAction("1.504");
		head.setMethod("1.1.0008");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);

		this.write(message);
	}
}
