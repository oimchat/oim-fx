package com.oim.core.common.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oim.common.chat.util.HtmlContentUtil;

/**
 * 
 * @author XiaHui
 * @date 2017-12-26 20:56:13
 */
public class MatcherEmotion {

	Map<String, String> map = new HashMap<String, String>();

	public MatcherEmotion() {
	}

	public void put(String key, String url) {
		map.put(key, url);
	}

	public String handlerToImageTag(String source) {
		if (source != null) {

			Pattern sina = Pattern.compile("(\\[[^\\]]*\\])");
			Matcher sinaMatcher = sina.matcher(source);
			List<String> list = new ArrayList<String>();
			while (sinaMatcher.find()) {
				String e = sinaMatcher.group();
				list.add(e);
			}
			if (!list.isEmpty()) {
				for (String key : list) {
					String url = map.get(key);
					if (null != url) {
						String imageTag = HtmlContentUtil.getImageTag("", "image", "", url, "");
						source = source.replace(key, imageTag);
					}
				}
			}
		}
		return source;
	}
	public static void main(String[] arg) {
		MatcherEmotion se = new MatcherEmotion();
		String text = "浙江急急急撒[笑而不语]";
		System.out.println(se.handlerToImageTag(text));
	}
}
