package com.oim.core.business.sender;

import com.only.net.data.action.DataBackAction;
import com.only.net.data.action.DataBackActionAdapter;
import com.onlyxiahui.app.base.AppContext;
import com.onlyxiahui.im.bean.UserData;
import com.onlyxiahui.im.bean.UserHead;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.client.Message;
import com.onlyxiahui.im.message.data.LoginData;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月6日 下午8:26:31
 * @version 0.0.1
 */
public class PersonalSender extends BaseSender {

	public PersonalSender(AppContext appContext) {
		super(appContext);
	}

	/**
	 * 登录,为了层次更分明，所以绕了这么远，最终在这里把消息发送出去。而不是直接在ui层就发送了
	 * 
	 * @param loginData
	 * @param action
	 */
	public void login(LoginData loginData, DataBackActionAdapter action) {

		Message message = new Message();
		message.put("loginData", loginData);

		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0001");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/**
	 * 重连接，当断网后又恢复网络时
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 */
	public void reconnect(LoginData loginData) {
		Message message = new Message();
		message.put("loginData", loginData);
		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0002");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message);
	}

	public void getUserData(DataBackAction action) {
		Message message = new Message();
		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0003");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	public void updateUser(UserData user, DataBackAction action) {
		Message message = new Message();
		message.put("userData", user);

		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0004");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}

	/**
	 * 发送修改密码请求
	 * 
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param user
	 * @param action
	 */
	public void upadtePassword(String oldPassword,String newPassword, DataBackActionAdapter action) {
		Message message = new Message();
		message.put("oldPassword", oldPassword);
		message.put("newPassword", newPassword);
		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0005");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}
	
	/**
	 * 上传头像
	 * @author XiaHui
	 * @date 2017年6月18日 下午5:24:59
	 * @param userHead
	 * @param action
	 */
	public void uploadHead(UserHead userHead, DataBackActionAdapter action) {
		Message message = new Message();
		message.put("userHead", userHead);
		Head head = new Head();
		head.setAction("1.100");
		head.setMethod("1.1.0006");
		head.setTime(System.currentTimeMillis());
		message.setHead(head);
		this.write(message, action);
	}
}
