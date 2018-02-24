package com.onlyxiahui.im.message.data.chat;

/**
 * 聊天内容信息列表
 * 
 * @author XiaHui
 * @date 2015-03-2 下午2:51:24
 */
public class Font {

	private boolean underline = false;
	private boolean bold = false;
	private boolean italic = false;
	private String color = "000000";
	private String name = "微软雅黑";
	private int size = 12;

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
