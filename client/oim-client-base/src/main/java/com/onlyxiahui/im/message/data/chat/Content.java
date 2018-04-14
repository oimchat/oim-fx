package com.onlyxiahui.im.message.data.chat;

import java.util.List;

/**
 * 描述：聊天内容
 * 
 * @author XiaHui
 * @date 2015-04-16 08:09:32
 * @version 0.0.1
 */
public class Content {

	private Font font=new Font();
	private List<Section> sections;
	private long timestamp;

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
