package com.oim.fx.common.box;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author: XiaHui
 * @date: 2016年10月13日 下午5:15:42
 */
public class FontBox {
	static Map<String, String> map = new HashMap<String, String>();

	static {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font font : fonts) {
			map.put(font.getFontName(), font.getFontName(Locale.ENGLISH));
		}
	}

	public static String getFontName(String name) {
		String fontName = map.get(name);
		if (null == fontName) {
			fontName = name;
		}
		return fontName;
	}
}
