package com.im.server.general.common.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.im.base.common.util.CacheKeyUtil;
import com.only.common.result.ResultMessage;
import com.onlyxiahui.im.message.data.Auth;
import com.onlyxiahui.im.message.data.UserData;
import com.im.server.general.common.service.api.CacheBaseService;
import com.im.server.general.common.service.api.UserBaseService;

/**
 * @author Only
 * @date 2016年5月20日 下午2:00:02
 */
@Service
public class AuthService {
	protected final Logger logger = LogManager.getLogger(this.getClass());
	@Resource
	UserBaseService userBaseService;
	@Resource
	CacheBaseService cacheBaseService;

	Map<String, String> map = new HashMap<String, String>();

	Map<String, UserData> userDataMap = new ConcurrentHashMap<String, UserData>();

	public AuthService() {
		map.put("own-000001", "kkkyyyttt");
	}

	public boolean isAuth(Auth auth) {
		boolean isAuth = false;
		String authId = auth.getId();
		String authKey = auth.getKey();
		if (map.containsKey(authId)) {
			String key = map.get(authId);
			isAuth = (authKey.equals(key));
		}
		return isAuth;
	}

	public ResultMessage getToken(Auth auth, String userId) {
		boolean success = true;
		ResultMessage message = new ResultMessage();
		String authId = auth.getId();
		String authKey = auth.getKey();
		if (map.containsKey(authId)) {
			String key = map.get(authId);
			if (authKey.equals(key)) {
				String textTemp = authKey + userId;
				String token = getToken(textTemp);
				message.put("token", token);
				cacheBaseService.putDefault(CacheKeyUtil.getTokenCacheKey(userId), token);// 保存5分钟令牌
			} else {
				success=false;
				message.addError(ResultMessage.code_fail, "authKey无效");
			}
		} else {
			success=false;
			message.addError(ResultMessage.code_fail, "authId无效");
		}
		message.setSuccess(success);
		return message;
	}


	public ResultMessage authUser(Auth auth, UserData userData) {
		boolean success = true;
		ResultMessage message = new ResultMessage();
		String authId = auth.getId();
		String authKey = auth.getKey();
		String userId = userData.getId();
		if (map.containsKey(authId)) {
			String key = map.get(authId);
			if (authKey.equals(key)) {
				String text = authKey + userId;
				String token = getToken(text);
				message.put("token", token);
				cacheBaseService.putDefault(CacheKeyUtil.getTokenCacheKey(userId), token);// 保存5分钟令牌
				userDataMap.put(userId, userData);
			} else {
				success=false;
				message.addError(ResultMessage.code_fail, "authKey无效");
			}
		} else {
			success=false;
			message.addError(ResultMessage.code_fail, "authId无效");
		}
		message.setSuccess(success);
		return message;
	}
	protected String getToken(String text) {
		try {
			byte id[] = text.getBytes();
			byte now[] = new Long(System.currentTimeMillis()).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			BigInteger a = new BigInteger(md.digest());
			return a.toString(16);
		} catch (IllegalStateException e) {
			return (text);
		} catch (NoSuchAlgorithmException e) {
			return (text);
		}
	}
}
