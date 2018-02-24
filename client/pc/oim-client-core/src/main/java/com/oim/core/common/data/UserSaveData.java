package com.oim.core.common.data;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016年1月23日 上午12:15:21
 * @version 0.0.1
 */
public class UserSaveData {

	private String account;
	private String password;
	private String status;
	private String head;
	private String headPath;
	private LoginConfig loginConfig;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public LoginConfig getLoginConfig() {
		return loginConfig;
	}

	public void setLoginConfig(LoginConfig loginConfig) {
		this.loginConfig = loginConfig;
	}

}
