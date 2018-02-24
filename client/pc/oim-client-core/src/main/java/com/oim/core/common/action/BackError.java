package com.oim.core.common.action;

/**
 * @author XiaHui
 * @date 2017年6月17日 下午9:13:09
 */
public class BackError {

	private String code;
	private String text;

	public BackError(String code, String text) {
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
