package com.onlyxiahui.im.message.data;

/**
 * @Author: XiaHui
 * @Date: 2016年1月5日
 * @ModifyUser: XiaHui
 * @ModifyDate: 2016年1月5日
 */
public class LoginData {

	private String account;// 帐号
	private String password;// 密码
	private String verifyCode;
	private String status;

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

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
