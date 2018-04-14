package com.onlyxiahui.im.message;

/**
 * @author Only
 * @date 2016-05-19 04:11:12
 */
public class Head {

	private String key;
	private String name;
	private String action;
	private String method;
	private String version;
	private long time;

	public static final String code_fail = "0";
	public static final String code_success = "1";

	public Head() {
		name = "";
		version = "1";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
