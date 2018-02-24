package com.oim.common.chat.util;

import com.oim.core.common.box.FontBox;

/**
 * @author XiaHui
 * @date 2017-11-10 10:53:22
 */
public class HtmlContentUtil {

	
	public static StringBuilder getFontStyle(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		style.append("style=\"");
		style.append(getFontStyleValue(fontName, fontSize, color, bold, underline, italic));
		style.append("\"");
		return style;
	}
	
	/**
	 * 这里组装聊天内容的样式，字体、大小、颜色、下划线、粗体、倾斜等
	 *
	 * @Author: XiaHui
	 * @Date: 2016年2月16日
	 * @ModifyUser: XiaHui
	 * @ModifyDate: 2016年2月16日
	 * @param fontName
	 * @param fontSize
	 * @param color
	 * @param bold
	 * @param underline
	 * @param italic
	 * @return
	 */
	public static StringBuilder getFontStyleValue(String fontName, int fontSize, String color, boolean bold, boolean underline, boolean italic) {
		StringBuilder style = new StringBuilder();
		style.append("font-family:").append(FontBox.getFontName(fontName)).append(";");
		style.append("font-size:").append(fontSize).append("px;");
		if (underline) {
			style.append("margin-top:0;text-decoration:underline;");
		} else {
			style.append("margin-top:0;");
		}
		if (italic) {
			style.append("font-style:italic;");
		}
		if (bold) {
			style.append("font-weight:bold;");
		}
		if (null != color) {
			style.append("color:#");
			style.append(color);
			style.append(";");
		}
		return style;
	}

	public static StringBuilder getImageTag(String id, String name, String value, String path) {

		StringBuilder image = new StringBuilder();
		image.append("	<img ");
		if (null != id && !"".equals(id)) {
			image.append(" id=\"");
			image.append(id);
			image.append("\"");
		}
		if (null != name && !"".equals(name)) {
			image.append(" name=\"");
			image.append(name);
			image.append("\"");
		}
		if (null != value && !"".equals(value)) {
			image.append(" value=\"");
			image.append(value);
			image.append("\"");
		}
		image.append(" src=\"");
		image.append(path);
		image.append("\" ");
		image.append("	style=\" max-width: 100%; height: auto;\" ");
		image.append(" />");
		return image;
	}
	
	public static String getImageTag(String id, String name, String value, String source, String alt) {
		StringBuilder image = new StringBuilder();
		image.append("<img ");
		if (null != id && !"".equals(id)) {
			image.append(" id=\"");
			image.append(id);
			image.append("\"");
		}
		if (null != name && !"".equals(name)) {
			image.append(" name=\"");
			image.append(name);
			image.append("\"");
		}
		if (null != value && !"".equals(value)) {
			image.append(" value=\"");
			image.append(value);
			image.append("\"");
		}
		if (null != source && !"".equals(source)) {
			image.append(" src=\"");
			image.append(source);
			image.append("\" ");
		}

		if (null != alt && !"".equals(alt)) {
			image.append(" alt=\"");
			image.append(alt);
			image.append("\"");
		}
		image.append(" />");
		return image.toString();
	}
}
