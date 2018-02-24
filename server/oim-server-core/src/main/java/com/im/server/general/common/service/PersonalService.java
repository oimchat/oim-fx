package com.im.server.general.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.im.server.general.common.bean.GroupCategory;
import com.im.server.general.common.bean.User;
import com.im.server.general.common.bean.UserCategory;
import com.im.server.general.common.bean.UserHead;
import com.im.server.general.common.bean.UserNumber;
import com.im.server.general.common.dao.GroupCategoryDAO;
import com.im.server.general.common.dao.NumberDAO;
import com.im.server.general.common.dao.UserCategoryDAO;
import com.im.server.general.common.dao.UserDAO;
import com.im.server.general.common.dao.UserHeadDAO;
import com.im.server.general.common.service.api.UserBaseService;
import com.im.base.common.constant.ErrorConstants;
import com.im.server.general.business.push.PersonalPush;
import com.im.server.general.business.thread.SessionServerHandler;
import com.only.common.util.OnlyDateUtil;
import com.only.net.session.SocketSession;
import com.onlyxiahui.im.message.Head;
import com.onlyxiahui.im.message.data.LoginData;
import com.onlyxiahui.im.message.data.UserData;
import com.onlyxiahui.im.message.server.ResultMessage;

/**
 * 描述： 个人业务处理service
 * 
 * @author XiaHui
 * @date 2016年1月7日 下午7:48:52
 * @version 0.0.1
 */
@Service
@Transactional
public class PersonalService {

	@Resource
	private UserDAO userDAO;
	@Resource
	private UserHeadDAO userHeadDAO;
	@Resource
	private NumberDAO numberDAO;
	@Resource
	private UserCategoryDAO userCategoryDAO;
	@Resource
	private GroupCategoryDAO groupCategoryDAO;
	@Resource
	private UserBaseService userBaseService;
	@Resource
	SessionServerHandler sessionServerHandler;
	@Resource
	PersonalPush personalPush;

	public ResultMessage verify(Head head, String address, String account) {
		ResultMessage m = new ResultMessage();
		return m;
	}

	boolean isNeedVerify(String address, String account) {
		return false;
	}

	/**
	 * 登陆
	 * 
	 * @param userMessage
	 * @return
	 */
	public ResultMessage login(SocketSession socketSession, LoginData loginData) {
		ResultMessage message = new ResultMessage();
		if (null != loginData) {
			String account = loginData.getAccount();
			if (StringUtils.isNotBlank(account)) {

				if (StringUtils.isNotBlank(loginData.getPassword())) {
					account = account.replace(" ", "").replace("'", "");
					User user = userDAO.getByAccount(account);
					if (null != user) {
						if (loginData.getPassword().equals(user.getPassword())) {

							UserHead userHead = userHeadDAO.getLastByUserId(user.getId());
							UserData userData = new UserData();
							BeanUtils.copyProperties(user, userData);
							message.put("userData", userData);
							message.put("userHead", userHead);
							socketSession.setAuth(true);
							socketSession.setKey(user.getId());
							putSocketSession(socketSession);
							userBaseService.putUserStatus(user.getId(), loginData.getStatus());
						} else {
							message.addWarning(ErrorConstants.warning_business + ".0005", "密码错误！");
						}
					} else {
						message.addWarning(ErrorConstants.warning_business + ".0004", "帐号不存在！");
					}
				} else {
					message.addWarning(ErrorConstants.warning_business + ".0003", "密码不能为空！");
				}
			} else {
				message.addWarning(ErrorConstants.warning_business + ".0002", "帐号不能为空！");
			}
		} else {
			message.addWarning(ErrorConstants.warning_business + ".0001", "帐号和密码不能为空！");
		}
		return message;
	}

	private void putSocketSession(SocketSession socketSession) {
		String key = socketSession.getKey();
		if (sessionServerHandler.isSingleSession()) {
			CopyOnWriteArraySet<SocketSession> set = sessionServerHandler.remove(key);
			if (set != null && !set.isEmpty()) {
				Map<String, Object> client = new HashMap<String, Object>();
				client.put("clientType", "");
				client.put("clientVersion", "");
				client.put("onlineTime", "");

				personalPush.pushOtherOnline(set, client, true);
			}
		}
		sessionServerHandler.put(socketSession.getKey(), socketSession);
	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	public User register(User user) {
		int i = new Random().nextInt(20);
		i = i + 1;
		UserNumber userNumber = new UserNumber();// 生成数子账号
		userNumber.setCreateTime(OnlyDateUtil.getNowDate());

		numberDAO.save(userNumber);

		user.setAccount(userNumber.getId() + "");
		user.setNumber(userNumber.getId());
		user.setHead(i + "");
		user.setCreateTime(OnlyDateUtil.getNowDate());
		userDAO.save(user);

		UserCategory userCategory = new UserCategory();// 生成默认分组信息
		userCategory.setUserId(user.getId());
		userCategory.setName("我的好友");
		userCategory.setSort(UserCategory.sort_default);

		userCategoryDAO.save(userCategory);

		GroupCategory groupCategory = new GroupCategory();// 生成默认分组信息
		groupCategory.setUserId(user.getId());
		groupCategory.setName("我的聊天群");
		groupCategory.setSort(GroupCategory.sort_default);

		groupCategoryDAO.save(groupCategory);

		return user;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userMessage
	 * @return
	 */
	public ResultMessage update(User user) {
		ResultMessage message = new ResultMessage();
		int mark = userDAO.updateSelective(user);
		if (mark > 0) {
		} else {
			message.addWarning("000000", "修改失败！");
		}
		return message;
	}

	public ResultMessage updatePassword(String userId, String oldPassword, String newPassword) {
		ResultMessage message = new ResultMessage();
		try {
			User user = userDAO.get(userId);
			if (user.getPassword().equals(oldPassword)) {
				boolean mark = userDAO.updatePassword(userId, newPassword);
				if (!mark) {
					message.addWarning(ErrorConstants.warning_business + ".0002", "修改失败！");
				}
			} else {
				message.addWarning(ErrorConstants.warning_business + ".0001", "原密码不正确！");
			}
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_business + ".0002", "修改失败！");
		}
		return message;
	}

	/**
	 * 用户上传图像
	 * 
	 * @author: XiaHui
	 * @param userHead
	 * @return
	 * @createDate: 2017年6月6日 上午11:49:21
	 * @update: XiaHui
	 * @updateDate: 2017年6月6日 上午11:49:21
	 */
	public ResultMessage uploadHead(UserHead userHead) {
		ResultMessage message = new ResultMessage();
		try {
			userHead.setCreateTime(new Date());
			userHeadDAO.save(userHead);
			message.put("userHead", userHead);
		} catch (Exception e) {
			message.addWarning(ErrorConstants.warning_business + ".0001", "上传失败！");
		}
		return message;
	}
}
