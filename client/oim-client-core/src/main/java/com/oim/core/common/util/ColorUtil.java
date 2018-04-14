package com.oim.core.common.util;

import java.awt.Color;

/**
 * @author XiaHui
 * @date 2015年2月10日 上午10:41:38
 */
public class ColorUtil {
	/**
	 * * 通过RGB颜色得到十六进制的颜色 * @param r 0-255 * @param g 0-255 * @param b 0-255 * @return
	 * 255,0,253返回FF00FD
	 */
	public static String getColorInHexFromRGB(int r, int g, int b) {
		return vali(getHexNum(r)) + vali(getHexNum(g)) + vali(getHexNum(b));
	}

	private static String vali(String s) {
		if (s.length() < 2) {
			s = "0" + s;
		}
		return s;
	}

	private static String getHexNum(int num) {
		int result = num / 16;
		int mod = num % 16;
		StringBuilder s = new StringBuilder();
		hexHelp(result, mod, s);
		return s.toString();
	}

	private static void hexHelp(int result, int mod, StringBuilder s) {
		char[] H = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		if (result > 0) {
			hexHelp(result / 16, result % 16, s);
		}
		s.append(H[mod]);
	}

	public static String getColorInHexFromRGB(Color color) {
		return getColorInHexFromRGB(color.getRed(), color.getGreen(), color.getBlue());
	}

	public static void main(String[] a) {
		System.out.println(getColorInHexFromRGB(255, 254, 253));
		System.out.println(getColorInHexFromRGB(255, 0, 253));
	}

}