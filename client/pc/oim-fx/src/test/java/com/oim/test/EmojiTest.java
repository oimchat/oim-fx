package com.oim.test;

import org.hamcrest.Matchers;

import emoji4j.Emoji;
import emoji4j.EmojiUtils;

/**
 * @author XiaHui
 * @date 2017年5月29日 上午11:23:07
 */
public class EmojiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Emoji e = EmojiUtils.getEmoji("😂");
		System.out.println(e.getDecimalHtml());
		System.out.println(Matchers.equalToIgnoringCase("😂"));

		System.out.println("setDecimalHtml:" + htmlifyHelper("🕜", false, false));
		System.out.println("setHexHtml:" + htmlifyHelper("🕜", true, false));
		System.out.println("setDecimalSurrogateHtml:" + htmlifyHelper("🕜", false, true));
	}

	protected static String htmlifyHelper(String text, boolean isHex, boolean isSurrogate) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {
			int ch = text.codePointAt(i);

			if (ch <= 128) {
				sb.appendCodePoint(ch);
			} else if (ch > 128 && (ch < 159 || (ch >= 55296 && ch <= 57343))) {
				// don't write illegal html characters
				// refer
				// http://en.wikipedia.org/wiki/Character_encodings_in_HTML
				// Illegal characters section
				continue;
			} else {
				if (isHex) {
					sb.append("0x" + Integer.toHexString(ch) + ";");
				} else {
					if (isSurrogate) {
						double H = Math.floor((ch - 0x10000) / 0x400) + 0xD800;
						double L = ((ch - 0x10000) % 0x400) + 0xDC00;
						sb.append("&#" + String.format("%.0f", H) + ";&#" + String.format("%.0f", L) + ";");
					} else {
						sb.append("&#" + ch + ";");
					}
				}
			}
		}
		return sb.toString();
	}
}
