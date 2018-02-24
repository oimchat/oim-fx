package com.im.server.general.business.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.im.server.general.common.bean.SecurityQuestion;
import com.im.server.general.common.bean.User;
import com.im.server.general.common.bean.UserHead;
import com.im.server.general.common.service.PersonalService;
import com.im.server.general.common.service.SecurityQuestionService;
import com.im.server.general.common.service.UserService;
import com.only.general.annotation.action.ActionMapping;
import com.only.general.annotation.action.MethodMapping;
import com.only.general.annotation.parameter.Define;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.LoginData;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述： 个人信息业务处理
 * 
 * @author XiaHui
 * @date 2016年1月7日 下午7:45:48
 * @version 0.0.1
 */
@Component
@ActionMapping(value = "1.100")
public class PersonalAction {

	@Resource
	private UserService userService;
	@Resource
	private PersonalService personalService;
	@Resource
	private SecurityQuestionService securityQuestionService;

	/**
	 * 用户注册
	 * @author XiaHui
	 * @date 2017-11-24 13:04:24
	 * @param socketSession
	 * @param head
	 * @param user：用户信息
	 * @param list：密保问题
	 * @return
	 */
	@MethodMapping(value = "1.1.0000", isIntercept = false)
	public Object register(
			SocketSession socketSession,
			Head head,
			@Define("userData") User user,
			@Define("securityQuestionList") List<SecurityQuestion> list) {

		ResultMessage message = new ResultMessage();
		try {
			personalService.register(user);
			securityQuestionService.save(list, user.getId());
			UserData userData = new UserData();
			BeanUtils.copyProperties(user, userData);
			message.put("userData", userData);
		} catch (Exception e) {
			e.printStackTrace();
			message.addWarning("001", "注册失败");
		}
		return message;
	}

	/**
	 * 登录
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0001", isIntercept = false)
	public Object login(SocketSession socketSession, Head head, @Define("loginData") LoginData loginData) {
		ResultMessage message = personalService.login(socketSession, loginData);
		return message;
	}

	/**
	 * 处理客户端断开连接后自动连接
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0002", isIntercept = false)
	public Object connect(SocketSession socketSession, @Define("loginData") LoginData loginData) {
		ResultMessage message = personalService.login(socketSession, loginData);
		return message;
	}

	@MethodMapping(value = "1.1.0003")
	public Object getUserData(SocketSession socketSession) {
		ResultMessage message = new ResultMessage();
		UserData user = userService.getUserDataById(socketSession.getKey());
		message.put("userData", user);
		return message;
	}

	/**
	 * 修改个人信息
	 * 
	 * @param userMessage
	 * @param dataWrite
	 * @return
	 */
	@MethodMapping(value = "1.1.0004")
	public Object updateUser(@Define("userData") User user) {
		return personalService.update(user);
	}

	/**
	 * 修改密码
	 * 
	 * @param socketSession
	 * @param password
	 * @return
	 */
	@MethodMapping(value = "1.1.0005")
	public Object updatePassword(SocketSession socketSession,
			@Define("oldPassword") String oldPassword,
			@Define("newPassword") String newPassword) {
		return personalService.updatePassword(socketSession.getKey(), oldPassword, newPassword);
	}

	@MethodMapping(value = "1.1.0006")
	public Object uploadHead(@Define("userHead") UserHead userHead) {
		return personalService.uploadHead(userHead);
	}
}
