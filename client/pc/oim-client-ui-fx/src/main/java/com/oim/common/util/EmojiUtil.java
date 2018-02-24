package com.oim.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: XiaHui
 * @date: 2017年6月1日 下午5:50:42
 */
public class EmojiUtil {

	static Map<String, String> map = new HashMap<String, String>();
	static Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

	public static boolean isEmoji(String text) {
		boolean isEmoji = false;
		if (text != null) {
			Matcher matcher = emoji.matcher(text);
			isEmoji = ( matcher.matches());
		}
		return isEmoji;
	}

	public static String emojiCode(String text) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			int ch = text.codePointAt(i);

			if (ch <= 128) {
				sb.appendCodePoint(ch);
			} else if (ch > 128 && (ch < 159 || (ch >= 55296 && ch <= 57343))) {
				continue;
			} else {
				sb.append("0x" + Integer.toHexString(ch) + "");
			}
		}
		return sb.toString();
	}

	public static String emojiImage(String text) {
		String image;
		String code = emojiCode(text);
		if (map.containsKey(code)) {
			image = map.get(code);
		} else {
			image = code + ".png";
			map.put(code, image);
		}
		return image;
	}
}
