package com.only.common.result;

/**
 * @author: XiaHui
 * @date: 2016年11月22日 上午10:10:05
 */
public class ErrorInfo {

	private String code;
	private String text;

	public ErrorInfo(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
