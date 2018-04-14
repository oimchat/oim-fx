package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;

/**
 * @author XiaHui
 * @date 2015年3月16日 下午3:23:23
 */
public class VideoSender extends BaseSender{

	public VideoSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 获取服务器的视频服务端口
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param dataBackAction
	 */
	public void getVideoServerPort(DataBackAction dataBackAction) {
		
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.502");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message, dataBackAction);
	}
	public void getUserVideoAddress(String userId,DataBackAction dataBackAction) {
		Message message = new Message();
		message.put("userId", userId);
		
		Head head = new Head();
		head.setAction("1.502");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message,dataBackAction);
	}
	/**
	 * 请求视频聊天
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void requestVideo(String sendUserId, String receiveUserId) {
		
		Message message = new Message();
		message.put("receiveUserId", receiveUserId);
		message.put("sendUserId", sendUserId);
		
		Head head = new Head();
		head.setAction("1.502");
		head.setMethod("1.1.0004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		
		this.write(message);
	}
	
	public void responseVideo(String sendUserId, String receiveUserId,String actionType) {
		Message message = new Message();
		message.put("receiveUserId", receiveUserId);
		message.put("sendUserId", sendUserId);
		message.put("actionType", actionType);
		
		Head head = new Head();
		head.setAction("1.502");
		head.setMethod("1.1.0005");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}
}
