package com.oim.test;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Locale;

/**
 * @author: XiaHui
 * @date: 2016年10月13日 下午5:11:01
 */
public class GetAllFontTest {

	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font font : fonts) {
			System.out.println(font.getFontName(Locale.ENGLISH));
			System.out.println(font.getFontName());
			System.out.println(font.getName());
		}
	}

}
