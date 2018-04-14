package com.onlyxiahui.im.message.data.chat;

/**
 * 描述：
 * 
 * @author XiaHui
 * @date 2016-01-24 12:30:06
 * @version 0.0.1
 */
public class Item {

	private String type;
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static final String type_text = "text";
	public static final String type_image = "image";
	public static final String type_face = "face";
	public static final String type_url = "url";
	public static final String type_file = "file";
	public static final String type_audio = "audio";
	public static final String type_video = "video";
	public static final String type_position = "position";
}
