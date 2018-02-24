package com.im.server.general.business.push;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.PushMessage;
import com.im.server.general.business.mq.MessageQueueWriteHandler;

/**
 * @author: XiaHui
 * @date: 2016年8月26日 上午9:21:41
 */
@Service
public class UserPush {
	@Resource
	MessageQueueWriteHandler messageQueueWriteHandler;
	
	public void pushUserStatus(String key,String userId,String status,List<String> userIdList){
		Head head=new Head();
		head.setAction("1.101");
		head.setMethod("1.2.0008");
		head.setKey(key);
		head.setTime(System.currentTimeMillis());
		PushMessage message=new PushMessage();
		message.setHead(head);
		message.put("userId", userId);
		message.put("status", status);
		messageQueueWriteHandler.push(userIdList, message);
	}
	
	public void pushUserUpdate(Head h,String userId,List<String> userIdList){
		Head head=new Head();
		head.setAction("1.101");
		head.setMethod("1.2.0009");
		head.setKey(h.getKey());
		head.setTime(System.currentTimeMillis());
		PushMessage message=new PushMessage();
		message.setHead(head);
		message.put("userId", userId);
		messageQueueWriteHandler.push(userIdList, message);
	}
	
	public void pushUserUpdate(Head head,UserData userData,List<String> userIdList){
		PushMessage message=new PushMessage();
		message.setHead(head);
		message.put("userData", userData);
		messageQueueWriteHandler.push(userIdList, message);
	}
}
