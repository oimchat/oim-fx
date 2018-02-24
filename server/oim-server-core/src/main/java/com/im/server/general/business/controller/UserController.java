package com.im.server.general.business.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.im.server.general.common.bean.SecurityQuestion;
import com.im.server.general.common.bean.User;
import com.im.server.general.common.service.PersonalService;
import com.im.server.general.common.service.SecurityQuestionService;
import com.only.general.annotation.parameter.Define;
import com.only.general.annotation.parameter.RequestParameter;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.ResultMessage;

@Controller
@RequestMapping("/user")
public class UserController {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Resource
	private PersonalService personalService;
	@Resource
	private SecurityQuestionService securityQuestionService;

	@ResponseBody
	@RequestParameter
	@RequestMapping("/register.do")
	public Object register(@Define("userData") User user,
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

	@ResponseBody
	@RequestParameter
	@RequestMapping("/getSecurityQuestionList.do")
	public Object getSecurityQuestionList(@Define("account") String account) {
		ResultMessage message = securityQuestionService.getListByAccount(account);
		return message;
	}

	@ResponseBody
	@RequestParameter
	@RequestMapping("/updatePassword.do")
	public Object updatePassword(
			@Define("userId") String userId,
			@Define("password") String password,
			@Define("securityQuestionList") List<SecurityQuestion> list) {
		ResultMessage message = securityQuestionService.updatePassword(userId, password, list);
		return message;
	}
}
