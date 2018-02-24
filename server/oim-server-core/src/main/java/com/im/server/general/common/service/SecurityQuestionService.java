package com.im.server.general.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.server.general.common.bean.SecurityQuestion;
import com.im.server.general.common.dao.SecurityQuestionDAO;
import com.im.server.general.common.dao.UserDAO;
import com.only.common.util.OnlyDateUtil;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.ResultMessage;

@Service
@Transactional
public class SecurityQuestionService {

	@Resource
	UserDAO userDAO;
	@Resource
	SecurityQuestionDAO securityQuestionDAO;

	public void save(List<SecurityQuestion> list, String userId) {
		if (null != list) {
			for (SecurityQuestion sq : list) {
				sq.setUserId(userId);
				sq.setCreateTime(OnlyDateUtil.getNowDate());
				securityQuestionDAO.save(sq);
			}
		}
	}

	public ResultMessage getListByAccount(String account) {
		ResultMessage message = new ResultMessage();
		UserData userData = userDAO.getUserDataByAccount(account);
		if (null != userData) {
			List<SecurityQuestion> list = securityQuestionDAO.getListByUserId(userData.getId());
			if (list.isEmpty()) {
				message.addWarning("0010001", "您未设置密保问题！");
			}
			message.put("userData", userData);
			message.put("list", list);
		} else {
			message.addWarning("0010001", "帐号不存在！");
		}
		return message;
	}

	public ResultMessage updatePassword(String userId, String password, List<SecurityQuestion> list) {
		ResultMessage message = new ResultMessage();
		List<SecurityQuestion> qs = securityQuestionDAO.getListByUserId(userId);
		if (null != qs && !qs.isEmpty() && null != list && !list.isEmpty()) {
			Map<String, String> map = new HashMap<String, String>();
			for (SecurityQuestion sq : list) {
				map.put(sq.getId(), sq.getAnswer());
			}
			boolean mark = true;
			for (SecurityQuestion sq : qs) {
				if (null != sq.getAnswer() && sq.getAnswer().equals(map.get(sq.getId()))) {

				} else {
					mark = false;
				}
			}
			if (mark) {
				userDAO.updatePassword(userId, password);
			} else {
				message.addWarning("0010001", "问题回答错误！");
			}
		} else {
			message.addWarning("0010001", "修改失败！");
		}
		return message;
	}
}
