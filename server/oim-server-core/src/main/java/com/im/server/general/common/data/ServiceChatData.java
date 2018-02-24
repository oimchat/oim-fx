package com.im.server.general.common.data;

import java.util.Date;

public class ServiceChatData {

	private String userId;
	private String userHead;// 用户头像
	private String userRole;//
	private String userName;//
	private String userNickname;//

	private String serviceId;// 发送消息用户id
	private String serviceHead;// 用户头像
	private String serviceRole;//
	private String serviceName;//
	private String serviceNickname;//

	public Date time;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceHead() {
		return serviceHead;
	}

	public void setServiceHead(String serviceHead) {
		this.serviceHead = serviceHead;
	}

	public String getServiceRole() {
		return serviceRole;
	}

	public void setServiceRole(String serviceRole) {
		this.serviceRole = serviceRole;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceNickname() {
		return serviceNickname;
	}

	public void setServiceNickname(String serviceNickname) {
		this.serviceNickname = serviceNickname;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
