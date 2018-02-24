package com.oim.fx.common.component.list;

/**
 * 选择框或者列表通用显示文本和key
 * @author: XiaHui
 * @date: 2017年5月25日 下午4:52:40
 */
public class KeyText {

	private String text;
	private String key;

	public KeyText() {

	}

	public KeyText(String key, String text) {
		this.key = key;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
