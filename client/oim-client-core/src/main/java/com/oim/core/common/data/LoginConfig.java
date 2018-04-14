package com.oim.core.common.data;

/**
 * @author: XiaHui
 * @date: 2018-01-21 09:58:34
 */
public class LoginConfig {

	private String account;
	private boolean autoLogin = false;
	private boolean rememberPassword = false;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public boolean isRememberPassword() {
		return rememberPassword;
	}

	public void setRememberPassword(boolean rememberPassword) {
		this.rememberPassword = rememberPassword;
	}
}
